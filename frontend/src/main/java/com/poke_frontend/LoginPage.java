package com.poke_frontend;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginPage {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button clickLoginButton;

    @FXML
    void clickLoginButton(ActionEvent event) {
        IO.print(usernameField.getText());
        IO.print(passwordField.getText());
    }
}
