package ru.gb.onlinechat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static ru.gb.onlinechat.Commands.*;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private final Controller controller;

    public ChatClient(Controller controller) {
        this.controller = controller;
    }

    public void openConnection() {
        try {
            socket = new Socket(HOST, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int authTimer = 30;
                public void run() {
                    System.out.println("Time for auth remained: " + authTimer + " seconds");
                    authTimer = authTimer - 5;
                    if (authTimer <= 0) {
                        timer.cancel();
                        sendMessage(AUTH_COMMAND.getCommand() + " " + "autologin" + " " + "autopass");
                        controller.setAuth(true);
                        sendMessage("/end");
                    }
                }
            };
            System.out.println("Chat client started: " + new Date());
            timer.schedule(task, new Date(), 5000);
            new Thread(() -> {
                try {
                    while (true) {
                        final String msgAuth = in.readUTF();
                        if (msgAuth.startsWith(COMMAND_PREFIX.getCommand() + "authok")) {
                            final String[] split = msgAuth.split(" ");
                            final String nick = split[1];
                            controller.addMessage("Успешная авторизация под ником " + nick);
                            controller.setAuth(true);
                            timer.cancel();
                            break;
                        }
                    }
                    while (true) {
                        final String message = in.readUTF();
                        System.out.println("Receive message: " + message);
                        if (message.startsWith(COMMAND_PREFIX.getCommand())) {
                            if (END_COMMAND.getCommand().equals(message)) {
                                controller.setAuth(false);
                                break;
                            }
                            if (message.startsWith(GET_CLIENTS_COMMAND.getCommand())) {
                                final String[] tokens = message.replace(GET_CLIENTS_COMMAND.getCommand() + " ", "").split(" ");
                                final List<String> clients = Arrays.asList(tokens);
                                if (!clients.contains("disconnected")) {
                                    controller.updateClientList(clients);
                                    controller.addMessage(message.replace(GET_CLIENTS_COMMAND.getCommand(), "Online now:"));
                                }
                                continue;
                            }
                        }
                        controller.addMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void sendMessage(String message) {
        try {
            System.out.println("Send message: " + message);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}