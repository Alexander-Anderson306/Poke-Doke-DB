package com.poke_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    // 0 for login, 1 for main menu, 2 for inventory, 3 for database, 4 for shop.
    // Carson will make this an enum later.
    static int currentPage = 0;

    private static Scene currentScene;

    private static Stage theStage;

    /**
     * Will change the current page the application is displaying.
     * Refer to the app class to determine which number will lead to which page.
     * @param newPage The num of the page to be switched to.
     */
    public static void changeCurrentPage(int newPage) {

        currentPage = newPage;

        String fxml;
        if (currentPage==0)
            fxml = "/fxml/login.fxml";
        else if (currentPage==1)
            fxml = "/fxml/mainMenu.fxml";
        else if (currentPage==2 || currentPage==3)
            fxml = "/fxml/viewCards.fxml";
        else {
            fxml = "";
            System.out.println("ERROR: Invalid Page Destination.");
            System.exit(27);
        }

        try {
            currentScene = new Scene(FXMLLoader.load(App.class.getResource(fxml)));
            theStage.setScene(currentScene);
            theStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void start(Stage stage) throws IOException {

        theStage = stage;

        currentScene = new Scene(FXMLLoader.load(App.class.getResource("/fxml/login.fxml")));
        stage.setScene(currentScene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}