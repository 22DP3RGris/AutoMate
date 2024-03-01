package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;

public class LoginController {

    @FXML
    public void exitApp(){
        System.exit(0);
    }

    @FXML
    public void redirectToRegister() throws IOException{
        App.setRoot("Register");
    }
}
