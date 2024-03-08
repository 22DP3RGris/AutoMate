package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML 
    private Text usernameError;
    @FXML
    private TextField eMailField;
    @FXML 
    private Text eMailError;
    @FXML
    private PasswordField passwordField;
    @FXML 
    private Text passwordError;
    @FXML
    private PasswordField cPasswordField;
    @FXML 
    private Text cPasswordError;

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
        if (!Validator.validateEmail(email)){
            eMailError.setText("Input valid E-mail."); eMailError.setVisible(true);
            dataIsValid = false;
        }
        if (!Validator.validatePassword(password)){
            passwordError.setText("Password must be Strong."); passwordError.setVisible(true);
            dataIsValid = false;
        }
        if (!password.equals(cPassword) && Validator.validatePassword(password)){
            cPasswordError.setText("Please make sure your Passwords match.");
            cPasswordError.setVisible(true);
            dataIsValid = false;
        }
        if (dataIsValid){
            User user = new User(username, password, email);
            System.out.println(user);
            App.setRoot("Login");
        }
    }

    @FXML
    private void redirectToLogin() throws IOException{
        App.setRoot("Login");
    }
}