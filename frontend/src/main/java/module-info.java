module com.poke_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.poke_frontend;
    opens com.poke_frontend to javafx.fxml;
}
