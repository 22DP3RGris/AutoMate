package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainPageController {

    @FXML
    private Button logoutBtn;
    @FXML 
    private AnchorPane window;

    // TopBar
    @FXML
    private void logout() throws IOException{
        App.setResizable(false);
        App.setRoot("Login");
    }

    @FXML
    private void maximizeApp() throws IOException{
        TopBarController.maximize();
    }

    @FXML
    private void hideApp() throws IOException{
        TopBarController.hide();
    }

    @FXML
    private void exitApp() throws IOException{
        TopBarController.close();
    }
}
