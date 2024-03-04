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

    @FXML
    private void logout() throws IOException{
        App.setResizable(false);
        App.setRoot("Login");
    }

    @FXML
    private void exitApp(){
        App.getStage().close();
    }
}
