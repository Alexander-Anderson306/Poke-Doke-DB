package com.poke_frontend;

public enum Page {

    LOGIN("/fxml/login.fxml"),
    CREATE_ACCOUNT("/fxml/createAccount.fxml"),
    MAIN_MENU("/fxml/mainMenu.fxml"),
    VIEW_INVENTORY("/fxml/viewCards.fxml"),
    VIEW_DATABASE("/fxml/viewCards.fxml"),
//  SHOP(""),
    ;

    final String fxml;

    Page(String fxml) {
        this.fxml=fxml;
    }

    public String getFXML() {
        return fxml;
    }
}
