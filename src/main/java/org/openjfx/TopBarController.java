package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TopBarController{

    @FXML
    public static void hide() throws IOException{
        App.minimizeStage();
    }

    @FXML
    public static void close() throws IOException{
        App.getStage().close();
    }

    @FXML
    public static void maximize() throws IOException{
        if(App.getStage().isMaximized()){
            App.getStage().setMaximized(false);
        } else {
            App.getStage().setMaximized(true);
        }
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

}
