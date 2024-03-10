package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

public class Topbar{

    @FXML
    public static void hide(Button button) throws IOException{
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            App.getStage().setIconified(true);
        });
    }

    @FXML
    public static void close(Button button) throws IOException{
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            App.getStage().close();
        });
    }

    @FXML
    public static void maximize(Button button) throws IOException{
        if (button == null) return;
        button.setOnMouseReleased(event -> {
            if(App.getStage().isMaximized()){
                App.getStage().setMaximized(false);
            } else {
                App.getStage().setMaximized(true);
            }
        });
    }

    private static double xOffSet = 0, yOffSet = 0;

    @FXML
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
