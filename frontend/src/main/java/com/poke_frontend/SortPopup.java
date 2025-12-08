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

    /**
     * This method is called when the user presses the exit button.
     * It will bring them back to the view page without loading any cards.
     */
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

        // If not logged in, throw an error and do nothing.
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

        // Determining if we need to filter by rarity
        String rarityFilter;
        if (commonBox.isSelected()) rarityFilter="common";
        else if (uncommonBox.isSelected()) rarityFilter="uncommon";
        else if (rareBox.isSelected()) rarityFilter="rare";
        else rarityFilter="";
        boolean filterByRarity = !rarityFilter.isEmpty();

        // We iterate over all the cards we have.
        // We check filters based on the booleans above
        // If a card does not match the filters, it is removed from the list.
        for (int i=0; i<allObjects.size(); i++) {

            CardTypeQuant currentObject = allObjects.get(i);
            Card currentCard = currentObject.getCard();
            List<String> currentCardsTypes = currentObject.getTypes();

            // If we are filtering by name, and this card has a different name, discard it.
            if (filterByName) {
                if (!currentCard.getCardName().toLowerCase().contains(pokemonNameBox.getText().toLowerCase())) {
                    allObjects.remove(currentObject);
                    i--;
                    continue;
                }
            }

            // If we are filtering by type, check if we are filtering by AND or OR
            if (filterByType) {

                // Filter by AND
                if (andOrButton.getText().equals("AND")) {
                    // Check every type we have selected. If the Pokemon does not have one of them, discard it.
                    boolean keep = true;
                    for (String currentType : allSelectedTypes) {
                        if (!currentCardsTypes.contains(currentType)) {
                            keep = false;
                            break;
                        }
                    }
                    if (!keep) {
                        allObjects.remove(currentObject);
                        i--;
                        continue;
                    }
                }

                // Filter by OR
                else {
                    // Check every type the Pokemon has. If any of them exist in selectedTypes, keep it.
                    boolean keep = false;
                    for (String currentType : currentCardsTypes) {
                        if (allSelectedTypes.contains(currentType)) {
                            keep = true;
                            break;
                        }
                    }
                    if (!keep) {
                        allObjects.remove(currentObject);
                        i--;
                        continue;
                    }
                }
            }

            // If we are filtering by rarity, and this card has a different rarity, discard it.
            if (filterByRarity) {
                if (!currentCard.getRarity().equalsIgnoreCase(rarityFilter)) {
                    allObjects.remove(currentObject);
                    i--;
                }
            }

        } // End of filter loop

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
            else if (sortByChoiceBox.getValue().equals("Rarity")) {
                allObjects.sort((c1, c2) -> compareCardRarity(c1.getCard(), c2.getCard()));
            }
        }

        // Then we turn the remaining card objects into urls and send them to the view page to be loaded.
        List<String> allUrls = new ArrayList<String>();
        List<String> cardRarities = new ArrayList<String>();
        for (CardTypeQuant currentObject: allObjects) {
            // Temp fix since CardTypeQuant sometimes has the quantity value set to 0.
            allUrls.add("/images/full_image/" + currentObject.getCard().getImagePath());
            cardRarities.add(currentObject.getCard().getRarity());
            for (int i=1; i<currentObject.getQuantity(); i++) {
                allUrls.add("/images/full_image/" + currentObject.getCard().getImagePath());
                cardRarities.add(currentObject.getCard().getRarity());
            }
        }

        // Then we load this new set of images in the view page and close the popup.
        parentContoller.loadViewPage(allUrls, cardRarities);
        closePopup();

    }

    /**
     * This method will compare the two cards by rarity and return
     * an integer representing which one has a lower value.
     * @param c1 The first card.
     * @param c2 The second card.
     * @return A negative number if the first card is more common then the second, a zero if they are of the same value, and a positive number if the second card is more common.
     */
    public static int compareCardRarity(Card c1, Card c2) {

        int c1Value=-1;
        if (c1.getRarity().equalsIgnoreCase("common")) c1Value=1;
        if (c1.getRarity().equalsIgnoreCase("uncommon")) c1Value=2;
        if (c1.getRarity().equalsIgnoreCase("rare")) c1Value=3;
        if (c1.getRarity().equalsIgnoreCase("epic")) c1Value=4;
        if (c1.getRarity().equalsIgnoreCase("legendary")) c1Value=5;

        int c2Value=-1;
        if (c2.getRarity().equalsIgnoreCase("common")) c2Value=1;
        if (c2.getRarity().equalsIgnoreCase("uncommon")) c2Value=2;
        if (c2.getRarity().equalsIgnoreCase("rare")) c2Value=3;
        if (c2.getRarity().equalsIgnoreCase("epic")) c2Value=4;
        if (c2.getRarity().equalsIgnoreCase("legendary")) c2Value=5;

        return Integer.compare(c1Value, c2Value);

    }

    /**
     * This helper method creates a popup warning the user that they are logged out
     * and therefore their desired action can not be completed.
     */
    void throwLoggedOutError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Not logged in.");
        errorAlert.setContentText("Not logged in, so, no inventory search possible. Try logging in first");
        errorAlert.showAndWait();
        closePopup();
    }

    /**
     * This helper method creates a popup alerting the user that a database error occurred
     * and therefore their desired action did not carry through.
     */
    void throwDatabaseError() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Database Issue");
        errorAlert.setContentText("Sorry, but there was an issue with our database. Please try again.");
        errorAlert.showAndWait();
        closePopup();
    }

    /**
     * This internal method will close the sort popup.
     */
    void closePopup() {
        ( (Stage) pokemonNameBox.getScene().getWindow() ).close();
    }

    /**
     * This method is called upon when the user clicks to "OR" button
     * in the sort popup, toggling it between "AND" and "OR"
     */
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

    /**
     * All 5 of the below methods serve the same function.
     * They make it so anytime the user clicks on a rarity box, all other rarity boxes become unselected.
     */

    public void clickLegendaryBox(ActionEvent actionEvent) {
        commonBox.setSelected(false);
        uncommonBox.setSelected(false);
        rareBox.setSelected(false);
        epicBox.setSelected(false);
    }

    public void clickEpicBox(ActionEvent actionEvent) {
        commonBox.setSelected(false);
        uncommonBox.setSelected(false);
        rareBox.setSelected(false);
        legendaryBox.setSelected(false);
    }

    public void clickRareBox(ActionEvent actionEvent) {
        commonBox.setSelected(false);
        uncommonBox.setSelected(false);
        epicBox.setSelected(false);
        legendaryBox.setSelected(false);
    }

    public void clickUncommonBox(ActionEvent actionEvent) {
        commonBox.setSelected(false);
        rareBox.setSelected(false);
        epicBox.setSelected(false);
        legendaryBox.setSelected(false);
    }

    public void clickCommonBox(ActionEvent actionEvent) {
        uncommonBox.setSelected(false);
        rareBox.setSelected(false);
        epicBox.setSelected(false);
        legendaryBox.setSelected(false);
    }

}
