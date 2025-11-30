package com.poke_frontend;

public enum Page {

    LOGIN("/fxml/login.fxml", "Login to POKEDOKE", "/css/mainPage.css"),
    CREATE_ACCOUNT("/fxml/createAccount.fxml", "Create Account!", "/css/mainPage.css"),
    MAIN_MENU("/fxml/mainMenu.fxml", "POKEDOKE", "/css/mainPage.css"),
    VIEW_INVENTORY("/fxml/viewCards.fxml", "Inventory", "/css/mainPage.css"),
    VIEW_DATABASE("/fxml/viewCards.fxml", "The Pokedex", "/css/mainPage.css"),
    SHOP("/fxml/buyPacks.fxml", "POKEDOKE Store", "/css/mainPage.css"),
    ;

    final String fxml;
    final String sceneName;
    final String styleSheet;

    Page(String fxml, String sceneName, String styleSheet) {
        this.fxml=fxml;
        this.sceneName = sceneName;
        this.styleSheet = styleSheet;
    }

    public String getFXML() {
        return fxml;
    }

    public String getSceneName(){
        return sceneName;
    }

    public String getCSS(){
        return styleSheet;
    }
}
