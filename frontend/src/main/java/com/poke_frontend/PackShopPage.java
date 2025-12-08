package com.poke_frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.poke_frontend.dto.request.PackRequest;
import com.poke_frontend.models.CardPack;
import com.poke_frontend.models.CardTypeQuant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for the Pack Shop page.
 * <p>
 * This class handles:
 * <ul>
 *     <li>Displaying available Pokémon card packs</li>
 *     <li>Letting the user choose the quantity of a pack</li>
 *     <li>Updating the user's pack inventory</li>
 *     <li>Navigating to checkout or home</li>
 * </ul>
 */
public class PackShopPage extends ScalePage {

    @FXML private Label errorAmount;
    @FXML private Button confirmAmount;
    @FXML private TextField packAmount_field;
    @FXML private Label packAmountPrompt;
    @FXML private Button checkout_button;
    @FXML private Button cancelButton;
    @FXML private GridPane gird_View;
    @FXML private Group groupScale;
    @FXML private Button homeButton_View;
    @FXML private Pane rootPane;
    @FXML private Pane packBuyPane;
    @FXML private ScrollPane scrollView;
    @FXML private Label amountPack;
    //@FXML private Button clearButton;

    /**
     * Stores the user's pack inventory.
     * The key is the pack ID (e.g., "Pack #1"), and the value is the quantity the user selected.
     * <p>
     * Declared static so the inventory is shared across pages.
     */
    private static final HashMap<Integer, Integer> userInvent = new HashMap<>();

    /**
     * Tracks the ID of the pack the user is currently editing.
     */
    private String currentPackId;

    /**
     * Initializes the PackShopPage.
     * <p>
     * Called automatically by JavaFX after the FXML components load.
     * Sets up screen scaling and loads pack images into the grid.
     */
    @FXML
    void initialize() {
        implementScaling(groupScale, rootPane);
        getDataPacks(new PackRequest());
    }

    /**
     * Navigates back to the shop selection page.
     *
     * @param event the button click event
     */
    @FXML
    void edit(ActionEvent event) {
        App.changeCurrentPage(Page.SHOP);
    }

    /**
     * Called when the user clicks on a pack image.
     * Shows the input UI so the user can type how many packs they want.
     *
     * @param event the mouse click event containing the ImageView
     */
    @FXML
    void buyPack(MouseEvent event) {
        ImageView currentPack = (ImageView) event.getSource();
        this.currentPackId = currentPack.getId();

        int currPackID = Integer.valueOf(currentPackId);
        if (userInvent.containsKey(currPackID) && userInvent.get(currPackID) > 0) {
            amountPack.setText("Currently has: " + userInvent.get(currPackID));
        } else {
            amountPack.setText("Currently has: " + 0);
        }

        packBuyPane.setVisible(true);
    }

    /**
     * Returns the user's current inventory of purchased packs.
     *
     * @return a reference to the shared user inventory map
     */
    public HashMap<Integer, Integer> getUserInventory() {
        return userInvent;
    }

    /**
     * Confirms the number of packs the user wants to buy.
     * Parses the input field and updates the shared inventory.
     *
     * @param event the button click event
     */
    @FXML
    void conPackAmount(ActionEvent event) {
        String packAmount = packAmount_field.getText();
        int amount;
        
        if (!packAmount.isBlank()) {
            try {
                amount = Integer.parseInt(packAmount);

                if (amount > 0) {
                    userInvent.put(Integer.valueOf(currentPackId), amount);

                    // Reset UI
                    backToShop(null);
                } else {
                    errorAmount.setVisible(true);
                }
            } catch (NumberFormatException e) {}
        }
    }

    /**
     * Navigates to the checkout page.
     *
     * @param event the button click event
     */
    @FXML
    void goToCheckout(ActionEvent event) {
        App.changeCurrentPage(Page.CHECKOUT);
    }

    /**
     * Navigates back to the main menu.
     *
     * @param event the button click event
     */
    @FXML
    void goToMainMenu(ActionEvent event) {
        App.changeCurrentPage(Page.MAIN_MENU);
    }

