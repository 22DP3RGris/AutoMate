package org.openjfx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class FolderPageController {

    // Scene elements
    @FXML
    private VBox macroList;

    @FXML
    private ScrollPane mainScroll;

    private HashMap<String, HashMap<String, HashMap<String, String>>> allMacros = new HashMap<>();

    @FXML
    private ToggleGroup sort;

    @FXML // Initialize the scene
    private void initialize(){

        // Set the width of the macro list to the width of the scroll pane
        macroList.prefWidthProperty().bind(mainScroll.widthProperty());

        // Get the macros from the database and create the macro boxes
        allMacros = JsonManager.readMacrosFromJson();

        updateMacroBoxes();
    }

    @FXML // Sync the local macros with the database
    private void syncWithDb(){

        // Get the macros from the database and update the JSON file
        allMacros = new HashMap<>(Database.getMacros());
        JsonManager.updateMacros(allMacros);
        allMacros = JsonManager.readMacrosFromJson();

        // Update the macro boxes
        updateMacroBoxes();
    }

    @FXML // Update the macro boxes
    private void updateMacroBoxes(){
        // Clear the macro list
        while (macroList.getChildren().size() > 2) {
            macroList.getChildren().remove(macroList.getChildren().size() - 1);
        }
        // Get and sort the macro names
        ArrayList<String> macroNames = new ArrayList<>(allMacros.keySet());
        // Sort the macro names based on the selected sort
        if (((ToggleButton) sort.getSelectedToggle()).getId().equals("aToZ")){
            Sorter.sortMacroName(macroNames, false);
        }
        else if (((ToggleButton) sort.getSelectedToggle()).getId().equals("zToA")){
            Sorter.sortMacroName(macroNames, true);
        }
        // Create the macro boxes
        for (String macroName : macroNames){
            createMacroBox(macroName);
        }
    }

    // Create a single macro box
    private void createMacroBox(String macroName){

        // Create the macro box
        HBox parent = new HBox();
        HBox macroBox = new HBox();
        macroBox.setCursor(Cursor.HAND);
        macroBox.setPrefWidth(600);
        macroBox.setPrefHeight(60);
        parent.setAlignment(Pos.CENTER);
        macroBox.setAlignment(Pos.CENTER);
        macroBox.getStyleClass().add("placeholder");
        VBox.setMargin(parent, new Insets(20, 180, 0, 200));

        // Add the macro name to the macro box
        Label label = new Label(macroName);
        label.getStyleClass().add("element-label");
        macroBox.getChildren().add(label);
        macroBox.getStyleClass().add("open-element");

        // Open the macro when the macro box is clicked
        macroBox.setOnMouseClicked(event -> {
            try {
                MacroElements.setMacro(allMacros.get(macroName));
                MacroElements.setMacroName(macroName);
                App.setRoot("createPage", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Create the share button
        Button sendMacro = new Button("Share");
        sendMacro.getStyleClass().add("run-btn");
        sendMacro.setPrefWidth(100);
        sendMacro.setPrefHeight(30);
        sendMacro.setFocusTraversable(false);
        HBox.setMargin(sendMacro, new Insets(0, 0, 0, 20));
        sendMacro.cursorProperty().set(Cursor.HAND);

        // Share the macro when the share button is clicked
        sendMacro.setOnAction(event -> {
            try {
                shareMacro(macroName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add elements to the parent
        parent.getChildren().add(removeBtn());
        parent.getChildren().add(macroBox);
        parent.getChildren().add(sendMacro);
        macroList.getChildren().add(parent);
    }

    // Create the remove button for the macro box
    private static Button removeBtn() {

        // Create the remove button
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 20, 0, 60));
        removeBtn.cursorProperty().set(javafx.scene.Cursor.HAND);

        // Remove the macro box when the remove button is clicked
        removeBtn.setOnAction(event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            String macroName = ((Label) ((HBox)parentNode.getChildren().get(1)).getChildren().get(0)).getText();
            Database.deleteMacro(macroName);
            JsonManager.removeMacro(macroName);
        });
        removeBtn.setFocusTraversable(false);
        return removeBtn;
    }

    // Share the macro
    private static void shareMacro(String macroName) throws IOException {

        // Create the share macro dialog stage
        Stage dialogStage = App.createDialogStage("ShareMacro");

        // Get the scene elements
        TextField friendsName = (TextField) dialogStage.getScene().lookup("#friendsName");
        Button shareBtn = (Button) dialogStage.getScene().lookup("#shareBtn");
        Text friendsNameError = (Text) dialogStage.getScene().lookup("#friendsNameError");

        // Share the macro when the share button is clicked
        shareBtn.setOnAction(event -> {
            friendsNameError.setVisible(false);
            String friendName = friendsName.getText();
            if (friendName.isEmpty()) { // If the username is empty, show an error message
                friendsNameError.setText("Enter a username.");
                friendsNameError.setVisible(true);
            } else if (!Database.friendExists(friendName)) { // If the user isn't a friend, show an error message
                friendsNameError.setText("User isn't your friend.");
                friendsNameError.setVisible(true);
            } else{ // Share the macro
                Database.sentMacroShareRequest(friendName, macroName);
                dialogStage.close();
            }
        });

        dialogStage.showAndWait();
    }

    @FXML
    private void incomingMacros() throws IOException {
        Stage dialogStage = App.createDialogStage("IncomingMacros");

        // Get the scroll pane
        ScrollPane scroll = (ScrollPane) dialogStage.getScene().lookup("#scroll");

        // Get the requests vbox
        VBox requests = (VBox) scroll.getContent();

        // Set the width of the requests vbox to the width of the scroll pane
        requests.prefWidthProperty().bind(scroll.widthProperty());

        // Get the incoming macros from the database
        HashMap<String, HashMap<String, String>> incomingMacros = Database.getIncomingMacros();
        for (String friend : incomingMacros.keySet()){
            for (String macro : incomingMacros.get(friend).keySet()){
                createIncomingMacroBox(requests, macro, friend);
            }
        }
        dialogStage.showAndWait();
    }

    // Create the incoming macro box
    private void createIncomingMacroBox(VBox requests, String macroName, String friendName){

        // Create the incoming macro box
        HBox parent = new HBox();
        parent.setAlignment(Pos.CENTER);

        HBox macroBox = new HBox();
        macroBox.setPrefWidth(400);
        macroBox.setPrefHeight(75);
        macroBox.setAlignment(Pos.CENTER);
        macroBox.getStyleClass().add("placeholder");
        VBox.setMargin(parent, new Insets(20, 50, 10, 0));

        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 0, 0, 40));
        removeBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
        removeBtn.setOnAction( event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            Database.removeIncomingMacro(friendName, macroName);
        });
        parent.getChildren().add(removeBtn);

        Button acceptBtn = new Button("✔");
        acceptBtn.getStyleClass().add("element-delete");
        acceptBtn.setPrefWidth(30);
        acceptBtn.setPrefHeight(30);
        HBox.setMargin(acceptBtn, new Insets(0, 20, 0, 10));
        acceptBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
        acceptBtn.setOnAction( event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            // Database.acceptIncomingMacro(friendName, macroName);
            // JsonManager.writeMacro(macroName, Database.getMacro(friendName, macroName));
        });
        parent.getChildren().add(acceptBtn);

        Label label = new Label(macroName);
        label.getStyleClass().add("element-label");
        macroBox.getChildren().add(label);
        HBox.setMargin(macroBox, new Insets(0, 20, 0, 0));
        parent.getChildren().add(macroBox);

        label = new Label("From: " + friendName);
        label.getStyleClass().add("element-label");
        parent.getChildren().add(label);

        requests.getChildren().add(parent);
    }
}
