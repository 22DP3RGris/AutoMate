package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AccountPageController {

    // Scene elements
    @FXML
    private Label usernameLabel, emailLabel, passwordLabel;

    @FXML
    private CheckBox showPassword;

    @FXML
    private Button logoutBtn;


    @FXML // Initialize the scene
    private void initialize() throws IOException{

        // Set the user's information
        usernameLabel.setText(User.getUsername());
        emailLabel.setText(User.getEmail());

        if (showPassword.isSelected()){ // Show the password
            passwordLabel.setText(User.getPassword());
        }
        else{ // Hide the password
            passwordLabel.setText("*".repeat(User.getPassword().length()));
        }

        // Show the password when the checkbox is selected
        showPassword.setOnAction(event -> {
            if (showPassword.isSelected()){
                passwordLabel.setText(User.getPassword());
            }
            else{
                passwordLabel.setText("*".repeat(User.getPassword().length()));
            }
        });
    }

    @FXML // Logout the user
    private void logout() throws IOException{
        User.clear();
        App.getStage().setMaximized(false);
        App.getStage().setResizable(false);
        App.setRoot("login", true);
    }
}
