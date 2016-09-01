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

public class PublicMessageCommand implements Command {
    private static ResourceBundle res
            = ResourceBundle.getBundle("publicMessage_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("type"));

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

            if (sendMessage(m)) return;
        }
    }

    static boolean sendMessage(Message m) {
        try {
            int send = m.send(res.getString("address.add"));
            if (send != 200) {
                ConsoleHelper.writeMessage(res.getString("error.http") + send);
                return true;
            }
        } catch (IOException ex) {
            ConsoleHelper.writeMessage(res.getString("error") + ex.getMessage());
            return true;
        }
        return false;
    }

    static class GetThread extends Thread {
        private int n;

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    URL url = new URL(String.format(res.getString("address.get"), n));
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();

                    try (InputStream is = http.getInputStream()) {
                        int sz = is.available();
                        if (sz > 0) {
                            byte[] buf = new byte[is.available()];
                            is.read(buf);

                            Gson gson = new GsonBuilder().create();
                            Message[] list = gson.fromJson(new String(buf), Message[].class);

                            for (Message m : list) {
                                if (m.getTo().equals(LoginCommand.login) || m.getTo().equals("ALL")) {
                                    ConsoleHelper.writeMessage(m.toString());
                                }
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


