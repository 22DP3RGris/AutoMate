package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {

    @FXML
    private Label welcomeMsg;

    @FXML
    private void initialize(){
 
        welcomeMsg.setText("Welcome, " + CurrentUser.getUsername() + "!");

    }

}
