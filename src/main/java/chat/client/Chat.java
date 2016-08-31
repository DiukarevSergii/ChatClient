package chat.client;

import chat.client.command.CommandExecutor;
import chat.client.exception.InterruptOperationException;

import java.util.Locale;

public class Chat {

    public static final String RESOURCE_PATH = "chat.client.resources.";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
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
