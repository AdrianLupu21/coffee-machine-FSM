package com.lup.coffee;

// TODO add multiple ingredients for this method to make sense
public class CoffeeBuilder{
    private final int sugarQuantity;
    private final int coffeeQuantity;

    private CoffeeBuilder(int sugarQuantity, int coffeeQuantity){
        this.coffeeQuantity = coffeeQuantity;
        this.sugarQuantity = sugarQuantity;
    }

    public static Builder builder(){
        return new Builder();
    }

    static class Builder{

        private int sugarQuantity;
        private int coffeeQuantity;

        public Builder setSugarQuantity(int sugarQuantity){
            this.sugarQuantity = sugarQuantity;
            return this;
        }

        public Builder setCoffeeQuantity(int coffeeQuantity){
            this.coffeeQuantity = coffeeQuantity;
            return this;
        }



        public CoffeeBuilder build(){
            return new CoffeeBuilder(sugarQuantity, coffeeQuantity);
        }

    }
}
