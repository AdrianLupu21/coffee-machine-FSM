package com.lup.utils;

import com.lup.States;
import com.lup.command.CommandChannel;
import com.lup.exceptions.InvalidInputException;
import com.lup.machine.CoffeeMaker;

import static com.lup.machine.CoffeeMaker.requestPaymentMethod;

public class CommandLineTools {
    public static int getSumFromCommandLine(String sum) throws InvalidInputException {
        try{
            return Integer.parseInt(sum);
        }catch (NumberFormatException e){
            throw new InvalidInputException("The sum you entered {}, is invalid. Please enter an integer value greater than 0");
        }
    }


    public static int requestMoney() throws InterruptedException, InvalidInputException {
        CoffeeMaker coffeeMaker = CoffeeMaker.getInstance();
        CoffeeMaker.getInstance().currentState = States.WAIT_FOR_INPUT;
        // wait for the user to introduce argument
        synchronized (Thread.currentThread()) {
            Thread.currentThread().wait();
        }
        var sumOfMoney = getSumFromCommandLine(CommandChannel.commandChannelActions.getArgumentFromQueue());
        if(requestPaymentMethod())
            coffeeMaker.addMoney(sumOfMoney);
        return coffeeMaker.getCurrentSumOfMoney() - coffeeMaker.getSelectedCoffee().getPrice();
    }

    public static boolean randomCheck(){
        final int maxValue = 3;
        final int minValue = 0;
        double f = Math.random()/Math.nextDown(1.0);
        int x = (int) Math.round(minValue*(1.0-f) + maxValue *f);
        // 25% change of returning false
        if(x == 2)
            return false;
        return true;
    }

    public static boolean randomCheck(int propabilityOfFailure){
        double x = Math.random();
        if(x <= (double)propabilityOfFailure/100){
            return false;
        }
        return true;
    }

}
