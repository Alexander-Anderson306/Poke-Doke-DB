package com.poke_frontend;

import com.poke_frontend.dto.request.LoginRequest;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginPage extends ScalePage{

    @FXML
    public Label errorMessageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button clickLoginButton;

    @FXML
    protected Pane rootPane;

    @FXML
    private Group groupScale;

    @FXML    
    void initialize(){
        implementScaling(groupScale, rootPane);
    }

    @FXML
    void clickLoginButton(ActionEvent event) {

        // TEMP CODE FOR TESTING PURPOSES, DELETE BEFORE PUBLISHING
        if (usernameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            App.changeCurrentPage(Page.MAIN_MENU);
            return;
        }

        if(usernameField.getText().equals("test") || passwordField.getText().equals("test")) {
            Client cli = new Client();
            try {
                IO.println(cli.test());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        Client client = new Client();

        LoginRequest request = new LoginRequest();
        request.username = usernameField.getText();
        request.password = passwordField.getText();

        boolean success;
        try {
            success = client.login(request);
            App.theClient = client;
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        if (success) {
            App.changeCurrentPage(Page.MAIN_MENU);
            App.theClient = client;
        }
        else {
            errorMessageLabel.setText("Login failed, please try again.");
        }

    }

    public void clickCreateAccountButton(ActionEvent actionEvent) {
        App.changeCurrentPage(Page.CREATE_ACCOUNT);
    }
}
