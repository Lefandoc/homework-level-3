package ru.gb.onlinechat.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRunner {

    public static void main(String[] args) {
        new ChatServer().run();
    }
}
