package com.poke_frontend;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class App extends Application {

    static Page currentPage = Page.LOGIN;

    private static Scene currentScene;

    private static Stage theStage;

    public static Client theClient = null;

    /**
     * Returns whether there is a client currently logged into the software.
     * @return Whether there is a client currently logged into the software.
     */
    public static boolean loggedIn() {
        return (theClient!=null);
    }

    /**
     * Will set the current client to null.
     */
    public static void logout() {
        theClient=null;
    }

    /**
     * Will change the current page the application is displaying.
     * @param newPage The page to be switched to.
     */
    public static void changeCurrentPage(Page newPage) {

        Double sceneWidth = currentScene.getWidth();
        Double sceneheight = currentScene.getHeight();

        currentPage = newPage;

        String fxml = currentPage.getFXML();

        try {
            currentScene = new Scene(FXMLLoader.load(App.class.getResource(fxml)), sceneWidth, sceneheight);
            theStage.setScene(currentScene);
            theStage.setTitle(newPage.getSceneName());
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
        theStage.setTitle("Login");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    //Consumer is a function that takes T and returns nothing. Meaning we can user the consumer to call our initialization stuff
    public static <T extends ScalePage> T openPopUp(String fxmlFile, Class<T> controllerClass, Consumer<T> init)
    {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = loader.load();

            //create the controller and initialize it
            T controller = loader.getController();
            if(init != null) {
                init.accept(controller);
            }

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.initOwner(theStage);
            popupStage.initModality(Modality.WINDOW_MODAL);

            popupStage.showAndWait();

            return controller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends ScalePage> String getTitle(FXMLLoader loader, Class<T> controllerClass){
        //Connect the page methods to driver
        T pageMethod = loader.getController();

        return pageMethod.getSceneName();
    }

}