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

    private static HashMap<Button, Button> sideNavButtons;
    public static boolean sideNavOpen;

    public static void init(AnchorPane sideNavElements, AnchorPane workspace) throws IOException{
        SideNav.setSideNavButtons(getSideNavButtons());
        sideNavOpen = false;
        sideNavElements.setTranslateX(-300);
        Button sideNavBtn = (Button) App.getScene().lookup("#sideNavBtn");
        sideNavBtn.setOnAction(event -> {
            try {
                open(sideNavElements);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        workspace.setOnMouseClicked(event -> {
            if (sideNavOpen){
                try {
                    close(sideNavElements);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SideNav.openBtns();
    }

    public static void setSideNavButtons(HashMap<Button, Button> buttons){
        sideNavButtons = buttons;
    }

    public static boolean isSideButtonsNull(){
        return sideNavButtons == null;
    }

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

    public static void close(AnchorPane sideNavElements) throws IOException, InterruptedException{
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);
        double xOffset;
        closeLabels();
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(-300 + xOffset);
        transit.play();
        sideNavOpen = false;
    }

    public static void openBtns() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            String page = btn.getId().split("B")[0] + "Page";
            btn.setOnMouseReleased(event -> {
                try {
                    if (!App.getCurrentScene().equals(page)){
                        App.setRoot(page, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void openLabels() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            String page = btn.getId().split("B")[0] + "Page";
            sideNavButtons.get(btn).setOnMouseReleased(event -> {
                try {
                    if (!App.getCurrentScene().equals(page)){
                        App.setRoot(page, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });;
        }
    }

    public static void closeLabels() throws IOException{
        for (Button btn : sideNavButtons.keySet()){
            sideNavButtons.get(btn).removeEventHandler(MouseEvent.MOUSE_RELEASED, sideNavButtons.get(btn).getOnMouseReleased());
        }
    }

    private static HashMap<Button, Button> getSideNavButtons(){
        HashMap<Button, Button> sideNavButtons = new HashMap<>();
        Scene scene = App.getStage().getScene();
        sideNavButtons.put(((Button) scene.lookup("#homeBtn")), (Button) scene.lookup("#homeLabel"));
        sideNavButtons.put((Button) scene.lookup("#createBtn"), (Button) scene.lookup("#createLabel"));
        sideNavButtons.put((Button) scene.lookup("#folderBtn"), (Button) scene.lookup("#folderLabel"));
        // sideNavButtons.put((Button) scene.lookup("#friendsBtn"), (Button) scene.lookup("#friendsLabel"));
        sideNavButtons.put((Button) scene.lookup("#accountBtn"), (Button) scene.lookup("#accountLabel"));
        // sideNavButtons.put((Button) scene.lookup("#settingsBtn"), (Button) scene.lookup("#settingsLabel"));
        return sideNavButtons;
    }

}
