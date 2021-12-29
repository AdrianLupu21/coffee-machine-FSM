package com.lup.machine;

import com.lup.States;
import com.lup.coffee.Coffee;
import com.lup.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoffeeMaker {

    public static final Logger logger = LoggerFactory.getLogger(CoffeeMaker.class);

    private boolean isTurnedOn = false;
    public States currentState = States.IDLE;
    public int currentSumOfMoney;
    public int coffeeQuantity = 100;
    public int sugarQuantity = 10;
    private static CoffeeMaker coffeeMaker;
    private Coffee selectedCoffee = Coffee.NONE;
    private byte sugarLevel;

    private CoffeeMaker(){}

    public static CoffeeMaker getInstance(){
        if(coffeeMaker == null){
            coffeeMaker = new CoffeeMaker();
        }
        return coffeeMaker;
    }

    public void addMoney(int sumToAdd) throws InvalidInputException {
        if(sumToAdd <= 0){
            throw new InvalidInputException("The sum you entered {}, is invalid. Please enter an integer value greater than 0");
        }
        logger.info("adding money to the account");
        currentSumOfMoney += sumToAdd;
    }

    public void removeMoney(int sumToRemove) throws InvalidInputException {
        if(sumToRemove < 0){
            throw new InvalidInputException("The sum, {}, you entered " + sumToRemove +", is invalid. Please enter an integer value greater than 0");
        }else if(currentSumOfMoney - sumToRemove <0){
            throw new InvalidInputException("The sum, "+sumToRemove+", you want to remove is bigger than the current sold of the machine, " + currentSumOfMoney);
        }else{
            logger.info("removing {} from the account", sumToRemove);
            currentSumOfMoney -= sumToRemove;
        }

    }

    public int extractSugar(int sugar) throws InsuficientIngredientsException {
        if(sugarQuantity - sugar < 0){
            throw new InsuficientIngredientsException("there is not enough sugar to satisfy the order sugar=" + sugar);
        }else {
            sugarQuantity -= sugar;
            return sugar;
        }
    }

    public int extractCoffee(int coffee) throws InsuficientIngredientsException {
        if(coffeeQuantity - coffee < 0){
            throw new InsuficientIngredientsException("there is not enough coffee to satisfy the order coffee=" + coffee);
        }else {
            coffeeQuantity -= coffee;
            return coffee;
        }
    }


    public void setSugarLevel(byte sugarLevel) throws InvalidInputException {
        if(sugarLevel>10 || sugarLevel <0){
            throw new InvalidInputException("sugar level must have a value between 0 and 10");
        }else{
            this.sugarLevel = sugarLevel;
        }
    }

    public byte getSugarLevel() {
        return sugarLevel;
    }

    public Coffee getSelectedCoffee() {
        return selectedCoffee;
    }

    public void setSelectedCoffee(Coffee selectedCoffee) {
        this.selectedCoffee = selectedCoffee;
    }

    public int getCurrentSumOfMoney() {
        return currentSumOfMoney;
    }

    public void setCurrentSumOfMoney(int currentSumOfMoney) {
        this.currentSumOfMoney = currentSumOfMoney;
    }

    public int getCoffeeQuantity() {
        return coffeeQuantity;
    }

    public void setCoffeeQuantity(int coffeeQuantity) {
        this.coffeeQuantity = coffeeQuantity;
    }

    public int getSugarQuantity() {
        return sugarQuantity;
    }

    public void setSugarQuantity(int sugarQuantity) {
        this.sugarQuantity = sugarQuantity;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        isTurnedOn = turnedOn;
    }
}
