package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HomePageController {

    @FXML
    private AnchorPane sideNavElements, workspace;

    @FXML
    private Label welcomeMsg;

    @FXML
    private Button createBtn, createLabel, homeBtn, homeLabel, folderBtn, folderLabel, friendsBtn, friendsLabel, settingsBtn, settingsLabel, accountBtn, accountLabel;

    @FXML
    private void initialize() throws IOException{
        welcomeMsg.setText("Welcome, " + User.getUsername() + "!");

        SideNav.initSideNav(sideNavElements, workspace);

        HashMap<Button, Button> sideNavButtons = new HashMap<>();
        sideNavButtons.put(homeBtn, homeLabel);
        sideNavButtons.put(createBtn, createLabel);
        sideNavButtons.put(folderBtn, folderLabel);
        // sideNavButtons.put(friendsBtn, friendsLabel);
        sideNavButtons.put(accountBtn, accountLabel);
        // sideNavButtons.put(settingsBtn, settingsLabel);
        SideNav.setSideNavButtons(sideNavButtons);
        SideNav.openBtns();

    }

    @FXML
    private void openSideNav() throws IOException, InterruptedException {
        SideNav.open(sideNavElements);
    }
}
