module com.poke_frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.net.http;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    exports com.poke_frontend;

    exports com.poke_frontend.dto.request;
    exports com.poke_frontend.dto.response;
    exports com.poke_frontend.models;

    opens com.poke_frontend to javafx.fxml;

    opens com.poke_frontend.dto.request to com.fasterxml.jackson.databind;
    opens com.poke_frontend.dto.response to com.fasterxml.jackson.databind;
}
