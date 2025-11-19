package com.poke_frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainPage extends ScalePage{

    @FXML
    private Button databaseButton_Main;

    @FXML
    private Button exitButton_Menu;

    @FXML
    private Button inventoryButton_Main;

    @FXML
    private Button logOutButton_Menu;

    @FXML
    private Button shopButton_Main;

    @FXML
    protected Pane rootPane;

    @FXML
    private Group groupScale;

    @FXML    
    void initialize(){ 
        implementScaling(groupScale, rootPane);  
    }


    @FXML
    void closeApplication(ActionEvent event) {

    }

    @FXML
    void goToDatabase(ActionEvent event) {
        App.changeCurrentPage(Page.VIEW_DATABASE);
    }

    @FXML
    void goToInventory(ActionEvent event) {
        App.changeCurrentPage(Page.VIEW_INVENTORY);
    }

    @FXML
    void goToShop(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {
        App.changeCurrentPage(Page.LOGIN);
    }

}
