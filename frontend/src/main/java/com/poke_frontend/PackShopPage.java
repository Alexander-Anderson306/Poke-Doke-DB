package com.poke_frontend;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class PackShopPage extends ScalePage{

     @FXML
    private Button accountButton_view;

    @FXML
    private GridPane gird_View;

    @FXML
    private Group groupScale;

    @FXML
    private Button homeButton_View;

    @FXML
    private Pane rootPane;

    @FXML
    private ScrollPane scrollView;

    @FXML
    private Label username_label;

    @FXML    
    void initialize(){ 
        implementScaling(groupScale, rootPane);  
        loadPacks(null);
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

    void loadPacks(List<String>urlList){

        //A temp image
        String tempImg = "/TempImages/PokemonPack1.png";

        //Get the amount of images from list
        int amountImgs = 4;

        //calulate the amount of rows needed in the grid pane
        int amountOfColumns = amountImgs;

        //Keep count of current image
        int currentImg = 0;

        //Beauify a row
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(256);
        rc.setPrefHeight(256);
        rc.setMaxHeight(256);
        rc.setValignment(VPos.CENTER);
        
        //Beauify a column
        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(200);
        cc.setPrefWidth(200);
        cc.setMaxWidth(200);
        cc.setHalignment(HPos.CENTER);

        //for every column add the pack image
        for(int col = 0; col < amountOfColumns; col++){


            //If counter is more than the amount of images, stop loop
            if(currentImg >= amountImgs)
                break;

            //Create a image of current
            Image img = new Image(getClass().getResourceAsStream(tempImg));
            ImageView imgView = new ImageView(img);

            //Set the image height and width
            imgView.setFitWidth(150);
            imgView.setFitHeight(256);
            imgView.setPreserveRatio(true);
            imgView.setId("Pack #" + (col + 1));
            imgView.setOnMouseClicked(event ->{
                buyPack(event);
            });

            //Add the image to the gridview
            gird_View.add(imgView, col, 0);

            //Go to next index
            currentImg++;
            
            //Beauify column
            gird_View.getColumnConstraints().add(cc);

        }



    }



}
