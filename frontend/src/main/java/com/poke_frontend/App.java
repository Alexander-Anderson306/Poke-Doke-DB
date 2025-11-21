package com.poke_frontend;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    static Page currentPage = Page.LOGIN;

    private static Scene currentScene;

    private static Stage theStage;

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

    public static <T extends ScalePage> T openPopUp(String fxmlFile, Class<T> controllerClass){
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = loader.load();

            //give popup reference to its parent stage
            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle(getTitle(loader, controllerClass));
            popupStage.initOwner(theStage);
            //user cannot interact with primary stage while popup is open
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.showAndWait();

            //return the controller of the popup
            return controllerClass.cast(loader.getController());

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