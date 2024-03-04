package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void exitApp(){
        App.getStage().close();
    }

    @FXML
    private void processForm() throws IOException{
        App.setResizable(true);
        App.setRoot("MainPage");
    }

    @FXML
    private void redirectToRegister() throws IOException{
        App.setRoot("Register");
    }

    @FXML
    private void redirectToMainPage() throws IOException{
        App.setRoot("MainPage");
    }
}
