package com.lup.command;

public enum Commands {
    ADD_MONEY,
    ADD_SUGAR,
    GET_MONEY,
    SELECT_PRODUCT,
    STATUS, // for debug
    GET_REST,
    GO_BACK,
    UNKNOWN_COMMAND;

   public static Commands getCommandEnum(String command){
       if(command.equalsIgnoreCase("add money")){
           return ADD_MONEY;
       }else if(command.equalsIgnoreCase("add sugar")){
           return ADD_SUGAR;
       }else if(command.equalsIgnoreCase("get money")) {
           return GET_MONEY;
       }else if(command.equalsIgnoreCase("select coffee")) {
           return SELECT_PRODUCT;
       }else if(command.equalsIgnoreCase("status")){
           return STATUS;
       }else if(command.equalsIgnoreCase("get rest")) {
           return GET_REST;
       }else {
           return UNKNOWN_COMMAND;
       }
   }
}
