module ru.gb.onlinechat.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ru.gb.onlinechat.client to javafx.fxml;
    exports ru.gb.onlinechat.client;
}