package chat.client.command;

import chat.client.ConsoleHelper;
import chat.client.exception.InterruptOperationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StatusCommand implements Command {
    //TODO

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("Please, type the nickname below: ");
        String login = ConsoleHelper.readString();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(String.format("http://localhost:2408/status?login=%s", login));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200){
                ConsoleHelper.writeMessage(String.format("\tUser \"%s\" is online", login));
            } else {
                ConsoleHelper.writeMessage(String.format("\tUser \"%s\" is offline", login));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
