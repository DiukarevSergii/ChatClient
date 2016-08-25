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














//        String login = authorization();
//
//        GetThread th = new GetThread();
//        th.setDaemon(true);
//        th.start();
//
//        while (true) {
//            String text = scanner.nextLine();
//            if (text.isEmpty())
//                break;
//
//            Message m = new Message();
//            m.setText(text);
//            m.setFrom(login);
//
//            try {
//                int res = m.send("http://localhost:2408/add");
//                if (res != 200) {
//                    System.out.println("HTTP error: " + res);
//                    return;
//                }
//            } catch (IOException ex) {
//                System.out.println("Error: " + ex.getMessage());
//                return;
//            }
//        }
    }
}
