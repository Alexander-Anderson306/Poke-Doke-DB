package com.poke_frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SortPopup extends ScalePage {

    private ViewPage parentContoller;

    @FXML
    private Button andOrButton;

    @FXML
    private CheckBox bugBox;

    @FXML
    private CheckBox darkBox;

    @FXML
    private CheckBox dragonBox;

    @FXML
    private CheckBox electricBox;

    @FXML
    private Button exitButton;

    @FXML
    private CheckBox fairyBox;

    @FXML
    private CheckBox fightingBox;

    @FXML
    private CheckBox fireBox;

    @FXML
    private GridPane firstTypePane;

    @FXML
    private CheckBox flyingBox;

    @FXML
    private CheckBox ghostBox;

    @FXML
    private CheckBox grassBox;

    @FXML
    private CheckBox groundBox;

    @FXML
    private Group groupScale;

    @FXML
    private CheckBox iceBox;

    @FXML
    private CheckBox normalBox;

    @FXML
    private TextField pokemonNameBox;

    @FXML
    private CheckBox posionBox;

    @FXML
    private CheckBox psychicBox;

    @FXML
    private CheckBox rockBox;

    @FXML
    private Pane rootPane;

    @FXML
    private Button sortButton;

    @FXML
    private ChoiceBox<?> sortByChoiceBox;

    @FXML
    private CheckBox steelBox;

    @FXML
    private CheckBox waterBox;

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void sortCards(ActionEvent event) {

    }

    @FXML
    void switchTypeButton(ActionEvent event) {

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
