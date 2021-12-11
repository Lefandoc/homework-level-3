package ru.gb.onlinechat.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {

    private final List<UserData> users;
    private final AuthJDBC authJDBC;
    private final int USERS_COUNT = 5; // +1 для корректной отработки циклов for



    public SimpleAuthService() {
        authJDBC = new AuthJDBC();
        users = new ArrayList<>();
        try {
            authJDBC.connect();
            authJDBC.dropTable();
            authJDBC.createTable();
            authJDBC.insert("autologin", "autopass", "disconnected");
            for (int i = 0; i < USERS_COUNT; i++) {
                authJDBC.insert("login" + i, "pass" + i, "nick" + i);
            }
            for (int i = 1; i <= USERS_COUNT + 1; i++) {
                users.add(new UserData(authJDBC.selectLogin(i),
                        authJDBC.selectPassword(i),
                        authJDBC.selectNickname(i)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            authJDBC.disconnect();
        }
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        for (UserData user : users) {
            if (user.login.equals(login) && user.password.equals(password)) {
                return user.nick;
            }
        }
        return null;
    }

    private static class UserData {
        private final String login;
        private final String password;
        private final String nick;

        public UserData(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }

        @Override
        public String toString() {
            return "User" + login + password + nick;
        }
    }

    public int getUSERS_COUNT() {
        return USERS_COUNT;
    }
}
