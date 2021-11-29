package ru.gb.onlinechat.server;

public interface AuthService {

    String getNickByLoginAndPassword(String login, String password);
}
