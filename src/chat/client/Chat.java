package chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import chat.client.command.CommandExecutor;
import chat.client.exception.InterruptOperationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//class GetThread extends Thread {
//    private int n;
//
//    @Override
//    public void run() {
//        try {
//            while (!isInterrupted()) {
//                URL url = new URL("http://localhost:2408/get?from=" + n);
//                HttpURLConnection http = (HttpURLConnection) url.openConnection();
//
//                try (InputStream is = http.getInputStream()) {
//                    int sz = is.available();
//                    if (sz > 0) {
//                        byte[] buf = new byte[is.available()];
//                        is.read(buf);
//
//                        Gson gson = new GsonBuilder().create();
//                        Message[] list = gson.fromJson(new String(buf), Message[].class);
//
//                        for (Message m : list) {
//                            System.out.println(m);
//                            n++;
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return;
//        }
//    }
//}

public class Chat {

    private static Scanner scanner = new Scanner(System.in);
    public static final String RESOURCE_PATH = "chat.client.resources.";

//    private static String authorization() {
//        Locale.setDefault(Locale.ENGLISH);
//
//        System.out.println("Please log in:");
//        String login = "";
//
//        HttpURLConnection connection;
//        while (true) {
//            System.out.println("\tEnter login:");
//            login = scanner.nextLine();
//            System.out.println("\tEnter password: ");
//            String pass = scanner.nextLine();
//
//
//            try {
//                URL url = new URL(String.format("http://localhost:2408/authorization?login=%s&pass=%s", login, pass));
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoOutput(true);
//                if (connection.getResponseCode() == 200) {
//                    break;
//                } else {
//                    System.out.println("Incorrect login or password!!!");
//                    System.out.println("Do you want try again? (Y / N)");
//                    if (!scanner.nextLine().equalsIgnoreCase("y")) {
//                        System.exit(0);
//                    }
//                }
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println();
//        }
//        System.out.println("Now online: ");
//        try (InputStream is = connection.getInputStream()) {
//            int sz = is.available();
//            if (sz > 0) {
//                byte[] buf = new byte[is.available()];
//                is.read(buf);
//
//                Gson gson = new GsonBuilder().create();
//                Map<String, Boolean> usersOnline = gson.fromJson(new String(buf), Map.class);
//                String online = "";
//                for (Map.Entry<String, Boolean> pair: usersOnline.entrySet()){
//                    if (pair.getValue() == true){
//                        online += pair.getKey() + ", ";
//                    }
//                }
//                System.out.println(online.substring(0, online.length() - 2));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return login;
//    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Operation operation;
            CommandExecutor.execute(Operation.LOGIN);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

            } while (!operation.equals(Operation.EXIT));
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }














//        String login = authorization();
//
//        GetThread th = new GetThread();
//        th.setDaemon(true);
//        th.start();
//
//        while (true) {
//            String text = scanner.nextLine();
//            if (text.isEmpty())
//                break;
//
//            Message m = new Message();
//            m.setText(text);
//            m.setFrom(login);
//
//            try {
//                int res = m.send("http://localhost:2408/add");
//                if (res != 200) {
//                    System.out.println("HTTP error: " + res);
//                    return;
//                }
//            } catch (IOException ex) {
//                System.out.println("Error: " + ex.getMessage());
//                return;
//            }
//        }
    }
}
