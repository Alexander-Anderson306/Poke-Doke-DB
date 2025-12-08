package com.poke_frontend;

import com.poke_frontend.dto.request.AllCardsRequest;
import com.poke_frontend.dto.request.InventoryRequest;
import com.poke_frontend.models.Card;
import com.poke_frontend.models.CardTypeQuant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ViewPage extends ScalePage{

    List<ImageView> allImageViews = new ArrayList<>();

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

    /**
     * This method is called when this page is loaded.
     * It will set the scaling, the username in the top right,
     * and load the users inventory / the database into
     * the view pane.
     */
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
            InventoryRequest req = new InventoryRequest();
            req.userId = App.theClient.getUserId();
            loadInventoryPage(req);
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

            List<CardTypeQuant> allObjects = App.theClient.getInventory(req);
            List<String> allUrls = new ArrayList<String>();
            List<String> cardRarities = new ArrayList<String>();

            for (CardTypeQuant currentObject: allObjects) {
                for (int i=0; i<currentObject.getQuantity(); i++) {
                    allUrls.add(App.imageDirectory + currentObject.getCard().getImagePath());
                    cardRarities.add(currentObject.getCard().getRarity());
                }
            }

            loadViewPage(allUrls, cardRarities);

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

            //List all urls path of images
            List<String> urls = new ArrayList<String>();

            //List all card rairty
            List<String> cardRarities = new ArrayList<String>();

            List<CardTypeQuant> databaseCards = App.theClient.getDBCards(req);

            for (CardTypeQuant currentCard : databaseCards) {
                urls.add(App.imageDirectory + currentCard.getCard().getImagePath());
                cardRarities.add(currentCard.getCard().getRarity());
            }

            loadViewPage(urls, cardRarities);

        } catch (Exception e) {
            e.printStackTrace();
            loadDefaultViewPage();
        }
    }

    /**
     * This method will load the view page with 35 Rioulo cards
     * This should be what the user sees if they are not logged in
     * or if an error occurs.
     */
    void loadDefaultViewPage(){

        List<String> urls = new ArrayList<>();
        for (int i=0; i<35; i++) { urls.add("/TempImages/RioluCard.png"); }
        loadViewPage(urls, null);

    }

    /**
     * This method will fill the view page with the given card images.
     * @param urlList A list of urls of images to display on screen.
     */
    void loadViewPage(List<String> urlList, List<String> rarityList){

        //Start by removing all the old images.
        clearViewPage();

        //Get the amount of images from list
        int amountImgs = urlList.size();

        //calulate the amount of rows needed in the grid pane
        int amountOfRows = amountImgs/4;

        //Add a extra row for extra cards
        if(amountImgs % 4 > 0){
            amountOfRows++;
        }

        boolean isDefault = (rarityList == null);

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
                allImageViews.add(imgView);

                //Set the image height and width
                imgView.setFitWidth(140);
                imgView.setFitHeight(140);
                imgView.setPreserveRatio(true);

                //Store the rarity of card
                Label rairtyLabel;

                if(isDefault){
                    rairtyLabel= new Label("BestBoi");
                }
                else{
                    rairtyLabel = new Label(rarityList.get(currentImg));
                }

                //Set Styles to rairtyLabel
                rairtyLabel.setPrefWidth(imgView.getFitWidth() - 20);
                rairtyLabel.setAlignment(Pos.CENTER_RIGHT);

                //Create a cell that would store the image and label
                VBox cardCell = new VBox();
                cardCell.setPrefWidth(imgView.getFitWidth());
                cardCell.setSpacing(5);
                cardCell.setAlignment(Pos.CENTER);
                VBox.setVgrow(imgView, Priority.ALWAYS);
                cardCell.getChildren().addAll(imgView, rairtyLabel);
                
                //Add the cell to the gridview
                gird_View.add(cardCell, column, row);

                //Go to next index
                currentImg++;

            }

            //Beauify Row
            gird_View.getRowConstraints().add(rc);

        }

    }

    /**
     * This method will remove all the images current in the view page.
     */
    void clearViewPage() {
        
        //Deleted and clear every row
        gird_View.getChildren().clear();
        gird_View.getRowConstraints().clear();

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

    /**
     * This method will be called when the user clicks the magnifying glass next to the search.
     * This method will create an error popup if the user is not logged in or the search
     * bar is blank.
     * Otherwise, it will filter the cards so only those with a string matching the
     * text in the search box will appear.
     */
    @FXML
    void searchCards(ActionEvent event) {

        // If the user is not logged in, throw an error
        if(!App.loggedIn()){
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Not logged in.");
            errorAlert.setContentText("Not logged in, so, no inventory search possible. Try logging in first");
            errorAlert.showAndWait();
            return;
        }

        // If the search bar is blank, throw an error.
        if (searchBar.getText().isBlank()) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Empty Search Bar");
            errorAlert.setContentText("The search bar is empty. Please enter the name of a Pokemon first.");
            errorAlert.showAndWait();
            return;
        }

        // This list of cards will be filled by either an inventory request or an all cards request.
        List<CardTypeQuant> allObjects = new ArrayList<>();

        // Inventory request
        if (App.currentPage==Page.VIEW_INVENTORY) {
            try {
                InventoryRequest req = new InventoryRequest();
                req.userId = App.theClient.getUserId();
                allObjects = App.theClient.getInventory(req);
            } catch (Exception e) {
                e.printStackTrace();
                throwDatabaseError();
            }
        }

        // Database request
        else {
            try {
                AllCardsRequest req = new AllCardsRequest();
                allObjects = App.theClient.getDBCards(req);
            } catch (Exception e) {
                e.printStackTrace();
                throwDatabaseError();
            }
        }

        // Then we convert our list of cards to a list or urls, if they match the given search.
        List<String> allUrls = new ArrayList<String>();
        List<String> cardRarities = new ArrayList<String>();

        for (CardTypeQuant currentObject : allObjects) {
            Card currentCard = currentObject.getCard();
            if (currentCard.getCardName().toLowerCase().contains(searchBar.getText().toLowerCase())) {
                // Temp fix since CardTypeQuant sometimes has the quantity value set to 0.
                allUrls.add(App.imageDirectory + currentObject.getCard().getImagePath());
                for (int i=1; i<currentObject.getQuantity(); i++) {
                    allUrls.add(App.imageDirectory + currentObject.getCard().getImagePath());
                    cardRarities.add(currentObject.getCard().getRarity());
                }
            }
        }

        // Then we load the view page with this new list of urls.
        loadViewPage(allUrls, cardRarities);

    }

    /**
     * A helper method that will show a popup on screen warning the user
     * that a database error has occurred and their desired action
     * has not been completed.
     */
    void throwDatabaseError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Database Issue");
        errorAlert.setContentText("Sorry, but there was an issue with our database. Please try again.");
        errorAlert.showAndWait();
    }


    /**
     * This method will be called when the user hits the sort button next
     * to the magnifying glass.
     * It will bring them to the sort cards pop up.
     */
    @FXML
    void sortCards(ActionEvent event) {
        App.openPopUp("/fxml/sort.fxml", SortPopup.class, popup -> {
            popup.setParentController(this);
        });
    }


}
