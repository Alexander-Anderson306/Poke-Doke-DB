package com.poke_frontend;

import com.poke_frontend.dto.request.AllCardsRequest;
import com.poke_frontend.dto.request.InventoryRequest;
import com.poke_frontend.models.Card;
import com.poke_frontend.models.InventoryRequestObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.transform.Scale;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ViewPage extends ScalePage{

    @FXML
    private Button accountButton_view;

    @FXML
    protected Pane rootPane;

    @FXML
    private Button buyButton_View;

    @FXML
    private AnchorPane cardView;

    @FXML
    private Button homeButton_View;

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
    private Group groupScale;

    @FXML    
    void initialize(){

        implementScaling(groupScale, rootPane);


        // Setting the username field in the top right.
        if (App.loggedIn()) {
            username_label.setText(App.theClient.getUsername());
        } else {
            username_label.setText("No User");
        }


        // Not logged in? Load default page.
        if (!App.loggedIn()) {
            loadDefaultViewPage();
        }

        // Inventory Page
        else if (App.currentPage==Page.VIEW_INVENTORY) {
            loadInventoryPage(new InventoryRequest());
        }

        // Database Page
        else {
            loadDatabasePage(new AllCardsRequest());
        }

    }

    /**
     * This method takes in an inventory request and loads it into the view page if possible.
     * @param req The request that is going to be sent to the database.
     */
    public void loadInventoryPage(InventoryRequest req) {
        if (!App.loggedIn()) {
            loadDefaultViewPage();
            return;
        }
        try {

            List<InventoryRequestObject> allObjects = App.theClient.getInventory(req);
            List<String> allUrls = new ArrayList<String>();

            for (InventoryRequestObject currentObject: allObjects) {
                for (int i=0; i<currentObject.getQuantity(); i++) {
                    allUrls.add(currentObject.getCard().getImagePath());
                }
            }

            loadViewPage(allUrls);

        } catch (Exception e) {
            e.printStackTrace();
            loadDefaultViewPage();
        }
    }

    /**
     * This method takes in a allCardsRequest and loads it into the view page if possible.
     * @param req The request that going to be sent the database.
     */
    public void loadDatabasePage(AllCardsRequest req) {
        if (!App.loggedIn()) {
            loadDefaultViewPage();
            return;
        }

        try {

            List<String> urls = new ArrayList<String>();
            List<Card> databaseCards = App.theClient.getDBCards(req);

            for (Card currentCard : databaseCards) {
                urls.add(currentCard.getImagePath());
            }

            loadViewPage(urls);

        } catch (Exception e) {
            e.printStackTrace();
            loadDefaultViewPage();
        }
    }

    /**
     * This is the method that Alan made which will fill the screen with Rioulo cards.
     */
    void loadDefaultViewPage(){

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

    /**
     * This is a copy paste of Alans method that will actually load the images passed to it.
     * @param urlList A list of urls of images to display on screen.
     */
    void loadViewPage(List<String>urlList){

        //Get the amount of images from list
        int amountImgs = urlList.size();

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
                Image img = new Image(getClass().getResourceAsStream(urlList.get(currentImg)));
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
        App.logout();
    }

    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeCurrentPage(Page.MAIN_MENU);
    }

    @FXML
    void goToShop(ActionEvent event) {
        App.changeCurrentPage(Page.SHOP);
    }

    @FXML
    void searchCards(ActionEvent event) {

    }

    @FXML
    void sortCards(ActionEvent event) {
        SortPopup popUP = App.openPopUp("/fxml/sort.fxml", SortPopup.class);
        popUP.setParentController(this);
    }

}
