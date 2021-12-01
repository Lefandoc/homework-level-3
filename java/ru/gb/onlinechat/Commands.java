package ru.gb.onlinechat;

public enum Commands {
    COMMAND_PREFIX("/"),
    AUTH_COMMAND(COMMAND_PREFIX.getCommand() + "auth"),
    SUCCESS_AUTH_COMMAND(COMMAND_PREFIX.getCommand() + "authok"),
    PRIVATE_MESSAGE_COMMAND(COMMAND_PREFIX.getCommand() + "w"),
    GET_CLIENTS_COMMAND(COMMAND_PREFIX.getCommand() + "clients"),
    END_COMMAND(COMMAND_PREFIX.getCommand() + "end");


    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
