package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private AnchorPane window;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorMsg;

    @FXML
    private Button submitLogin;

    @FXML
    private void initialize(){
        window.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLogin.fire();
            }
        });
    }
    
    @FXML
    private void processForm() throws IOException{

        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            if(Database.loginUser(usernameField.getText(), passwordField.getText())){
                App.setResizable(true);
                App.setRoot("homePage", true);
            }
        }
        errorMsg.setText("Incorrect password or username.");errorMsg.setVisible(true);
    }

    @FXML
    private void redirectToRegister() throws IOException{
        App.setRoot("register", true);
    }

}
