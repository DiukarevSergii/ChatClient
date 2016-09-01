package chat.client;

import chat.client.command.CommandExecutor;
import chat.client.exception.InterruptOperationException;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class Chat {

//    public static String RESOURCE_PATH = "resources.";


    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
//        ResourceBundle rb = ResourceBundle.getBundle("exit_en");
//
//        System.out.println(rb);

        try {
            Operation operation;
            CommandExecutor.execute(Operation.LOGIN);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } while (!operation.equals(Operation.EXIT));
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
