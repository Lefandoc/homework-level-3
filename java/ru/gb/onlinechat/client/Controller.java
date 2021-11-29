package ru.gb.onlinechat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.List;

import static ru.gb.onlinechat.Commands.*;

public class Controller {

    @FXML
    private HBox loginBox;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authBtn;
    @FXML
    private HBox messageBox;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private ListView<String> clientList;

    private final ChatClient client;

    public Controller() {
        client = new ChatClient(this);
        client.openConnection();
    }

    public void btnAuthClick(ActionEvent event) {
        client.sendMessage(AUTH_COMMAND.getCommand() + " " + loginField.getText() + " " + passwordField.getText());
    }

    public void btnSendClick(ActionEvent event) {
        final String message = textField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        client.sendMessage(message);
        textField.clear();
        textField.requestFocus();
    }

    public void addMessage(String message) {
        textArea.appendText(message + "\n");
    }

    public void setAuth(boolean isClientAuth) {
        loginBox.setVisible(!isClientAuth);
        messageBox.setVisible(isClientAuth);
    }

    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            final String message = textField.getText();
            final String nick = clientList.getSelectionModel().getSelectedItem();
            textField.setText(PRIVATE_MESSAGE_COMMAND.getCommand() + " " + nick + " " + message);
            textField.requestFocus();
            textField.selectEnd();
        }
    }

    public void updateClientList(List<String> clients) {
        clientList.getItems().clear();
        clientList.getItems().addAll(clients);
    }

    public void btnExitClick(ActionEvent event) {
        System.exit(0);
    }
}