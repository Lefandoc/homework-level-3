package ru.gb.onlinechat.server;

import java.util.concurrent.ThreadFactory;

public interface AuthService {

    String getNickByLoginAndPassword(String login, String password);

}
