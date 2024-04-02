package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {

    @FXML
    private Label welcomeMsg;

    @FXML // Initialize the scene
    private void initialize(){
        // Set the welcome message
        welcomeMsg.setText("Welcome, " + CurrentUser.getUsername() + "!");
    }

}
