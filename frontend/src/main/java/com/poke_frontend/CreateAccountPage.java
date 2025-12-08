package com.poke_frontend;

import com.poke_frontend.dto.request.CreateAccountRequest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CreateAccountPage extends ScalePage{

    @FXML
    private Label usernameSame;

    @FXML
    private Label emailNull;

    @FXML
    private Label passwordNull;

    @FXML
    private PasswordField confirmPassword_Text;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button backButton;

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
    private Label usernameNull;

    @FXML
    private TextField username_Text;

    @FXML
    protected Pane rootPane;

    @FXML
    private Group groupScale;

    @FXML
    /**
     * Attempts to create a new account
     */
    void createAccount(ActionEvent event) {

        /* Getting the user input */
        String email = email_Text.getText(); // primary key
        String fname = fname_Text.getText();
        String lname = lname_Text.getText();
        String userName = username_Text.getText();
        String password = password_Text.getText();
        String confirmPass = confirmPassword_Text.getText();

        /* Checking if the input are valid */
        if(Blank(email, userName, password)) {

            /* Checking if the pass and conPass are the same*/
            if (samePass(password, confirmPass)) {
                /* Add information to the database*/
                 Client cli = new Client();
                 CreateAccountRequest  CAR = new CreateAccountRequest();

                 CAR.email = email;
                 CAR.name = lname + ", " + fname;
                 CAR.username = userName;
                 CAR.password = password;
                
                 boolean successful;
                 try {
                    successful = cli.createAccount(CAR);
                 } catch (Exception e) {
                    successful = false;
                 }
                
                 if (!successful) {
                    usernameSame.setVisible(true);
                 } else {
                    usernameSame.setVisible(false);
                    /* Let the user login */
                    App.changeCurrentPage(Page.LOGIN);
                 }
            }
        }
    }
    
    /**
     * Methods to validate user input
     */
    private boolean Blank (String email, String username, String password) {
        boolean emailBlank = false, usernameBlank = false, passwordBlank = false;

        if (email.isBlank()) {
            emailNull.setVisible(true);
            emailBlank = true;
        } else {
            emailNull.setVisible(false);
        }

        if (username.isBlank()) {
            usernameNull.setVisible(true);
            usernameBlank = true;
        } else {
            usernameNull.setVisible(false);
        }

        if (password.isBlank()) {
            passwordNull.setVisible(true);
            passwordBlank = true;
        } else {
            passwordNull.setVisible(false);
        }


        return !emailBlank && !usernameBlank && !passwordBlank;
    }

    /**
     * Checking pass and conPass are the same
     */
    private boolean samePass(String password, String conPass) {
        boolean same = password.equals(conPass);

        /* If pass != conPass*/
        if (!same) {
            /* Set the error message */
            passwordError.setVisible(true);
        }
        return same;
    }

    @FXML
    void backToLogin(ActionEvent event){
        App.changeCurrentPage(Page.LOGIN);
    }


    /**
     * Scale the page
     */
    @FXML   
    void initialize(){ 
        implementScaling(groupScale, rootPane);  
    }

}
