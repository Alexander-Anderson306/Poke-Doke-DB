package com.poke_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginPage {

    public static void main(String[] args) {
        Application.launch(LoginApplication.class, args);
    }

    public static void attemptToLogin(String username, String password) {

    }

}

class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }
}

class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void clickLoginButton(ActionEvent event) {
        LoginPage.attemptToLogin(usernameField.getText(), passwordField.getText());
    }
}
