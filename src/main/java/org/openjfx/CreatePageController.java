package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CreatePageController {

    @FXML
    private AnchorPane sideNavElements, workspace;

    @FXML
    private Pane leftClick;

    @FXML
    private HBox placeholder;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private VBox elements;

    @FXML
    private Button createBtn, createLabel, homeBtn, homeLabel, folderBtn, folderLabel, friendsBtn, friendsLabel, settingsBtn, settingsLabel, accountBtn, accountLabel;

    @FXML
    private void initialize() throws IOException{
        elements.prefWidthProperty().bind(mainScroll.widthProperty());
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
        setTarget(leftClick);
    }

    @FXML
    private void setTarget(Pane sourcePane) {
        sourcePane.setOnMouseClicked(event -> {
            MacroElements.cloneLeftClick(sourcePane, placeholder);
            placeHolder();
        });
    }

    @FXML
    private void placeHolder() {
        if (!placeholder.getChildren().isEmpty()) {
            placeholder = MacroElements.placeHolder();
            elements.getChildren().add(placeholder);
        }
    }


    @FXML
    private Pane getPane(Pane sourcePane) {
        Pane newPane = new Pane();
        newPane.getChildren().add(sourcePane);
        return newPane;
    }

    @FXML
    private void openSideNav() throws IOException, InterruptedException {
        SideNav.open(sideNavElements);
    }

    @FXML
    private void runMacro() {
        MacroFunctionality.runMacro(getCommands());
    }

    @FXML
    private HashMap<String, String> getCommands() {
        HashMap<String, String> commands = new HashMap<>();
        String command = "";
        String value = "";
        for (Node node : elements.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                command = "";
                value = "";
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label) {
                        Label label = (Label) child;
                        for (char c : label.getText().toCharArray()) {
                            if (Character.isUpperCase(c)) {
                                command += String.valueOf(c);
                            }
                        }
                    } else if (child instanceof TextField) {
                        TextField textField = (TextField) child;
                        if (textField.getText() == ""){
                            value = "0";
                        }
                        else{
                            value = textField.getText();
                        }
                    }
                    if (value != "")
                    {
                        System.out.println(command + " " + value);
                        commands.put(command, value);
                    }
                }
            }
        }
        return commands;
    }
}
