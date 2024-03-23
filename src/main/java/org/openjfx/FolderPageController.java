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

    @FXML
    private VBox macroList;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private void initialize() throws IOException{
        macroList.prefWidthProperty().bind(mainScroll.widthProperty());
        HashMap<String, HashMap<String, HashMap<String, String>>> macros = JsonManager.readMacrosFromJson();
        createMacroBoxes(macros);
    }

    private void createMacroBoxes(HashMap<String, HashMap<String, HashMap<String, String>>> macros){
        for (String macroName : macros.keySet()){
            createMacroBox(macroName, macros.get(macroName));
        }
    }

    private void createMacroBox(String macroName, HashMap<String, HashMap<String, String>> commands){
        HBox parent = new HBox();
        HBox macroBox = new HBox();
        macroBox.setCursor(Cursor.HAND);
        macroBox.setPrefWidth(600);
        macroBox.setPrefHeight(75);
        parent.setAlignment(Pos.CENTER);
        macroBox.setAlignment(Pos.CENTER);
        macroBox.getStyleClass().add("placeholder");
        VBox.setMargin(parent, new Insets(20, 250, 0, 200));
        Label label = new Label(macroName);
        label.getStyleClass().add("element-label");
        macroBox.getChildren().add(label);
        parent.getChildren().add(macroBox);
        parent.getChildren().add(0, removeBtn());
        macroList.getChildren().add(parent);
    }

    private static Button removeBtn() {
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 20, 0, 0));
        removeBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
        removeBtn.setOnAction(event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            String macroName = ((Label) ((HBox)parentNode.getChildren().get(1)).getChildren().get(0)).getText();
            Database.deleteMacro(macroName);
            JsonManager.removeMacroFromJson(macroName);
        });
        removeBtn.setFocusTraversable(false);
        return removeBtn;
    }
}
