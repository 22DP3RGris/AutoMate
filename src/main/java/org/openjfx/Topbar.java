package org.openjfx;

import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Topbar{

    public static void hide(Button button) throws IOException{
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            App.getStage().setIconified(true);
        });
    }

    public static void close(Button button){
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            App.getStage().close();
        });
    }

    public static void maximize(Button button){
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            App.getStage().setMaximized(!App.getStage().isMaximized());
        });
    }

    private static double xOffSet = 0, yOffSet = 0;

    public static void dragWindow(AnchorPane topBar) {
        topBar.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            App.getStage().setX(event.getScreenX() - xOffSet);
            App.getStage().setY(event.getScreenY() - yOffSet);
        });
    }

    public static void dragDialog(AnchorPane topBar, Stage stage) {
        topBar.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
        });
    }

    public static void closeDialog(Button button, Stage stage){
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            stage.close();
        });
    }

    public static void init(Scene scene) throws IOException{
        Topbar.maximize((Button) scene.lookup("#maxBtn"));
        Topbar.hide((Button) scene.lookup("#hideBtn"));
        Topbar.close((Button) scene.lookup("#exitBtn"));
        Topbar.dragWindow((AnchorPane) scene.lookup("#topBar"));
    }
}
