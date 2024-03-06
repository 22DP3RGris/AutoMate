package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMsg;
    
    @FXML
    private void processForm() throws IOException{
        errorMsg.setVisible(false);
        if(usernameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            App.setResizable(true);
            App.setRoot("MainPage");
        } else {
            errorMsg.setVisible(true);
        }
    }

    @FXML
    private void redirectToRegister() throws IOException{
        App.setRoot("Register");
    }

    @FXML
    private void redirectToMainPage() throws IOException{
        App.setRoot("MainPage");
    }


    // TopBar
    @FXML
    private void hideApp() throws IOException{
        TopBarController.hide();
    }

    @FXML
    private void exitApp() throws IOException{
        TopBarController.close();
    }
}
