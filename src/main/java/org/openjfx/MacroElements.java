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

    private static HashMap<String, HashMap<String, String>> macro = new HashMap<>();

    public static void cloneElement(HBox source, HBox target) {
        target.getChildren().clear();
        HBox parent = (HBox) target.getParent();
        HBox.setMargin(target, new Insets(0, 0, 0, 0));
        parent.getChildren().add(0, createRemoveBtn());
        target.setAlignment(Pos.CENTER);
        for (Node node : source.getChildren()) {
            if (node instanceof Label) {
                Label original = (Label) node;
                Label clone = new Label(original.getText());
                clone.setFocusTraversable(false);
                clone.getStyleClass().add("element-label");
                target.getChildren().add(clone);
            } else if (node instanceof TextField) {
                TextField original = (TextField) node;
                TextField clone = new TextField(original.getText());
                clone.setPrefWidth(original.getPrefWidth());
                clone.setPromptText(original.getPromptText());
                if (original.getPromptText().equals("Key")) {
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
        Validator.numberField(target);
    }

    public static HBox createPlaceHolder() {
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
        Label label = new Label("Select Element");
        label.getStyleClass().add("element-label");
        placeHolder.getChildren().add(label);
        mainBox.getChildren().add(placeHolder);
        return mainBox;
    }

    private static Button createRemoveBtn() {
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(30);
        removeBtn.setPrefHeight(30);
        HBox.setMargin(removeBtn, new Insets(0, 20, 0, 0));
        removeBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
        removeBtn.setOnAction(event -> {
            HBox parentNode = (HBox)((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
            
        });
        removeBtn.setFocusTraversable(false);
        return removeBtn;
    }

    public static void setMacro(HashMap<String, HashMap<String, String>> Macro) {
        macro = Macro;
    }

    public static HashMap<String, HashMap<String, String>> getMacro() {
        return macro;
    }

    public static HBox createMacroBoxes(HBox placeholder, VBox commands) {
        while (commands.getChildren().size() > 1) {
            commands.getChildren().remove(commands.getChildren().size() - 1);
        }
        for (int i = 0; i < macro.size(); i++) {

            String key = String.valueOf(i);

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

            for (String command : order) { 
                if (macro.get(key).containsKey(command)) {
                    if (command.equals("name")) {
                        Label label = new Label(getFullCommandName(macro.get(key).get(command)));
                        label.getStyleClass().add("element-label");
                        macroBox.getChildren().add(label);
                    } else if (command.equals("count")) {
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
                    } else if (command.equals("delay")) {
                        String delayValue = macro.get(key).get(command);
                        if (delayValue.equals("100")) {
                            delayValue = "";
                        }
                        TextField delay = new TextField(delayValue);
                        delay.setPrefWidth(54);
                        if (macro.get(key).get("name").equals("W")) {
                            delay.setPromptText("Time");
                        } else{
                            delay.setPromptText("Delay");
                        }
                        delay.getStyleClass().add("element-input");
                        HBox.setMargin(delay, new Insets(0, 0, 0, 10));
                        delay.setPrefHeight(30);
                        delay.setFocusTraversable(false);
                        macroBox.getChildren().add(delay);
                    } else if (command.equals("letter")) {
                        TextField letter = new TextField(KeyCodeReverse.reverseKeyCodeToUser(macro.get(key).get(command)));
                        letter.setPrefWidth(100);
                        letter.setPromptText("Key");
                        letter.setEditable(false);
                        letter.getStyleClass().add("element-input");
                        HBox.setMargin(letter, new Insets(0, 0, 0, 10));
                        letter.setPrefHeight(30);
                        letter.setFocusTraversable(false);
                        macroBox.getChildren().add(letter);
                    }
                }
            }
            macroBox.getStyleClass().add("placeholder");
            parent.getChildren().add(macroBox);
            commands.getChildren().add(parent);
        }
        return appendPlaceHolder(placeholder, commands);
    }

    public static HBox appendPlaceHolder(HBox placeholder, VBox elements) {
        // If last placeholder is not empty then add a new placeholder
        if (!placeholder.getChildren().isEmpty()) {
            placeholder = MacroElements.createPlaceHolder();
            elements.getChildren().add(placeholder);
            placeholder = (HBox) placeholder.getChildren().get(0);
        }

        return placeholder;
    }

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
}
