package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.exception.InterruptOperationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusCommand implements Command {
    private ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "status_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("type"));
        String login = ConsoleHelper.readString();
        try {
            URL url = new URL(String.format(res.getString("address"), login));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200){
                ConsoleHelper.writeMessage(String.format(res.getString("online"), login));
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("offline"), login));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
