package com.poke_frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountPage {

    @FXML
    private PasswordField confirmPassword_Text;

    @FXML
    private Button createAccountButton;

    @FXML
    private TextField email_Text;

    @FXML
    private TextField fname_Text;

    @FXML
    private TextField lname_Text;

    @FXML
    private Label passwordError;

    @FXML
    private PasswordField password_Text;

    @FXML
    private Label usernameError;

    @FXML
    private TextField username_Text;

    @FXML
    void createAccount(ActionEvent event) {

    }

}
