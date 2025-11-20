package com.poke_frontend;

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
    protected Pane rootPane;

    @FXML
    private Group groupScale;
    

    private static final String url = "";
    private static final String root = "";
    private static final String db_password = "";

    @FXML
    void createAccount(ActionEvent event) {
        String email = email_Text.getText(); // primary key
        String fname = fname_Text.getText();
        String lname = lname_Text.getText();
        String userName = username_Text.getText();
        String password = password_Text.getText();
        String confirmPass = confirmPassword_Text.getText();

        if(Blank(email, fname, lname, userName, password)) {
            if (samePass(password, confirmPass)) {
                App.changeCurrentPage(Page.LOGIN);
            }
        }
    }

    private boolean Blank (String email, String fname, String lname, String username, String password) {
        boolean emailBlank = false, fnameBlank = false, lnameBlank = false, usernameBlank = false, passwordBlank = false;

        if (email.isBlank()) {
            System.out.println("email is blank.");
            emailBlank = true;
        }

        if (fname.isBlank()) {
            System.out.println("first name is blank.");
            fnameBlank = true;
        }

        if (lname.isBlank()) {
            System.out.println("last name is blank.");
            lnameBlank = true;
        }

        if (username.isBlank()) {
            System.out.println("username is blank.");
            usernameBlank = true;
        }

        if (password.isBlank()) {
            System.out.println("password is blank.");
            passwordBlank = true;
        }

        return !emailBlank && !fnameBlank && !lnameBlank && !usernameBlank && !passwordBlank;
    }

    private boolean samePass(String password, String conPass) {
        return password.equals(conPass);
    }

    @FXML    
    void initialize(){ 
        implementScaling(groupScale, rootPane);  
    }

}
