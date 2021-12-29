package com.lup.utils;

import com.lup.States;
import com.lup.coffee.CoffeeFactory;
import com.lup.command.CommandChannel;
import com.lup.exceptions.InvalidInputException;
import com.lup.machine.CoffeeMaker;

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
        coffeeMaker.addMoney(sumOfMoney);
        return coffeeMaker.getCurrentSumOfMoney() - coffeeMaker.getSelectedCoffee().getPrice();
    }
}
