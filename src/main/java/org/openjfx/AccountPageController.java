package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AccountPageController {

    // Scene elements
    @FXML
    private Label usernameLabel, emailLabel, passwordLabel;

    @FXML
    private CheckBox showPassword;


    @FXML // Initialize the scene
    private void initialize(){

        // Set the user's information
        usernameLabel.setText(CurrentUser.getUsername());
        emailLabel.setText(CurrentUser.getEmail());

        if (showPassword.isSelected()){ // Show the password
            passwordLabel.setText(CurrentUser.getPassword());
        }
        else{ // Hide the password
            passwordLabel.setText("*".repeat(CurrentUser.getPassword().length()));
        }

        // Show the password when the checkbox is selected
        showPassword.setOnAction(event -> {
            if (showPassword.isSelected()){
                passwordLabel.setText(CurrentUser.getPassword());
            }
            else{
                passwordLabel.setText("*".repeat(CurrentUser.getPassword().length()));
            }
        });
    }

    @FXML // Logout the user
    private void logout() throws IOException{
        CurrentUser.clear();
        App.getStage().setMaximized(false);
        App.getStage().setResizable(false);
        App.setRoot("login", true);
    }
}
