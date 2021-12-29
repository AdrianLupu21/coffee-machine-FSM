import com.lup.States;
import com.lup.command.CommandChannel;
import com.lup.command.CommandChannelActions;
import com.lup.command.CommandProcessor;
import com.lup.machine.CoffeeMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Application was started");

        CommandProcessor commandProcessor = new CommandProcessor();
        Thread commandLineThread = new Thread(commandProcessor);
        while(true){
            String command = scanner.nextLine();
            if(command != null && !CoffeeMaker.getInstance().isTurnedOn()) {
                commandLineThread.start();
                CoffeeMaker.getInstance().setTurnedOn(true);
            }
            // exit the program
            if (command.equalsIgnoreCase("exit")) {
                break;
            } else if(command.equalsIgnoreCase("help")){
                //print help
            }else if(CoffeeMaker.getInstance().currentState.equals(States.WAIT_FOR_INPUT)){
                CommandChannel.commandChannelActions.addArgumentToQueue(command);
                synchronized (commandLineThread) {
                    commandLineThread.notify();
                }
            } else{
                CommandChannel.commandChannelActions.addCommandToQueue(command);
            }
            //commandLine.executeCommand(command);
        }
    }
}
