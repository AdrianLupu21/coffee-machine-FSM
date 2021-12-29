package com.lup.coffee;

import com.lup.machine.CoffeeMaker;
import com.lup.machine.InsuficientIngredientsException;

public class CoffeeFactory {

    public static CoffeeBuilder buildCoffee(Coffee coffee) throws InsuficientIngredientsException, InvalidCoffeeException {
        CoffeeMaker coffeeMaker = CoffeeMaker.getInstance();
        switch (coffee){
            case CAPPUCCINO:
                return CoffeeBuilder.builder()
                        .setCoffeeQuantity(coffeeMaker.extractCoffee(10))
                        .setSugarQuantity(coffeeMaker.extractSugar(coffeeMaker.getSugarLevel() != 0 ? coffeeMaker.getSugarLevel(): 10))
                        .build();
            case CAFFE_LATTE:
                return CoffeeBuilder.builder()
                        .setCoffeeQuantity(coffeeMaker.extractCoffee(15))
                        .setSugarQuantity(coffeeMaker.extractSugar(coffeeMaker.getSugarLevel() != 0 ? coffeeMaker.getSugarLevel(): 10))
                        .build();
            default:
                throw new InvalidCoffeeException();
        }
    }

}
