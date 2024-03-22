package org.openjfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MacroElements {

    public static void cloneElement(HBox source, HBox target) {
        target.getChildren().clear();
        HBox parent = (HBox) target.getParent();
        HBox.setMargin(target, new Insets(0, 0, 0, 0));
        parent.getChildren().add(0, removeBtn());
        target.setAlignment(Pos.CENTER);
        for (Node node : source.getChildren()) {
            if (node instanceof Label) {
                Label originalLabel = (Label) node;
                Label clonedLabel = new Label(originalLabel.getText());
                clonedLabel.getStyleClass().add("element-label");
                target.getChildren().add(clonedLabel);
            } else if (node instanceof TextField) {
                TextField originalTextField = (TextField) node;
                TextField clonedTextField = new TextField(originalTextField.getText());
                clonedTextField.setPrefWidth(originalTextField.getPrefWidth());
                clonedTextField.setPromptText(originalTextField.getPromptText());
                if (originalTextField.getPromptText().equals("Key")) {
                    clonedTextField.setPrefWidth(80);
                    clonedTextField.setEditable(false);
                } 
                clonedTextField.getStyleClass().add("element-input");
                HBox.setMargin(clonedTextField, new Insets(0, 0, 0, 10));
                clonedTextField.setPrefHeight(30);
                target.getChildren().add(clonedTextField);
            }
        }
        Validator.numberField(target);
    }

    public static HBox placeHolder() {
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
            
        });
        return removeBtn;
    }
}
