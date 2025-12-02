package com.poke_frontend;

import com.poke_frontend.dto.request.InventoryRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
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

    @FXML
    void sortCards(ActionEvent event) {

        if(!App.loggedIn()) {
            closePopup();
            return;
        }

        InventoryRequest req = new InventoryRequest();

        // Id
        req.userId=App.theClient.getUserId();

        // Name
        req.cardName=pokemonNameBox.getText();

        // Card Type
        List<String> allSelectedTypes = new ArrayList<>();
        if (bugBox.isSelected()) allSelectedTypes.add("bug");
        if (darkBox.isSelected()) allSelectedTypes.add("dark");
        if (dragonBox.isSelected()) allSelectedTypes.add("dragon");
        if (electricBox.isSelected()) allSelectedTypes.add("electric");
        if (fairyBox.isSelected()) allSelectedTypes.add("fairy");
        if (fightingBox.isSelected()) allSelectedTypes.add("fighting");
        if (fireBox.isSelected()) allSelectedTypes.add("fire");
        if (flyingBox.isSelected()) allSelectedTypes.add("flying");
        if (ghostBox.isSelected()) allSelectedTypes.add("ghost");
        if (grassBox.isSelected()) allSelectedTypes.add("grass");
        if (groundBox.isSelected()) allSelectedTypes.add("ground");
        if (iceBox.isSelected()) allSelectedTypes.add("ice");
        if (normalBox.isSelected()) allSelectedTypes.add("normal");
        if (posionBox.isSelected()) allSelectedTypes.add("poison");
        if (psychicBox.isSelected()) allSelectedTypes.add("psychic");
        if (rockBox.isSelected()) allSelectedTypes.add("rock");
        if (steelBox.isSelected()) allSelectedTypes.add("steel");
        if (waterBox.isSelected()) allSelectedTypes.add("water");
        req.cardTypes = allSelectedTypes;

        // Card Rarity
        // No button in the sort popup lets the user choose this.
        req.cardRarity="";

        // And Or
        if (andOrButton.getText().equals("AND"))
            req.andOr='A';
        else
            req.andOr='O';

        parentContoller.loadInventoryPage(req);

        closePopup();

    }

    void closePopup() {
        /*
        IDK how to implement this. This should just close the sort popup.
         */
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
