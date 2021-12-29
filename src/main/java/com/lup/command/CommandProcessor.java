package com.lup.command;

import com.lup.States;
import com.lup.coffee.Coffee;
import com.lup.coffee.CoffeeFactory;
import com.lup.coffee.InvalidCoffeeException;
import com.lup.exceptions.InvalidInputException;
import com.lup.machine.CoffeeMaker;
import com.lup.machine.InsuficientIngredientsException;
import com.lup.utils.CommandLineTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lup.States.*;
import static com.lup.utils.CommandLineTools.requestMoney;

public class CommandProcessor implements Runnable{

    public static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);

    @Override
    public void run() {
        CoffeeMaker coffeeMaker = CoffeeMaker.getInstance();
        try {
            while (coffeeMaker.isTurnedOn()) {
                Commands commands = CommandChannel.commandChannelActions.getCommandFromChannel();
                if(commands != null) {
                    switch (commands) {
                        case ADD_MONEY:
                            if(!coffeeMaker.currentState.equals(PAYMENT)){
                                logger.error("you must be in the PAYMENT state in order to make a payment, you are now in {} state", coffeeMaker.currentState);
                            }else {
                                logger.info("please specify the amount of money");
                                try {
                                    var diff = requestMoney();
                                    if (diff < 0) {
                                        logger.info("you still need to introduce {} in order to pay for a {}", Math.abs(diff), coffeeMaker.getSelectedCoffee());
                                        coffeeMaker.currentState = PAYMENT;
                                    } else {
                                        CoffeeFactory.buildCoffee(coffeeMaker.getSelectedCoffee());
                                        coffeeMaker.removeMoney(coffeeMaker.getSelectedCoffee().getPrice());
                                        logger.info("coffee {} was made, you may pick it up", coffeeMaker.getSelectedCoffee());
                                        coffeeMaker.currentState = IDLE;
                                        coffeeMaker.setSelectedCoffee(Coffee.NONE);
                                    }
                                } catch (InvalidInputException | InvalidCoffeeException | InsuficientIngredientsException e) {
                                    logger.error(e.getMessage());
                                    coffeeMaker.currentState = PAYMENT;
                                }
                            }
                            break;
                        case SELECT_PRODUCT:
                            if(!coffeeMaker.currentState.equals(IDLE)){
                                logger.error("you can only select a product during IDLE state, you are now in {} state", coffeeMaker.currentState);
                            }else {
                                logger.info("please introduce the type of coffee you want to prepare");
                                CoffeeMaker.getInstance().currentState = States.WAIT_FOR_INPUT;
                                // wait for the user to introduce argument
                                synchronized (Thread.currentThread()) {
                                    Thread.currentThread().wait();
                                }
                                String coffee = CommandChannel.commandChannelActions.getArgumentFromQueue();
                                try {
                                    coffeeMaker.setSelectedCoffee(Coffee.getCoffee(coffee));
                                    logger.info("please select the sugar level with the \"add sugar\" command and confirm your choice");
                                    coffeeMaker.currentState = CONFIRMATION;
                                } catch (InvalidCoffeeException e) {
                                    e.printStackTrace();
                                    coffeeMaker.currentState = IDLE;
                                }
                            }
                            break;
                        case ADD_SUGAR:
                            if(!CoffeeMaker.getInstance().currentState.equals(CONFIRMATION)){
                                logger.error("you must first choose the type of coffee with the command: \"select coffee \"");
                            } else {
                                logger.info("please specify the quantity of sugar, allowed values: 1-10");
                                coffeeMaker.currentState = WAIT_FOR_INPUT;
                                // wait for the user to introduce argument
                                synchronized (Thread.currentThread()) {
                                    Thread.currentThread().wait();
                                }
                                String sugarLevel = CommandChannel.commandChannelActions.getArgumentFromQueue();
                                try {
                                    if(!sugarLevel.isEmpty())
                                        coffeeMaker.setSugarLevel(Byte.parseByte(sugarLevel));
                                    coffeeMaker.currentState = PAYMENT;
                                    logger.info("please add money with the command \"add money\"");
                                } catch (InvalidInputException e) {
                                    e.printStackTrace();
                                    coffeeMaker.currentState = CONFIRMATION;
                                }
                            }
                            break;
                        case GO_BACK:
                            if(coffeeMaker.currentState.equals(PAYMENT)){
                                coffeeMaker.currentState = CONFIRMATION;
                            }else if(coffeeMaker.currentState.equals(CONFIRMATION)){
                                coffeeMaker.currentState = IDLE;
                            }else{
                                logger.error("Cannot go back from state {}", coffeeMaker.currentState);
                            }
                            break;
                        case GET_REST:
                            coffeeMaker.currentState = MONEY_CHECK;
                            if(coffeeMaker.getCurrentSumOfMoney() > 0){
                                coffeeMaker.currentState = BANKNOTE_CHECK;
                                if(CommandLineTools.randomCheck()){
                                    coffeeMaker.currentState = MONEY_BACK_CASH;
                                }else{
                                    logger.info("The machine does not have sufficient banknotes to perform this action." +
                                            " A voucher will be provided");
                                    coffeeMaker.currentState = QR_SHOW;
                                }
                                coffeeMaker.returnAllMoney();
                            }else{
                                logger.info("The current sold of the machine is {}. Unable to provide the return of " +
                                        "money", coffeeMaker.getCurrentSumOfMoney());
                            }
                            coffeeMaker.currentState = IDLE;
                        case STATUS:
                            logger.info("\ncurrent state: {}\n" +
                                        "sold: {}\n" +
                                        "selected coffee: {}\n" +
                                        "sugarQuantity: {}\n" +
                                        "coffeeQuantity: {}\n", coffeeMaker.currentState, coffeeMaker.getCurrentSumOfMoney(), coffeeMaker.getSelectedCoffee(),
                                    coffeeMaker.getSugarQuantity(), coffeeMaker.getCoffeeQuantity());
                            break;
                    }
                }
            }
        }catch(InterruptedException e){
           logger.info("Thread {} was interrupted", Thread.currentThread().getName());
        }
        logger.info("Thread {}, has died", Thread.currentThread().getName());
    }

}
