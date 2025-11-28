package com.poke_frontend;

import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
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
import javafx.scene.layout.RowConstraints;

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
    @FXML private GridPane gird_View;
    @FXML private Group groupScale;
    @FXML private Button homeButton_View;
    @FXML private Pane rootPane;
    @FXML private ScrollPane scrollView;

    /**
     * Stores the user's pack inventory.
     * The key is the pack ID (e.g., "Pack #1"), and the value is the quantity the user selected.
     * <p>
     * Declared static so the inventory is shared across pages.
     */
    private static final HashMap<String, Integer> userInvent = new HashMap<>();

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
        loadPacks(null);
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

        packAmount_field.setVisible(true);
        confirmAmount.setVisible(true);
        packAmountPrompt.setVisible(true);
        hide(false);
    }

    /**
     * Hides or shows the main UI elements so the user can enter a pack amount.
     *
     * @param input true to show the main view; false to hide it
     */
    private void hide(boolean input) {
        scrollView.setVisible(input);
        homeButton_View.setVisible(input);
        checkout_button.setVisible(input);
    }

    /**
     * Returns the user's current inventory of purchased packs.
     *
     * @return a reference to the shared user inventory map
     */
    public HashMap<String, Integer> getUserInventory() {
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

                if (amount >= 0) {
                    userInvent.put(currentPackId, amount);

                    // Reset UI
                    packAmount_field.setText("");
                    packAmount_field.setVisible(false);
                    packAmountPrompt.setVisible(false);
                    errorAmount.setVisible(false);
                    confirmAmount.setVisible(false);
                    hide(true);
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
     * Loads Pokémon pack images into the grid view.
     * <p>
     * Currently uses placeholder images until URLs are provided.
     *
     * @param urlList optional list of image URLs (unused in current implementation)
     */
    void loadPacks(List<String> urlList) {
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

        // Temporary placeholder image
        String tempImg = "/TempImages/PokemonPack1.png";
        int amountImgs = 4;

        int currentImg = 0;

        for (int col = 0; col < amountImgs; col++) {

            if (currentImg >= amountImgs) break;

            Image img = new Image(getClass().getResourceAsStream(tempImg));
            ImageView imgView = new ImageView(img);

            imgView.setFitWidth(150);
            imgView.setFitHeight(256);
            imgView.setPreserveRatio(true);
            imgView.setId("Pack #" + (col + 1));

            imgView.setOnMouseClicked(this::buyPack);

            gird_View.add(imgView, col, 0);
            currentImg++;

            gird_View.getColumnConstraints().add(cc);
        }
    }
}
