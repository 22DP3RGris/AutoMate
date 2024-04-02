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

    // Scene elements
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

    @FXML // Initialize the scene
    private void initialize(){
        window.setOnKeyPressed(event -> { // If the user presses enter, submit the registration form
            if (event.getCode() == KeyCode.ENTER) {
                submitReg.fire();
            }
        });
    }

    @FXML // Process the registration form
    private void processForm() throws IOException{

        // Hide all error messages
        usernameError.setVisible(false); usernameError.setText("");
        eMailError.setVisible(false); eMailError.setText("");
        passwordError.setVisible(false); passwordError.setText("");
        cPasswordError.setVisible(false); cPasswordError.setText("");

        // Get the user's input
        String username = usernameField.getText();
        String email = eMailField.getText();
        String password = passwordField.getText();
        String cPassword = cPasswordField.getText();

        boolean dataIsValid = true;

        // Validate the user's input
        if (!Validator.validateUsername(username)){
            // If the username is invalid, show an error message
            usernameError.setText("Input valid Username."); usernameError.setVisible(true);
            dataIsValid = false;
        }
        else if (Database.usernameExist(username)){
            // If the username already exists, show an error message
            usernameError.setText("Username already exists."); usernameError.setVisible(true);
            dataIsValid = false;
        }
        if (!Validator.validateEmail(email)){
            // If the email is invalid, show an error message
            eMailError.setText("Input valid E-mail."); eMailError.setVisible(true);
            dataIsValid = false;
        }
        else if (Database.emailExist(email)){
            // If the email already exists, show an error message
            eMailError.setText("E-mail already exists."); eMailError.setVisible(true);
            dataIsValid = false;
        }
        if (!Validator.validatePassword(password)){
            // If the password is invalid, show an error message
            passwordError.setText("Password must be at least 6 characters."); passwordError.setVisible(true);
            dataIsValid = false;
        }
        if (!password.equals(cPassword) && Validator.validatePassword(password)){
            // If the passwords do not match, show an error message
            cPasswordError.setText("Please make sure your Passwords match.");
            cPasswordError.setVisible(true);
            dataIsValid = false;
        }
        if (dataIsValid){
            // If the user's input is valid, register the user
            Database.registerUser(username, password, email);
            App.setRoot("login", true);
        }
    }

    @FXML // Redirect the user to the login page
    private void redirectToLogin() throws IOException{
        App.setRoot("login", true);
    }
}