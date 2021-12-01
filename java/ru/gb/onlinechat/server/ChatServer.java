package ru.gb.onlinechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static ru.gb.onlinechat.Commands.*;

public class ChatServer {

    private static final int PORT = 8189;

    private final AuthService authService;
    private final Map<String, ClientHandler> clients;

    public ChatServer() {
        this.authService = new SimpleAuthService();
        this.clients = new HashMap<>();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Wait client connection...");
                final Socket socket = serverSocket.accept();
                new ClientHandler(socket, this);
                System.out.println("Client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isNickBusy(String nick) {
        return clients.containsKey(nick);
    }

    public void subscribe(ClientHandler client) {
        if (client.getNick().equals("disconnected")) {
            clients.remove("disconnected");
        }
        clients.put(client.getNick(), client);
        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler client) {
        if (!client.getNick().equals("disconnected")) {
            clients.remove(client.getNick());
            broadcastClientsList();
        }
    }

    public void privateMessage(ClientHandler from, String nickTo, String message) {
        final ClientHandler client = clients.get(nickTo);
        if (client != null) {
            client.sendMessage("от " + from.getNick() + ": " + message);
            from.sendMessage("участнику " + nickTo + ": " + message);
            return;
        }
        from.sendMessage("Участника с ником " + nickTo + " нет в чат-комнате");
    }

    public void broadcastClientsList() {
        StringBuilder clientsCommand = new StringBuilder(GET_CLIENTS_COMMAND.getCommand() + " ");
        for (ClientHandler client : clients.values()) {
            if (!client.getNick().equals("disconnected")) {
                clientsCommand.append(client.getNick()).append(" ");
            }
        }
        broadcast(clientsCommand.toString());
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients.values()) {
            if (!client.getNick().equals("disconnected")) {
                client.sendMessage(message);
            }
        }
    }
}
