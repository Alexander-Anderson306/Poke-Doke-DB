package com.poke_frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PackShopPage extends ScalePage{

    @FXML
    private Button accountButton_view;

    @FXML
    private Button homeButton_View;

    @FXML
    private Label pack1_Name;

    @FXML
    private Label pack2_Name;

    @FXML
    private Label pack3_Name;

    @FXML
    private ImageView packSlotID1;

    @FXML
    private ImageView packSlotID2;

    @FXML
    private ImageView packSlotID3;

    @FXML
    private Label username_label;

    @FXML
    protected Pane rootPane;

    @FXML
    private Group groupScale;

    @FXML    
    void initialize(){ 
        implementScaling(groupScale, rootPane);  
    }
    

    @FXML
    void buyPack(MouseEvent event) {

        //Get the current Pack slot
        ImageView currentPack = (ImageView) event.getSource();

        //Print the pack's ID
        System.out.println("Pack bought was: " + currentPack.getId());
    }

    @FXML
    void goToLogin(ActionEvent event) {

    }

    @FXML
    void goToMainMenu(ActionEvent event) {

    }

}
