package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SideNav {

    private static HashMap<Button, Button> sideNavButtons = null;
    public static boolean sideNavOpen;

    @FXML
    public static void initSideNav(AnchorPane sideNavElements, AnchorPane workspace) throws IOException{
        sideNavOpen = false;
        sideNavElements.setTranslateX(-300);
        workspace.setOnMouseClicked(event -> {
            if (sideNavOpen){
                try {
                    close(sideNavElements);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setSideNavButtons(HashMap<Button, Button> buttons){
        sideNavButtons = buttons;
    }

    public static boolean isSideButtonsNull(){
        return sideNavButtons == null;
    }

    @FXML
    public static void open(AnchorPane sideNavElements) throws IOException, InterruptedException{
        sideNavElements.setVisible(true);
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);
        double xOffset;
        if(sideNavOpen){
            close(sideNavElements);
            sideNavOpen = false;
        }
        openLabels();
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(0 - xOffset);
        transit.play();
        xOffset = sideNavElements.getTranslateX();
        sideNavOpen = true;
    }

    @FXML
    public static void close(AnchorPane sideNavElements) throws IOException, InterruptedException{
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);
        double xOffset;
        closeLabels();
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(-300 + xOffset);
        transit.play();
        sideNavOpen = false;
    }

    @FXML
    public static void openBtns() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            String page = btn.getId().split("B")[0] + "Page";
            btn.setOnMouseReleased(event -> {
                try {
                    App.setRoot(page, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    public static void openLabels() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            String page = btn.getId().split("B")[0] + "Page";
            sideNavButtons.get(btn).setOnMouseReleased(event -> {
                try {
                    App.setRoot(page, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });;
        }
    }

    @FXML
    public static void closeLabels() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            sideNavButtons.get(btn).removeEventHandler(MouseEvent.MOUSE_RELEASED, sideNavButtons.get(btn).getOnMouseReleased());
        }
    }
}
