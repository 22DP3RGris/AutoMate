package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class RegisterController {

    @FXML
    private AnchorPane window;
    @FXML
    private TextField usernameField, eMailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField cPasswordField;
    @FXML 
    private Text usernameError, eMailError, passwordError, cPasswordError;
    @FXML
    private Button submitReg;

    @FXML
    private void initialize(){
        window.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitReg.fire();
            }
        });
    }

    @FXML
    private void processForm() throws IOException{

        usernameError.setVisible(false); usernameError.setText("");
        eMailError.setVisible(false); eMailError.setText("");
        passwordError.setVisible(false); passwordError.setText("");
        cPasswordError.setVisible(false); cPasswordError.setText("");

        String username = usernameField.getText();
        String email = eMailField.getText();
        String password = passwordField.getText();
        String cPassword = cPasswordField.getText();

        boolean dataIsValid = true;

        if (!Validator.validateUsername(username)){
            usernameError.setText("Input valid Username."); usernameError.setVisible(true);
            dataIsValid = false;
        }
        else if (Database.usernameExist(username)){
            usernameError.setText("Username already exists."); usernameError.setVisible(true);
            dataIsValid = false;
        }
        if (!Validator.validateEmail(email)){
            eMailError.setText("Input valid E-mail."); eMailError.setVisible(true);
            dataIsValid = false;
        }
        else if (Database.emailExist(email)){
            eMailError.setText("E-mail already exists."); eMailError.setVisible(true);
            dataIsValid = false;
        }
        if (!Validator.validatePassword(password)){
            passwordError.setText("Password must be at least 6 characters."); passwordError.setVisible(true);
            dataIsValid = false;
        }
        if (!password.equals(cPassword) && Validator.validatePassword(password)){
            cPasswordError.setText("Please make sure your Passwords match.");
            cPasswordError.setVisible(true);
            dataIsValid = false;
        }
        if (dataIsValid){
            Database.registerUser(username, password, email);
            App.setRoot("login", true);
        }
    }

    @FXML
    private void redirectToLogin() throws IOException{
        App.setRoot("login", true);
    }
}