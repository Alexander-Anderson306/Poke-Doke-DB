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

    /**
     * When the user clicks the login button, this method will create
     * a login request object and send it to the database.
     * If successful, theClient field in App will be set to the current
     * client, and the user will be brought to the main menu.
     * Otherwise, they will receive a warning that the login failed
     * and nothing will happen.
     */
    @FXML
    void clickLoginButton(ActionEvent event) {

        // Unused test code written by someone else.
        if(usernameField.getText().equals("test") || passwordField.getText().equals("test")) {
            Client cli = new Client();
            try {
                IO.println(cli.test());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Create a new client and request
        Client client = new Client();
        LoginRequest request = new LoginRequest();
        request.username = usernameField.getText();
        request.password = passwordField.getText();

        // Send that request to the database using the newly created client.
        boolean success;
        try {
            success = client.login(request);
            App.theClient = client;
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        // If successful, save the client and switch to the main menu.
        if (success) {
            App.changeCurrentPage(Page.MAIN_MENU);
            App.theClient = client;
        }

        // Otherwise print an error message.
        else {
            errorMessageLabel.setText("Login failed, please try again.");
        }

    }

    /**
     * Brings the user to the create account page.
     */
    public void clickCreateAccountButton(ActionEvent actionEvent) {
        App.changeCurrentPage(Page.CREATE_ACCOUNT);
    }
}
