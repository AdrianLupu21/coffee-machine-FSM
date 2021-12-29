package com.lup;

public enum States {
    IDLE,
    CONFIRMATION,
    PAYMENT,
    PREPARATION,
    MONEY_CHECK, // state which describes the verification of the current sold
    BANKNOTE_CHECK, // state which describes the verification of the physical banknotes inside the machine
    UNAVAILABLE,
    CASH_PAYMENT_CHECK,
    MONEY_BACK_CASH,
    CARD_PAYMENT_CHECK,
    QR_SHOW,
    GIVE_MONEY_BACK,
    WAIT_FOR_INPUT,
    PRODUCT_SELECTION

//    private States stateInput;
//
//    States
}
