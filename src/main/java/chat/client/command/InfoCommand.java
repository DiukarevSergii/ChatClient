package chat.client.command;

import chat.client.Chat;
import chat.client.ConsoleHelper;
import chat.client.exception.InterruptOperationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class InfoCommand implements Command {
    private ResourceBundle res
            = ResourceBundle.getBundle("info_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("now.online"));

        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:2408/authorization");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream is = connection.getInputStream()) {
            int sz = is.available();
            if (sz > 0) {
                byte[] buf = new byte[is.available()];
                is.read(buf);

                Gson gson = new GsonBuilder().create();
                Set<String> usersOnline = gson.fromJson(new String(buf), Set.class);
                String online = "";
                for (String user : usersOnline) {
                        online += user + ", ";
                }
                ConsoleHelper.writeMessage(online.substring(0, online.length() - 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
