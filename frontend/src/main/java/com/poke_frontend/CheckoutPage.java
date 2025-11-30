package com.poke_frontend;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Controller class for the checkout page.
 * <p>
 * This page displays the user's selected Pokémon pack quantities
 * (retrieved from {@link PackShopPage}) and allows the user to confirm
 * or return to the shop to make changes.
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Scaling the page UI for different resolutions</li>
 *     <li>Displaying the user's pack inventory</li>
 *     <li>Navigating back to the shop</li>
 * </ul>
 */
public class CheckoutPage extends ScalePage {

    @FXML protected Pane rootPane;
    @FXML private Group groupScale;
    @FXML private Label TempDisplay;
    @FXML private Button confirm_button;
    @FXML private Button edit_button;

    /**
     * Navigates back to the shop page so the user can edit their pack selections.
     *
     * @param event the button click event
     */
    @FXML
    void edit(ActionEvent event) {
        App.changeCurrentPage(Page.SHOP);
    }

    /**
     * Displays the contents of the user's cart by fetching the shared
     * inventory stored in {@link PackShopPage}.
     * <p>
     * Note: The inventory is static inside {@link PackShopPage},
     * so creating a new instance still provides access to the shared data.
     */
    void displayUserCart() {
        // Create PackShopPage instance to access static inventory
        PackShopPage packShopPage = new PackShopPage();

        HashMap<String, Integer> userInventory = packShopPage.getUserInventory();

        // Debug print to console
        for (String key : userInventory.keySet()) {
            System.out.println("Key: " + key + ", Value: " + userInventory.get(key));
        }

        // Temporary UI display text
        TempDisplay.setText("Hello World");
    }

    /**
     * Initializes the checkout page.
     * <p>
     * Called automatically by JavaFX after FXML components are loaded.
     * Applies UI scaling and loads the user's cart data.
     */
    @FXML
    void initialize() {
        implementScaling(groupScale, rootPane);
        displayUserCart();
    }
}
