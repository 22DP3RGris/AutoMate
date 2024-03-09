package org.openjfx;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainPageController {

    private boolean sideNavOpen = false;

    @FXML
    private AnchorPane sideNavElements;

    @FXML
    private Button logoutBtn;

    @FXML
    private void initialize(){
        sideNavElements.setTranslateX(-400);
    }

    @FXML
    private void logout() throws IOException{
        App.setResizable(false);
        App.setRoot("Login");
    }

    @FXML
    private void openSideNav() throws IOException, InterruptedException{
        TranslateTransition transit = new TranslateTransition(Duration.seconds(0.35), sideNavElements);
        double xOffset;
        if(sideNavOpen){
            sideNavOpen = false;
            xOffset = sideNavElements.getTranslateX();
            transit.setByX(-400 + xOffset);
            transit.play();
            return;
        }
        xOffset = sideNavElements.getTranslateX();
        transit.setByX(0 - xOffset);
        transit.play();
        xOffset = sideNavElements.getTranslateX();
        sideNavOpen = true;
    }

}
