module com.poke_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    exports com.poke_frontend;
    opens com.poke_frontend to javafx.fxml;
}
