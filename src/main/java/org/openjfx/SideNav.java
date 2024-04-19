package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SideNav {

    // Store side navigation buttons
    private static HashMap<Button, Button> sideNavButtons;

    // Store side navigation state
    public static boolean sideNavOpen;

    public static void init(Scene scene){ // Initialize the side navigation

        AnchorPane sideNavElements = (AnchorPane) scene.lookup("#sideNavElements");
        AnchorPane workspace = (AnchorPane) scene.lookup("#workspace");

        // Set side navigation buttons
        sideNavButtons = getSideNavButtons();

        // Hide side navigation
        sideNavOpen = false;
        sideNavElements.setTranslateX(-300);

        Button sideNavBtn = (Button) App.getScene().lookup("#sideNavBtn");

        // Open side navigation when the user clicks on the side navigation button
        sideNavBtn.setOnAction(event -> {
            try {
                open(sideNavElements);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // If the user clicks on the workspace, close the side navigation
        workspace.setOnMouseClicked(event -> {
            if (sideNavOpen){
                try {
                    close(sideNavElements);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SideNav.openBtns(); // Prepare the side navigation buttons
    }

    private static double xOffset;
    // Open the side navigation
    public static void open(AnchorPane sideNavElements){

        // Show the side navigation
        sideNavElements.setVisible(true);

        // Create a transition
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);

        // If the side navigation is open, close it
        if(sideNavOpen){
            close(sideNavElements);
            sideNavOpen = false;
        }

        // Open the side navigation
        openLabels();

        // Play the transition
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(0 - xOffset);
        transit.play();
        sideNavOpen = true;
    }

    // Close the side navigation
    public static void close(AnchorPane sideNavElements){

        // Create a transition
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);

        // Play the transition
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(-300 + xOffset);
        transit.play();
        sideNavOpen = false;
    }

    // Prepare the side navigation buttons onClick
    public static void openBtns(){
        for (Button btn : sideNavButtons.keySet()){ // For each button in the side navigation
            String page = btn.getId().split("B")[0] + "Page"; // Get the page name
            btn.setOnMouseReleased(event -> {
                try {
                    // Clear the current macro
                    MacroElements.setMacro(null);

                    // If the current scene is not the page, set the root to the page
                    if (!App.getCurrentScene().equals(page)){
                        App.setRoot(page, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // Prepare the side navigation labels onClick
    public static void openLabels(){
        for (Button btn : sideNavButtons.keySet()){ // For each button in the side navigation
            String page = btn.getId().split("B")[0] + "Page"; // Get the page name
            sideNavButtons.get(btn).setOnMouseReleased(event -> {
                try {
                    // Clear the current macro
                    MacroElements.setMacro(null);

                    // If the current scene is not the page, set the root to the page
                    if (!App.getCurrentScene().equals(page)){
                        App.setRoot(page, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // Get the side navigation buttons and labels to HashMap
    private static HashMap<Button, Button> getSideNavButtons(){
        HashMap<Button, Button> sideNavButtons = new HashMap<>();
        Scene scene = App.getStage().getScene();
        sideNavButtons.put(((Button) scene.lookup("#homeBtn")), (Button) scene.lookup("#homeLabel"));
        sideNavButtons.put((Button) scene.lookup("#createBtn"), (Button) scene.lookup("#createLabel"));
        sideNavButtons.put((Button) scene.lookup("#folderBtn"), (Button) scene.lookup("#folderLabel"));
        sideNavButtons.put((Button) scene.lookup("#friendsBtn"), (Button) scene.lookup("#friendsLabel"));
        sideNavButtons.put((Button) scene.lookup("#accountBtn"), (Button) scene.lookup("#accountLabel"));
        return sideNavButtons;
    }

}
