package com.lup.coffee;

public class InvalidCoffeeException extends Exception {
    public InvalidCoffeeException(String message) {
        super(message);
    }
    public InvalidCoffeeException(){
        super();
    }

}
