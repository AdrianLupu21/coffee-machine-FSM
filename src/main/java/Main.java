import com.lup.States;
import com.lup.command.CommandChannel;
import com.lup.command.CommandChannelActions;
import com.lup.command.CommandProcessor;
import com.lup.machine.CoffeeMaker;
import com.lup.watcher.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Application was started");

        CommandProcessor commandProcessor = new CommandProcessor();
        Watcher watcher = new Watcher();
        Thread commandLineThread = new Thread(commandProcessor);
        Thread watcherThread = new Thread(watcher);
        while(true){
            String command = scanner.nextLine();
            if(command != null && !CoffeeMaker.getInstance().isTurnedOn()) {
                commandLineThread.start();
                watcherThread.start();
                CoffeeMaker.getInstance().setTurnedOn(true);
            }
            // exit the program
            if (command.equalsIgnoreCase("exit")) {
                CoffeeMaker.getInstance().setTurnedOn(false);
                commandLineThread.interrupt();
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
        }
    }
}
