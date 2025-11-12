package com.poke_frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    static Page currentPage = Page.LOGIN;

    private static Scene currentScene;

    private static Stage theStage;

    /**
     * Will change the current page the application is displaying.
     * Refer to the app class to determine which number will lead to which page.
     * @param newPage The num of the page to be switched to.
     */
    public static void changeCurrentPage(Page newPage) {

        currentPage = newPage;

        String fxml = currentPage.getFXML();

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