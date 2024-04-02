package org.openjfx;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Topbar{

    // Hide the window
    public static void hide(Button button) throws IOException{
        if (button == null) return; // If the button is null, return nothing
        button.setOnMouseReleased(event -> {
            App.getStage().setIconified(true); // Minimize the window
        });
    }

    // Close the window
    public static void close(Button button){
        if (button == null) return; // If the button is null, return nothing
        button.setOnMouseReleased(event -> {
            App.getStage().close(); // Close the window
        });
    }

    // Maximize the window
    public static void maximize(Button button){
        if (button == null) return; // If the button is null, return nothing
        button.setOnMouseReleased(event -> {
            App.getStage().setMaximized(!App.getStage().isMaximized()); // Maximize the window
        });
    }

    private static double xOffSet = 0, yOffSet = 0; // Initialize the x and y offset

    public static void dragWindow(AnchorPane topBar) { // Drag the window

        // When the mouse is pressed, set the x and y offset
        topBar.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        // When the mouse is dragged, change the window's position
        topBar.setOnMouseDragged(event -> {
            App.getStage().setX(event.getScreenX() - xOffSet);
            App.getStage().setY(event.getScreenY() - yOffSet);
        });
    }

    public static void dragDialog(AnchorPane topBar, Stage stage) {

        // When the mouse is pressed, set the x and y offset
        topBar.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        // When the mouse is dragged, change the window's position
        topBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
        });
    }

    // Close the dialog
    public static void closeDialog(Button button, Stage stage){
        if (button == null) return; // If the button is null, return nothing
        button.setOnMouseReleased(event -> {
            stage.close(); // Close the dialog
        });
    }

    // Initialize the topbar functionality
    public static void init(Scene scene) throws IOException{
        Topbar.maximize((Button) scene.lookup("#maxBtn"));
        Topbar.hide((Button) scene.lookup("#hideBtn"));
        Topbar.close((Button) scene.lookup("#exitBtn"));
        Topbar.dragWindow((AnchorPane) scene.lookup("#topBar"));
    }
}
