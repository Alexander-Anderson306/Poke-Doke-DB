package com.poke_frontend;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    @FXML private Button confirm_button;
    @FXML private Button edit_button;
    @FXML private GridPane gird_View;
    @FXML private Label error;
    @FXML private Label success;
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
        gird_View.getChildren().clear(); 
        gird_View.getColumnConstraints().clear();
        gird_View.setAlignment(Pos.CENTER);   // Center whole grid

        PackShopPage packShopPage = new PackShopPage();
        HashMap<Integer, Integer> userInventory = packShopPage.getUserInventory();

        String imgPath = "/TempImages/";
        String fileType = ".png";

        int col = 0; // column index for each pack

        for (Integer key : userInventory.keySet()) {

            // Load image
            String imgFullPath = imgPath + "PokemonPack" + key + fileType;
            Image img = new Image(getClass().getResourceAsStream(imgFullPath));

            ImageView imgView = new ImageView(img);

            // Setting the image width & height 
            imgView.setFitWidth(150);
            imgView.setFitHeight(156);
            imgView.setPreserveRatio(true);

            // Quantity label
            int qty = userInventory.get(key);
            Label qtyLabel = new Label("Amount: " + qty);
            qtyLabel.setFont(Font.font(16));
            qtyLabel.setAlignment(Pos.CENTER);

            // VBox = Image on top, text underneath
            VBox box = new VBox(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(imgView, qtyLabel);

            // Add VBox to grid
            gird_View.add(box, col, 0);

            // Column styling
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(150);
            cc.setPrefWidth(160);
            cc.setHalignment(HPos.CENTER);
            gird_View.getColumnConstraints().add(cc);

            col++;
        }
    }


    @FXML
    void confirm(ActionEvent event) {
        try {
            Client client = new Client();
            int userId = client.getUserId();
            System.out.println(userId);
            
            gird_View.setVisible(false);
            confirm_button.setVisible(false);
            edit_button.setVisible(false);

            success.setVisible(true);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            error.setVisible(true);
        }
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
