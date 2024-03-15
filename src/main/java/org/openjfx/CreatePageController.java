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
import javafx.scene.layout.VBox;

public class CreatePageController {

    @FXML
    private AnchorPane sideNavElements, workspace;

    @FXML
    private HBox placeholder, leftClick, rightClick, wait, oneKey;

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
        Validator.numberField(leftClick);
        setElements(leftClick, rightClick, wait, oneKey);
    }

    @FXML
    private void setElements(HBox... sourcePanes) {
        for (HBox sourcePane : sourcePanes) {
            sourcePane.setOnMouseClicked(event -> {
                MacroElements.countAndDelay(sourcePane, placeholder);
                placeHolder();
            });
        }
    }


    @FXML
    private void placeHolder() {
        if (!placeholder.getChildren().isEmpty()) {
            placeholder = MacroElements.placeHolder();
            elements.getChildren().add(placeholder);
        }
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
    private HashMap<Integer, HashMap<String, String>> getCommands() {
        HashMap<Integer, HashMap<String, String>> commands = new HashMap<>();
        String name = "";
        String count = "";
        String delay = "";
        String letter = "";
        byte parameterCounter = 0;
        for (Node node : elements.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                parameterCounter = 0;
                name = "";
                letter = "";
                count = "";
                delay = "";
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label) {
                        Label label = (Label) child;
                        parameterCounter++;
                        for (char c : label.getText().toCharArray()) {
                            if (Character.isUpperCase(c)) {
                                name += String.valueOf(c);
                            }
                        }
                    } else if (child instanceof TextField) {
                        TextField textField = (TextField) child;
                        parameterCounter++;
                        if (textField.getPromptText().equals("Count")) {
                            if (textField.getText() == ""){
                                count = "1";
                            }
                            else{
                                count = textField.getText();
                            }
                        } else if (textField.getPromptText().equals("Delay") || textField.getPromptText().equals("Time")){
                            if (textField.getText() == ""){
                                delay = "100";
                            }
                            else{
                                delay = textField.getText();
                            }
                        } else if (textField.getPromptText().equals("Key")){
                            letter = textField.getText();
                        }
                    }
                }
                if (!letter.isEmpty() && parameterCounter == 4){
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("letter", letter);
                    command.put("count", count);
                    command.put("delay", delay);
                    commands.put(commands.size(), command);
                } else
                if (!count.isEmpty() && !delay.isEmpty() && parameterCounter == 3) {
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("count", count);
                    command.put("delay", delay);
                    commands.put(commands.size(), command);
                } else if (!delay.isEmpty() && parameterCounter == 2){
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("delay", delay);
                    commands.put(commands.size(), command);
                }
                System.out.println(name + " " + count + " " + delay);
            }
        }
        return commands;
    }
}
