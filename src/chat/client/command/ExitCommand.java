package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command {

    private ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "exit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));

        String answer = ConsoleHelper.readString();
        if (answer.equalsIgnoreCase(res.getString("yes"))) {
            ConsoleHelper.writeMessage(res.getString("thank.message"));
        }
    }
}