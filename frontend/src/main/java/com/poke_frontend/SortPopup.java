package com.poke_frontend;

import com.poke_frontend.dto.request.AllCardsRequest;
import com.poke_frontend.dto.request.InventoryRequest;
import com.poke_frontend.models.Card;
import com.poke_frontend.models.CardTypeQuant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortPopup extends ScalePage {

    private ViewPage parentContoller;

    @FXML
    private TextField pokemonNameBox;

    @FXML
    private Button andOrButton;

    @FXML
    private Button sortButton;

    @FXML
    private Button exitButton;

    @FXML
    private GridPane firstTypePane;

    @FXML
    private Group groupScale;

    @FXML
    private Pane rootPane;

    @FXML
    private ChoiceBox<?> sortByChoiceBox;

    // --- Type Boxes ----

    @FXML
    private CheckBox bugBox;

    @FXML
    private CheckBox darkBox;

    @FXML
    private CheckBox dragonBox;

    @FXML
    private CheckBox electricBox;

    @FXML
    private CheckBox fairyBox;

    @FXML
    private CheckBox fightingBox;

    @FXML
    private CheckBox fireBox;

    @FXML
    private CheckBox flyingBox;

    @FXML
    private CheckBox ghostBox;

    @FXML
    private CheckBox grassBox;

    @FXML
    private CheckBox groundBox;

    @FXML
    private CheckBox iceBox;

    @FXML
    private CheckBox normalBox;

    @FXML
    private CheckBox posionBox;

    @FXML
    private CheckBox psychicBox;

    @FXML
    private CheckBox rockBox;

    @FXML
    private CheckBox steelBox;

    @FXML
    private CheckBox waterBox;

    // --- End Of Type Boxes ----

    // --- Rairty Boxes ----
    @FXML
    private CheckBox commonBox;

    @FXML
    private CheckBox uncommonBox;

    @FXML
    private CheckBox rareBox;

    @FXML
    private CheckBox epicBox;

    @FXML
    private CheckBox legendaryBox;
    // --- End Of Rairty Boxes ----

    @FXML
    void exit(ActionEvent event) {
        closePopup();
    }

    /**
     * This method will load the view page with a new set of cards
     * based on the parameters within the sort popup.
     * @param event
     */
    @FXML
    void sortCards(ActionEvent event) {

        if(!App.loggedIn()) {
            throwLoggedOutError();
            return;
        }

        // The first step is to get a list of card objects to work with
        List<CardTypeQuant> allObjects = new ArrayList<>();

        // If we want to view the inventory
        if (App.currentPage==Page.VIEW_INVENTORY) {
            // Send an inventory request to get the users inventory
            try {
                InventoryRequest req = new InventoryRequest();
                req.userId = App.theClient.getUserId();
                allObjects = App.theClient.getInventory(req);
            } catch (Exception e) {
                e.printStackTrace();
                throwDatabaseError();
            }
        }

        // Otherwise assume we want to see all cards in the database
        else {
            // Send an all card request to get the whole database.
            try {
                AllCardsRequest req = new AllCardsRequest();
                allObjects = App.theClient.getDBCards(req);
            } catch (Exception e) {
                e.printStackTrace();
                throwDatabaseError();
            }
        }

        // Creating a list of all selected types
        List<String> allSelectedTypes = new ArrayList<>();
        if (bugBox.isSelected()) allSelectedTypes.add("Bug");
        if (darkBox.isSelected()) allSelectedTypes.add("Dark");
        if (dragonBox.isSelected()) allSelectedTypes.add("Dragon");
        if (electricBox.isSelected()) allSelectedTypes.add("Electric");
        if (fairyBox.isSelected()) allSelectedTypes.add("Fairy");
        if (fightingBox.isSelected()) allSelectedTypes.add("Fighting");
        if (fireBox.isSelected()) allSelectedTypes.add("Fire");
        if (flyingBox.isSelected()) allSelectedTypes.add("Flying");
        if (ghostBox.isSelected()) allSelectedTypes.add("Ghost");
        if (grassBox.isSelected()) allSelectedTypes.add("Grass");
        if (groundBox.isSelected()) allSelectedTypes.add("Ground");
        if (iceBox.isSelected()) allSelectedTypes.add("Ice");
        if (normalBox.isSelected()) allSelectedTypes.add("Normal");
        if (posionBox.isSelected()) allSelectedTypes.add("Poison");
        if (psychicBox.isSelected()) allSelectedTypes.add("Psychic");
        if (rockBox.isSelected()) allSelectedTypes.add("Rock");
        if (steelBox.isSelected()) allSelectedTypes.add("Steel");
        if (waterBox.isSelected()) allSelectedTypes.add("Water");

        // Determining what we need to filter by
        boolean filterByType = !allSelectedTypes.isEmpty();
        boolean filterByName = !pokemonNameBox.getText().isBlank();

        // We iterate over all of the cards we have.
        // We check filters based on the booleans above
        // If a card does not match the filters, it is removed from the list.
        //error here! cannot remove from an array list inside of an enhanced for loop
        for (CardTypeQuant currentObject : allObjects) {

            Card currentCard = currentObject.getCard();
            List<String> currentCardsTypes = currentObject.getTypes();

            // If we are filtering by name, and this card has a different name, discard it.
            if (filterByName) {
                if (!currentCard.getCardName().equalsIgnoreCase(pokemonNameBox.getText())) {
                    allObjects.remove(currentObject);
                    continue;
                }
            }

            // If we are filtering by type, check if we are filtering by AND or OR
            if (filterByType) {

                // Filter by AND
                if (andOrButton.equals("AND")) {
                    // Check every type the Pokemon has. If any of them do not exist in the selectedTypes, discard it.
                    for (String currentType : currentCardsTypes) {
                        if (!allSelectedTypes.contains(currentType)) {
                            allObjects.remove(currentObject);
                            continue;
                        }
                    }
                }

                // Filter by OR
                else {
                    // Check every type the Pokemon has. If any of them exist in selectedTypes, keep it.
                    boolean keep = false;
                    for (String currentType : currentCardsTypes) {
                        if (allSelectedTypes.contains(currentType)) {
                            keep=true;
                            break;
                        }
                    }
                    if (!keep) {
                        allObjects.remove(currentObject);
                        continue;
                    }
                }
            }
        }

        // If something has been selected in the "sortByChoiceBox", then we need to organize the list of cards.
        if (sortByChoiceBox.getValue()!=null) {
            if (sortByChoiceBox.getValue().equals("Alphabetically")) {
                allObjects.sort(Comparator.comparing(a -> a.getCard().getCardName()));
            }
            else if (sortByChoiceBox.getValue().equals("Number Order")) {
                allObjects.sort(Comparator.comparingInt(a -> a.getCard().getId()));
            }
            else if (sortByChoiceBox.getValue().equals("Type")) {
                allObjects.sort(Comparator.comparing(a -> a.getTypes().getFirst()));
            }
        }

        // Then we turn the remaining card objects into urls and send them to the view page to be loaded.
        List<String> allUrls = new ArrayList<String>();
        for (CardTypeQuant currentObject: allObjects) {
            for (int i=0; i<currentObject.getQuantity(); i++) {
                allUrls.add("/images/full_image/" + currentObject.getCard().getImagePath());
            }
        }

        parentContoller.loadViewPage(allUrls);
        closePopup();

    }

    void throwLoggedOutError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Not logged in.");
        errorAlert.setContentText("Not logged in, so, no inventory search possible. Try logging in first");
        errorAlert.showAndWait();
        closePopup();
    }

    void throwDatabaseError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Database Issue");
        errorAlert.setContentText("Sorry, but there was an issue with our database. Please try again.");
        errorAlert.showAndWait();
        closePopup();
    }

    void closePopup() {
        ( (Stage) pokemonNameBox.getScene().getWindow() ).close();
    }

    @FXML
    void switchTypeButton(ActionEvent event) {
        if (andOrButton.getText().equals("OR"))
            andOrButton.setText("AND");
        else
            andOrButton.setText("OR");

    }

    @FXML
    void initialize(){
        implementScaling(groupScale, rootPane);
        setSceneName("Sort Cards");
    }



    /**
     * Set the parent controller
     * @param previousPage the parent controoler of the popup
     */
    public void setParentController(ViewPage previousPage){
        parentContoller = previousPage;
    }


}
