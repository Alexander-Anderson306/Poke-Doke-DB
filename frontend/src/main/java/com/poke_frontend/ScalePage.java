package com.poke_frontend;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.beans.binding.Bindings;

/**
 * A class that gives each page the ablity to scale
 */
public abstract class ScalePage {
    
    //Save the current group and pane
    public Group scaleGroup;
    public Pane scalePane;

    public String sceneName;

    /**
     * A method that makes the group and pane to scale up with scene
     * @param currentGroup the group that gives the objects the abilty to scale up
     * @param currentPane the pane that makes the object stay in places
     */
    public void implementScaling(Group currentGroup, Pane currentPane){

        //Saves the group and pane object
        scaleGroup = currentGroup;
        scalePane = currentPane;

        //Gives a listener to the scene as well to make sure the scene exist
        scalePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
        if (newScene != null) {

        // Bind scaleX and scaleY to the group object
        scaleGroup.scaleXProperty().bind(newScene.widthProperty().divide(600));
        scaleGroup.scaleYProperty().bind(newScene.heightProperty().divide(400));
        }
        });

    }

    public void setSceneName(String newName){
        sceneName = newName;
    }

    public String getSceneName(){
        return sceneName;
    }

}
