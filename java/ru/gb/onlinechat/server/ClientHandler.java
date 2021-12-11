package ru.gb.onlinechat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.gb.onlinechat.Commands.*;

public class ClientHandler {
    //private static final String COMMAND_PREFIX = Commands.COMMAND_PREFIX.getCommand();
    //private static final String SEND_MESSAGE_TO_CLIENT_COMMAND = COMMAND_PREFIX + "w";
    //private static final String Commands.END_COMMAND = COMMAND_PREFIX + "end";
    private final Socket socket;
    private final ChatServer server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String nick;

    public ClientHandler(Socket socket, ChatServer server) {
        try {
            this.nick = "";
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            ExecutorService executor = Executors.newFixedThreadPool(new SimpleAuthService().getUSERS_COUNT());
            executor.execute(() -> {
                try {
                    authenticate();
                    readMessages();
                } finally {
                    closeConnection();
                }
            });
            executor.shutdown();

//            new Thread(() -> {
//                try {
//                    authenticate();
//                    readMessages();
//                } finally {
//                    closeConnection();
//                }
//            }).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeConnection() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                server.unsubscribe(this);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticate() {
        while (true) {
            try {
                final String str = in.readUTF();
                if (str.startsWith(AUTH_COMMAND.getCommand())) {
                    final String[] split = str.split(" ");
                    final String login = split[1];
                    final String password = split[2];
                    final String nick = server.getAuthService().getNickByLoginAndPassword(
                            login,
                            password
                    );
                    if (nick != null) {
                        if (server.isNickBusy(nick)) {
                            sendMessage("Пользователь уже авторизован");
                            continue;
                        }
                        sendMessage(SUCCESS_AUTH_COMMAND.getCommand() + " " + nick);
                        this.nick = nick;
                        if (!nick.equals("disconnected")) {
                            server.broadcast("Пользователь " + nick + " зашел в чат");
                            server.subscribe(this);
                        }
                        break;
                    } else {
                        sendMessage("Неверные логин или пароль");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(String message) {
        try {
            System.out.println("SERVER: Send message to " + nick);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() {
        try {
            while (true) {
                final String msg = in.readUTF();
                System.out.println("Receive message from " + nick + ": " + msg);
                if (msg.startsWith(COMMAND_PREFIX.getCommand())) {
                    if (END_COMMAND.getCommand().equals(msg)) {
                        break;
                    }
                    if (msg.startsWith(PRIVATE_MESSAGE_COMMAND.getCommand())) {
                        final String[] token = msg.split(" ");
                        final String nick = token[1];
                        server.privateMessage(this, nick, msg.substring(PRIVATE_MESSAGE_COMMAND.getCommand().length() + 2 + nick.length()));
                    }
                    continue;
                }
                server.broadcast(nick + ": " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

}
