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

    // Scene elements
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

    @FXML // Initialize the scene
    private void initialize(){

        // If the user presses enter, submit the login form
        window.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitLogin.fire();
            }
        });
    }
    
    @FXML // Process the login form
    private void processForm() throws IOException{

        // Check if the fields are not empty
        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()){

            // Check if the user's credentials are correct
            if(Database.loginUser(usernameField.getText(), passwordField.getText())){
                App.setResizable(true);
                App.setRoot("homePage", true);
            }
        }
        errorMsg.setText("Incorrect password or username.");errorMsg.setVisible(true);
    }

    @FXML // Redirect the user to the register page
    private void redirectToRegister() throws IOException{
        App.setRoot("register", true);
    }

}
