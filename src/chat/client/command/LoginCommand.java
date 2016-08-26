package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.Message;
import chat.client.exception.InterruptOperationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "login_en");

    public static String login;

    @Override
    public void execute() throws InterruptOperationException {
        login = authorization();
        ConsoleHelper.writeMessage(res.getString("success.format"));
    }

    private String authorization() throws InterruptOperationException {
        Locale.setDefault(Locale.ENGLISH);

        ConsoleHelper.writeMessage(res.getString("before"));
        String login = "";

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
        return login;
    }
}


