package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {

    @FXML
    private Label welcomeMsg;



    @FXML
    private void initialize() throws IOException{
 
        welcomeMsg.setText("Welcome, " + User.getUsername() + "!");

    }

}
