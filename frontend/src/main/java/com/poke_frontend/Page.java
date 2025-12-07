package com.poke_frontend;

/**
 * This represents one of the pages the program can be on at a given time.
 * There are two string variables, one for storing the address for the
 * fxml file necessary to load that page, and another for the title of the
 * given page.
 */
public enum Page {

    LOGIN("/fxml/login.fxml", "Login to POKEDOKE"),
    CREATE_ACCOUNT("/fxml/createAccount.fxml", "Create Account!"),
    MAIN_MENU("/fxml/mainMenu.fxml", "POKEDOKE"),
    VIEW_INVENTORY("/fxml/viewCards.fxml", "Inventory"),
    VIEW_DATABASE("/fxml/viewCards.fxml", "The Pokedex"),
    SHOP("/fxml/buyPacks.fxml", "POKEDOKE Store"),
    CHECKOUT("/fxml/checkout.fxml", "Checkout Page"),
    ;

    final String fxml;
    final String sceneName;

    Page(String fxml, String sceneName) {
        this.fxml=fxml;
        this.sceneName = sceneName;
    }

    public String getFXML() {
        return fxml;
    }

    public String getSceneName(){
        return sceneName;
    }
}
