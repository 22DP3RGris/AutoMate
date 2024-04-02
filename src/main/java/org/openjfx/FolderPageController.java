package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class FolderPageController {

    // Scene elements
    @FXML
    private VBox macroList;

    @FXML
    private ScrollPane mainScroll;

    @FXML // Initialize the scene
    private void initialize(){

        // Set the width of the macro list to the width of the scroll pane
        macroList.prefWidthProperty().bind(mainScroll.widthProperty());

        // Get the macros from the database and create the macro boxes
        HashMap<String, HashMap<String, HashMap<String, String>>> macros = JsonManager.readMacrosFromJson();

        createMacroBoxes(macros);
    }

    @FXML // Sync the local macros with the database
    private void syncWithDb(){

        // Get the macros from the database and update the JSON file
        HashMap<String, HashMap<String, HashMap<String, String>>> macros = Database.getMacros();
        JsonManager.updateMacros(macros);
        macros = JsonManager.readMacrosFromJson();

        // Remove the current macro boxes and create the new ones
        while (macroList.getChildren().size() > 2) {
            macroList.getChildren().remove(macroList.getChildren().size() - 1);
        }
        createMacroBoxes(macros);
    }

    // Create the macro boxes
    private void createMacroBoxes(HashMap<String, HashMap<String, HashMap<String, String>>> macros){
        for (String macroName : macros.keySet()){
            createMacroBox(macroName, macros.get(macroName));
        }
    }

    // Create a single macro box
    private void createMacroBox(String macroName, HashMap<String, HashMap<String, String>> commands){

        // Create the macro box
        HBox parent = new HBox();
        HBox macroBox = new HBox();
        macroBox.setCursor(Cursor.HAND);
        macroBox.setPrefWidth(600);
        macroBox.setPrefHeight(75);
        parent.setAlignment(Pos.CENTER);
        macroBox.setAlignment(Pos.CENTER);
        macroBox.getStyleClass().add("placeholder");
        VBox.setMargin(parent, new Insets(20, 250, 0, 200));

        // Add the macro name to the macro box
        Label label = new Label(macroName);
        label.getStyleClass().add("element-label");
        macroBox.getChildren().add(label);
        macroBox.getStyleClass().add("open-element");

        // Open the macro when the macro box is clicked
        macroBox.setOnMouseClicked(event -> {
            try {
                MacroElements.setMacro(commands);
                MacroElements.setMacroName(macroName);
                App.setRoot("createPage", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add the macro box to the parent and add the remove button
        parent.getChildren().add(macroBox);
        parent.getChildren().add(0, removeBtn());
        macroList.getChildren().add(parent);
    }

    // Create the remove button for the macro box
    private static Button removeBtn() {

        // Create the remove button
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 20, 0, 0));
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
}
