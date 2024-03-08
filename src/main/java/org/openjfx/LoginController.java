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

        errorMsg.setVisible(false); errorMsg.setText("");

        if(usernameField.getText().equals("admin") && passwordField.getText().equals("admin")){
            App.setResizable(true);
            App.setRoot("MainPage");
        } else {
            errorMsg.setText("Incorrect password or username.");errorMsg.setVisible(true);
        }
    }

    @FXML
    private void redirectToRegister() throws IOException{
        App.setRoot("Register");
    }

}
