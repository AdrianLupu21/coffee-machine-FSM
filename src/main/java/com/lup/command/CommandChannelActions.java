package com.lup.command;

public class CommandChannelActions extends CommandChannel {

    public void addCommandToQueue(String command){
        Commands commandEnum = Commands.getCommandEnum(command);
        commandsQueue.add(commandEnum);
    }

    public Commands getCommandFromChannel(){
        return commandsQueue.poll();
    }

    public void addArgumentToQueue(String argument){
        argumentQueue.add(argument);
    }

    public String getArgumentFromQueue(){
        return argumentQueue.poll();
    }

}
