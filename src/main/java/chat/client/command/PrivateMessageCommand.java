package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.Message;
import chat.client.exception.InterruptOperationException;

import java.io.IOException;
import java.util.ResourceBundle;

public class PrivateMessageCommand implements Command {
    private ResourceBundle res
            = ResourceBundle.getBundle("privateMessage_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("to"));
        String nickname = ConsoleHelper.readString();
        ConsoleHelper.writeMessage(res.getString("type"));

        PublicMessageCommand.GetThread th = new  PublicMessageCommand.GetThread();
        th.setDaemon(true);
        th.start();

        while (true) {
            String text = ConsoleHelper.readString();
            if (text.isEmpty()) {
                break;
            }

            Message m = new Message();
            m.setText(text);
            m.setFrom(LoginCommand.login);
            m.setTo(nickname);

            PublicMessageCommand.sendMessage(m);
        }
    }

}