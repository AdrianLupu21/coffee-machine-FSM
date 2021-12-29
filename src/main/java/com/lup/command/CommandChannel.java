package com.lup.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CommandChannel{

    static {
        commandChannelActions = new CommandChannelActions();
        commandsQueue = new LinkedList<>();
        argumentQueue = new LinkedList<>();
    }

    public static CommandChannelActions commandChannelActions;

    static Queue<Commands> commandsQueue;
    static Queue<String> argumentQueue;



}
