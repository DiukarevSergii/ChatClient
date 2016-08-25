package chat.client;

import chat.client.exception.InterruptOperationException;

import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleHelper {
    public static Scanner scanner = new Scanner(System.in);
    public static ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String line = "";
        line = scanner.nextLine().trim();
        if (line.equalsIgnoreCase("EXIT")) {
            ConsoleHelper.writeMessage(res.getString("the.end"));
            throw new InterruptOperationException();
        }
        return line;
    }

    public static Operation askOperation() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.operation"));
        Operation operation = null;
        while (operation == null) {
            try {
                ConsoleHelper.writeMessage(String.format("Select the operation: 1 - %s, 2 - %s, 3 - %s, 4 - %s;",
                        res.getString("operation.INFO"),
                        res.getString("operation.EXIT")));

                operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));
            } catch (IllegalArgumentException e) {
                operation = null;
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
        return operation;
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
