package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AccountPageController {

    @FXML
    private AnchorPane sideNavElements, workspace;

    @FXML
    private Label usernameLabel, emailLabel, passwordLabel;

    @FXML
    private CheckBox showPassword;

    @FXML
    private Button createBtn, createLabel, homeBtn, homeLabel, folderBtn, folderLabel, friendsBtn, friendsLabel, settingsBtn, settingsLabel, accountBtn, accountLabel;

    @FXML
    private Button logoutBtn;

    @FXML
    private void initialize() throws IOException{
        SideNav.initSideNav(sideNavElements, workspace);
        HashMap<Button, Button> sideNavButtons = new HashMap<>();
        sideNavButtons.put(homeBtn, homeLabel);
        sideNavButtons.put(createBtn, createLabel);
        // sideNavButtons.put(folderBtn, folderLabel);
        // sideNavButtons.put(friendsBtn, friendsLabel);
        sideNavButtons.put(accountBtn, accountLabel);
        // sideNavButtons.put(settingsBtn, settingsLabel);
        SideNav.setSideNavButtons(sideNavButtons);
        SideNav.openBtns();

        usernameLabel.setText(User.getUsername());
        emailLabel.setText(User.getEmail());
        if (showPassword.isSelected()){
            passwordLabel.setText(User.getPassword());
        }
        else{
            passwordLabel.setText("*".repeat(User.getPassword().length()));
        }
        showPassword.setOnAction(event -> {
            if (showPassword.isSelected()){
                passwordLabel.setText(User.getPassword());
            }
            else{
                passwordLabel.setText("*".repeat(User.getPassword().length()));
            }
        });
    }

    @FXML
    private void logout() throws IOException{
        User.clear();
        App.getStage().setMaximized(false);
        App.getStage().setResizable(false);
        App.setRoot("login", true);
    }

    @FXML
    private void openSideNav() throws IOException, InterruptedException {
        SideNav.open(sideNavElements);
    }
}
