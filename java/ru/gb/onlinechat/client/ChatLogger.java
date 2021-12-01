package ru.gb.onlinechat.client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChatLogger {
    private static File file;

    public void writeLogs(String msg) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLogs() {
        int messagesScope = 100;
        int currentLine = 0;
        if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Old messages:\n");
            String message;
            try (BufferedReader reader1 = new BufferedReader(new FileReader(file))) {
                while ((message = reader1.readLine()) != null) {
                    currentLine++;
                    if (Files.lines(Path.of(file.toURI())).count() - currentLine < messagesScope) {
                        stringBuilder.append(message).append("\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            stringBuilder.append("Old messages:\n");
            return stringBuilder.toString();
        }
        return "No old messages here";
    }

    public void setLogFile(String user) {
        file = new File("history_" + user + ".txt");
    }

}
