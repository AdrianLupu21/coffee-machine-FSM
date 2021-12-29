package com.lup.coffee;

public enum Coffee {
    CAPPUCCINO(30),
    CAFFE_LATTE(20),
    NONE(0);

    private int price;

    public int getPrice() {
        return price;
    }

    Coffee(int price){
        this.price = price;
    }

    public static Coffee getCoffee(String coffee) throws InvalidCoffeeException {
        if(coffee.equalsIgnoreCase("cappuccino")){
            return CAPPUCCINO;
        }else if (coffee.equalsIgnoreCase("caffe latte")){
            return CAFFE_LATTE;
        }else {
            throw new InvalidCoffeeException();
        }
    }
}
