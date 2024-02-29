package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;

public class RegisterController {

    @FXML
    public void exitApp(){
        System.exit(0);
    }

    @FXML
    public void redirectToLogin() throws IOException{
        App.setRoot("Login");
    }
}