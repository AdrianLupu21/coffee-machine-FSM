package com.lup.watcher;

import com.lup.machine.CoffeeMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Watcher implements Runnable{

    public static final Logger logger = LoggerFactory.getLogger(Watcher.class);
    public static final CoffeeMaker coffeeMaker = CoffeeMaker.getInstance();

    @Override
    public void run(){
        while(coffeeMaker.isTurnedOn()) {
          //  logger.info("Checking the status of the machine");
            if (checkIngredients()) {
            //    logger.info("Everything seems fine");
            }
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public boolean checkIngredients(){
        if(coffeeMaker.getCoffeeQuantity() <= 0){
          //  logger.warn("Please add coffee to the coffee machine. Current quantity of coffee is {}", coffeeMaker.getCoffeeQuantity());
            return false;
        }
        if(coffeeMaker.getSugarQuantity() <= 0){
          //  logger.warn("Please add sugar to the coffee machine. Current quantity of sugar is {}", coffeeMaker.getSugarQuantity());
            return false;
        }
        return true;
    }
}
