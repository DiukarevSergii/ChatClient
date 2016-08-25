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
import java.util.Map;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        String login = authorization();

        GetThread th = new GetThread();
        th.setDaemon(true);
        th.start();

        while (true) {
            String text = ConsoleHelper.readString();
            if (text.isEmpty())
                break;

            Message m = new Message();
            m.setText(text);
            m.setFrom(login);

            try {
                int res = m.send("http://localhost:2408/add");
                if (res != 200) {
                    System.out.println("HTTP error: " + res);
                    return;
                }
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                return;
            }
        }
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
                URL url = new URL(String.format("http://localhost:2408/authorization?login=%s&pass=%s", login, pass));
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
            ConsoleHelper.readString();
        }
        ConsoleHelper.writeMessage(res.getString("now.online"));
        try (InputStream is = connection.getInputStream()) {
            int sz = is.available();
            if (sz > 0) {
                byte[] buf = new byte[is.available()];
                is.read(buf);

                Gson gson = new GsonBuilder().create();
                Map<String, Boolean> usersOnline = gson.fromJson(new String(buf), Map.class);
                String online = "";
                for (Map.Entry<String, Boolean> pair: usersOnline.entrySet()){
                    if (pair.getValue() == true){
                        online += pair.getKey() + ", ";
                    }
                }
                ConsoleHelper.writeMessage(online.substring(0, online.length() - 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return login;
    }
}

class GetThread extends Thread {
    private int n;

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                URL url = new URL("http://localhost:2408/get?from=" + n);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                try (InputStream is = http.getInputStream()) {
                    int sz = is.available();
                    if (sz > 0) {
                        byte[] buf = new byte[is.available()];
                        is.read(buf);

                        Gson gson = new GsonBuilder().create();
                        Message[] list = gson.fromJson(new String(buf), Message[].class);

                        for (Message m : list) {
                            ConsoleHelper.writeMessage(m.toString());
                            n++;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}
