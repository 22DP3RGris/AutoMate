package org.openjfx;

import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MacroElements {

    // Store current macro
    private static HashMap<String, HashMap<String, String>> macro = new HashMap<>();

    // Store current macro name
    private static String macroName;

    // Clone needed element
    public static void cloneElement(HBox source, HBox target) {

        // Clear target
        target.getChildren().clear();

        // Clone elements
        HBox parent = (HBox) target.getParent();
        HBox.setMargin(target, new Insets(0, 0, 0, 0));
        parent.getChildren().add(0, createRemoveBtn());
        target.setAlignment(Pos.CENTER);

        for (Node node : source.getChildren()) { // For each element in the source
            if (node instanceof Label original) { // If the element is a label
                Label clone = new Label(original.getText());
                clone.setFocusTraversable(false);
                clone.getStyleClass().add("element-label");
                target.getChildren().add(clone);
            } else if (node instanceof TextField original) { // If the element is a text field
                TextField clone = new TextField(original.getText());
                clone.setPrefWidth(original.getPrefWidth());
                clone.setPromptText(original.getPromptText());
                if (original.getPromptText().equals("Key")) { // If the element is a key
                    clone.setPrefWidth(100);
                    clone.setEditable(false);
                } 
                clone.getStyleClass().add("element-input");
                HBox.setMargin(clone, new Insets(0, 0, 0, 10));
                clone.setPrefHeight(30);
                clone.setFocusTraversable(false);
                target.getChildren().add(clone);
            }
        }
        Validator.numberField(target); // Validate the number field
    }

    // Create a placeholder
    public static HBox createPlaceHolder() {

        // Create the placeholder box
        HBox mainBox = new HBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPrefWidth(400);
        mainBox.setPrefHeight(75);
        HBox placeHolder = new HBox();
        placeHolder.setPrefWidth(400);
        placeHolder.setPrefHeight(75);
        placeHolder.setAlignment(Pos.CENTER);
        VBox.setMargin(mainBox, new Insets(20, 0, 0, 0));
        HBox.setMargin(placeHolder, new Insets(0, 0, 0, 45));
        placeHolder.getStyleClass().add("placeholder");

        // Add the label to the placeholder
        Label label = new Label("Select Element");
        label.getStyleClass().add("element-label");
        placeHolder.getChildren().add(label);
        mainBox.getChildren().add(placeHolder);

        return mainBox;
    }

    private static Button createRemoveBtn() {

        // Create the remove button
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 20, 0, 0));
        removeBtn.cursorProperty().set(javafx.scene.Cursor.HAND);

        // Remove the element when the remove button is clicked
        removeBtn.setOnAction(event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            
        });
        removeBtn.setFocusTraversable(false);
        return removeBtn;
    }

    // Create macro boxes from the commands list
    public static HBox createMacroBoxes(HBox placeholder, VBox commands) {

        // Clear the commands
        while (commands.getChildren().size() > 1) { // Don't remove the buttons
            commands.getChildren().remove(commands.getChildren().size() - 1);
        }

        // Go through each commands in the macro
        for (int i = 0; i < macro.size(); i++) {

            // Get the key
            String key = String.valueOf(i);

            // Create the parent and macro box
            HBox parent = new HBox();
            parent.setAlignment(Pos.CENTER);
            parent.getChildren().add(0, createRemoveBtn());
            HBox macroBox = new HBox();
            macroBox.setPrefWidth(400);
            macroBox.setPrefHeight(75);
            macroBox.setAlignment(Pos.CENTER);
            VBox.setMargin(parent, new Insets(20, 0, 0, 0));

            // Order of elements
            String[] order = {"name", "letter", "count", "delay"}; 

            // Add elements based on the order
            for (String command : order) {

                // If the command exists
                if (macro.get(key).containsKey(command)) {

                    // Add the element, depending on the command
                    switch (command) {
                        case "name":
                            Label label = new Label(getFullCommandName(macro.get(key).get(command)));
                            label.getStyleClass().add("element-label");
                            macroBox.getChildren().add(label);
                            break;
                        case "count":
                            String countValue = macro.get(key).get(command);
                            if (countValue.equals("1")) {
                                countValue = "";
                            }
                            TextField count = new TextField(countValue);
                            count.setPrefWidth(51);
                            count.setPromptText("Count");
                            count.getStyleClass().add("element-input");
                            HBox.setMargin(count, new Insets(0, 0, 0, 10));
                            count.setPrefHeight(30);
                            count.setFocusTraversable(false);
                            macroBox.getChildren().add(count);
                            break;
                        case "delay":
                            String delayValue = macro.get(key).get(command);
                            if (delayValue.equals("100")) {
                                delayValue = "";
                            }
                            TextField delay = new TextField(delayValue);
                            delay.setPrefWidth(54);
                            if (macro.get(key).get("name").equals("W")) {
                                delay.setPromptText("Time");
                            } else {
                                delay.setPromptText("Delay");
                            }
                            delay.getStyleClass().add("element-input");
                            HBox.setMargin(delay, new Insets(0, 0, 0, 10));
                            delay.setPrefHeight(30);
                            delay.setFocusTraversable(false);
                            macroBox.getChildren().add(delay);
                            break;
                        case "letter":
                            TextField letter = new TextField(KeyCodeReverse.reverseKeyCodeToUser(macro.get(key).get(command)));
                            letter.setPrefWidth(100);
                            letter.setPromptText("Key");
                            letter.setEditable(false);
                            letter.getStyleClass().add("element-input");
                            HBox.setMargin(letter, new Insets(0, 0, 0, 10));
                            letter.setPrefHeight(30);
                            letter.setFocusTraversable(false);
                            macroBox.getChildren().add(letter);
                            break;
                    }
                }
            }
            macroBox.getStyleClass().add("placeholder");
            parent.getChildren().add(macroBox);
            commands.getChildren().add(parent);
        }

        // Return the current placeholder (target)
        return appendPlaceHolder(placeholder, commands);
    }

    // Append a placeholder to the commands
    public static HBox appendPlaceHolder(HBox placeholder, VBox elements) {
        // If last placeholder is not empty then add a new placeholder
        if (!placeholder.getChildren().isEmpty()) {
            placeholder = createPlaceHolder();
            elements.getChildren().add(placeholder);
            placeholder = (HBox) placeholder.getChildren().get(0);
        }

        return placeholder;
    }

    // Get the full command name from the abbreviation
    private static String getFullCommandName(String name) {
        switch (name) {
            case "LC":
                return "Left Click";
            case "RC":
                return "Right Click";
            case "W":
                return "Wait";
            case "P":
                return "Press";
            default:
                return "";
        }
    }

    public static void setMacro(HashMap<String, HashMap<String, String>> Macro) {
        macro = Macro;
    }

    public static void setMacroName(String name) {
        macroName = name;
    }

    public static String getMacroName() {
        return macroName;
    }

    public static HashMap<String, HashMap<String, String>> getMacro() {
        return macro;
    }
}
