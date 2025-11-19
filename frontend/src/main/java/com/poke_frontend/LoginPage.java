package com.poke_frontend;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginPage extends ScalePage{
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
        IO.print(usernameField.getText());
        IO.print(passwordField.getText());
        App.changeCurrentPage(Page.MAIN_MENU);
    }

    public void clickCreateAccountButton(ActionEvent actionEvent) {
        App.changeCurrentPage(Page.CREATE_ACCOUNT);
    }
}
