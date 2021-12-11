module ru.gb.onlinechat.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens ru.gb.onlinechat.client to javafx.fxml;
    exports ru.gb.onlinechat.client;
}