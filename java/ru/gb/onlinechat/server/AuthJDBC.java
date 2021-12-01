package ru.gb.onlinechat.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthJDBC {
    private Connection connection;
    private Statement statement;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:AuthData.db");
        connection.setAutoCommit(false);
        statement = connection.createStatement();
        System.out.println("Success connection to database");
    }

    void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() throws SQLException {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "login TEXT," +
                    "password TEXT," +
                    "nickname TEXT" +
                    ");");
            System.out.println("Table users created");
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void insert(String login, String password, String nickname) throws SQLException {
        try (final PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO users(login, password, nickname) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nickname);
            preparedStatement.executeUpdate();
        }
    }

    public ResultSet select(Integer id) throws SQLException {
        try (final PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.printf("%d - %s - %s - %s\n",
                        resultSet.getInt(1), // id
                        resultSet.getString(2), // login
                        resultSet.getString(3), // pass
                        resultSet.getString(4)); // nick

            }
            return resultSet;
        }
    }

    public String selectLogin(Integer id) throws SQLException {
        try (final PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT login FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
        return null;
    }

    public String selectPassword(Integer id) throws SQLException {
        try (final PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT password FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
        return null;
    }

    public String selectNickname(Integer id) throws SQLException {
        try (final PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT nickname FROM users WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        }
        return null;
    }

    public void dropTable() throws SQLException {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }
}
