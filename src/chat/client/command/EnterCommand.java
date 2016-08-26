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
import java.net.URL;
import java.util.ResourceBundle;

public class EnterCommand implements Command {
    private ResourceBundle res
            = ResourceBundle.getBundle(Chat.RESOURCE_PATH + "enter_en");

    @Override
    public void execute() throws InterruptOperationException {
        GetThread th = new GetThread();
        th.setDaemon(true);
        th.start();

        while (true) {
            String text = ConsoleHelper.readString();
            if (text.isEmpty())
                break;

            Message m = new Message();
            m.setText(text);
            m.setFrom(LoginCommand.login);

            try {
                int send = m.send(res.getString("address.add"));
                if (send != 200) {
                    ConsoleHelper.writeMessage(res.getString("error.http") + send);
                    return;
                }
            } catch (IOException ex) {
                ConsoleHelper.writeMessage(res.getString("error") + ex.getMessage());
                return;
            }
        }
    }

    private class GetThread extends Thread {
        private int n;

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    URL url = new URL(res.getString("address.get") + n);
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
}


