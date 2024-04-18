package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class HomePageController {

    @FXML
    private Label welcomeMsg;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private VBox tipList;

    @FXML // Initialize the scene
    private void initialize(){
        // Set the welcome message
        tipList.prefWidthProperty().bind(mainScroll.widthProperty());
        welcomeMsg.setText("Welcome, " + CurrentUser.getUsername() + "!");
    }

}