    /**
     * Closes the pane user asking input
     * @param event
     */
    @FXML
    void backToShop(ActionEvent event){
        packAmount_field.setText("");
        packBuyPane.setVisible(false);
    }

    /**
     * Clear the selected pack out of the user cart
     * @param event
     */
    @FXML
    void clearCart(ActionEvent event) {
        if (userInvent.containsKey(Integer.valueOf(currentPackId))) {
            userInvent.remove(Integer.valueOf(currentPackId));
            packBuyPane.setVisible(false);
        }
    }

    /**
     * Loads Pokémon pack images into the grid view.
     * <p>
     * Currently uses placeholder images until URLs are provided.
     *
     * @param databasePacks  list of objects packs (unused in current implementation)
     */
    void loadPacks(List<CardPack> databasePacks) {

        //Get strings of pack info
        List<String> idPackList = new ArrayList<String>();
        List<String> packRairtyList = new ArrayList<String>();
        List<String> packPriceList = new ArrayList<String>();
        List<String> packNameList = new ArrayList<String>();

        // Image path
        String imgPath = "/images/pack_images/PokemonPack";
        String fileType = ".png";

        if(databasePacks == null){
            idPackList.add("/images/pack_images/PokemonPack1.png");
            packRairtyList.add("UNKNOWN");
            packPriceList.add("$0");
            packNameList.add("UNKNOWN");
        }
        else{
            for (CardPack currentPack : databasePacks) {
                idPackList.add(imgPath + (currentPack.getId()+1) +fileType);
                packRairtyList.add(currentPack.getPackRarity());
                packPriceList.add("$" + String.format("%.2f",currentPack.getPrice()));
                packNameList.add(currentPack.getPackName());
            }
        }


        // Row configuration
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(256);
        rc.setPrefHeight(256);
        rc.setMaxHeight(256);
        rc.setValignment(VPos.CENTER);

        // Column configuration
        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(200);
        cc.setPrefWidth(200);
        cc.setMaxWidth(200);
        cc.setHalignment(HPos.CENTER);

        //Number of packs
        int packAmount = idPackList.size();

        for (int col = 0; col < packAmount; col++) {
            Image img = new Image(getClass().getResourceAsStream(idPackList.get(col)));

            ImageView imgView = new ImageView(img);

            //Pack images properties
            imgView.setFitWidth(150);
            imgView.setFitHeight(200);
            imgView.setPreserveRatio(true);
            imgView.setId(String.valueOf(col + 1));
            imgView.setOnMouseClicked(this::buyPack);

            //Get properties for pack rairty
            Label rairtyLabel = new Label(packRairtyList.get(col));
            rairtyLabel.setPrefWidth(imgView.getFitWidth() - 20);
            rairtyLabel.setAlignment(Pos.CENTER_LEFT);

            //Get properties for pack Price
            Label priceLabel = new Label(packPriceList.get(col));
            priceLabel.setPrefWidth(imgView.getFitWidth() - 20);
            priceLabel.setAlignment(Pos.CENTER_RIGHT);

            //Get properties for pack rairty
            Label nameLabel = new Label(packNameList.get(col));
            nameLabel.setPrefWidth(imgView.getFitWidth() - 20);
            nameLabel.setAlignment(Pos.CENTER);




            //Create a cell that would store the image and label
            VBox cardCell = new VBox();
            cardCell.setPrefHeight(imgView.getFitHeight());
            cardCell.setSpacing(0);
            cardCell.setAlignment(Pos.CENTER);
            VBox.setVgrow(imgView, Priority.ALWAYS);
            cardCell.getChildren().addAll(nameLabel, imgView, rairtyLabel, priceLabel);

            gird_View.add(cardCell, col, 0);

            gird_View.getColumnConstraints().add(cc);
        }
    }

    //TODO: Create connections to packs in database

    
    void getDataPacks(PackRequest req){

        //If not logged in, show default packs
        if (!App.loggedIn()) {
            loadPacks(null);
            return;
        }
        try {

            List<CardPack> databasePacks = App.theClient.displayStore(req);

            loadPacks(databasePacks);          

        } catch (Exception e) {
            e.printStackTrace();
            loadPacks(null);
        }

    }
}
