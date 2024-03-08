package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainPageController {

    @FXML
    private Button logoutBtn;

    @FXML
    private void logout() throws IOException{
        App.setResizable(false);
        App.setRoot("Login");
    }

}
