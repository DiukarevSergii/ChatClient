package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.exception.InterruptOperationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle res
            = ResourceBundle.getBundle("login_en");

    public static String login;

    @Override
    public void execute() throws InterruptOperationException {
        Locale.setDefault(Locale.ENGLISH);
        ConsoleHelper.writeMessage(res.getString("before"));

        HttpURLConnection connection;
        while (true) {
            ConsoleHelper.writeMessage(res.getString("enter.login"));
            login = ConsoleHelper.readString();
            ConsoleHelper.writeMessage(res.getString("enter.password"));
            String pass = ConsoleHelper.readString();

            try {
                URL url = new URL(String.format(res.getString("address.login"), login, pass));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                if (connection.getResponseCode() == 200) {
                    break;
                } else {
                    System.out.println(connection.getResponseCode());
                    ConsoleHelper.writeMessage(res.getString("incorrect"));
                    ConsoleHelper.writeMessage(res.getString("specify.data"));
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ConsoleHelper.writeMessage("");
        }
        ConsoleHelper.writeMessage(res.getString("success.format"));
    }
}


