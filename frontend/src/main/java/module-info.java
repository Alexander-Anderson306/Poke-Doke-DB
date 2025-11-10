module com.poke_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    exports com.poke_frontend;
    opens com.poke_frontend to javafx.fxml;
}
