package com.poke_frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.image.Image;

import java.util.List;

public class ViewPage {

    @FXML
    private Button accountButton_view;

    @FXML
    private Button buyButton_View;

    @FXML
    private AnchorPane cardView;

    @FXML
    private Button homeButton_View;

    @FXML
    private ImageView imageSlot;

    @FXML
    private ImageView imageSlot1;

    @FXML
    private ImageView imageSlot11;

    @FXML
    private ImageView imageSlot111;

    @FXML
    private ImageView imageSlot1111;

    @FXML
    private ImageView imageSlot11111;

    @FXML
    private ImageView imageSlot112;

    @FXML
    private ImageView imageSlot1121;

    @FXML
    private ImageView imageSlot12;

    @FXML
    private ImageView imageSlot121;

    @FXML
    private ImageView imageSlot2;

    @FXML
    private ImageView imageSlot21;

    @FXML
    private ScrollPane scrollView;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton_View;

    @FXML
    private Button sortButton_View;

    @FXML
    private Label username_label;

    @FXML
    private GridPane gird_View;

    @FXML    
    void initialize(){

        loadViewPage2(null);
    }
    
    

    void loadViewPage2(List<String>urlList){

        //A temp image
        String tempImg = "/TempImages/RioluCard.png";

        //Get the amount of images from list
        int amountImgs = 35;

        //calulate the amount of rows needed in the grid pane
        int amountOfRows = amountImgs/4;

        //Add a extra row for extra cards
        if(amountImgs % 4 > 0){
            amountOfRows++;
        }

        //Keep count of current image
        int currentImg = 0;

        //Beauify a row
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(175);
        rc.setPrefHeight(175);
        rc.setMaxHeight(175);
        rc.setValignment(VPos.CENTER);
        
        //Beauify a column
        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(130);
        cc.setPrefWidth(130);
        cc.setMaxWidth(130);
        cc.setHalignment(HPos.CENTER);

        //for every row and column add the card image
        for(int row = 0; row < amountOfRows; row++){

            //Get the current slot of the grid
            for(int column = 0; column < 4; column++){

                //If counter is more than the amount of images, stop loop
                if(currentImg >= amountImgs)
                    break;

                //Create a image of current
                Image img = new Image(getClass().getResourceAsStream(tempImg));
                ImageView imgView = new ImageView(img);

                //Set the image height and width
                imgView.setFitWidth(130);
                imgView.setFitHeight(150);
                imgView.setPreserveRatio(true);

                //Add the image to the gridview
                gird_View.add(imgView, column, row);

                //Go to next index
                currentImg++;
                
            }
            
            //Beauify Row
            gird_View.getRowConstraints().add(rc);

        }



    }

    @FXML
    void goToLogin(ActionEvent event) {
        App.changeCurrentPage(Page.LOGIN);
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeCurrentPage(Page.MAIN_MENU);
    }

    @FXML
    void goToShop(ActionEvent event) {

    }

    @FXML
    void searchCards(ActionEvent event) {

    }

    @FXML
    void sortCards(ActionEvent event) {

    }

}
