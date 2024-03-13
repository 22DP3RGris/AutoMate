package org.openjfx;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MacroElements {
    @FXML
    public static void countAndDelay(Pane sourcePane, HBox targetPane) {
        targetPane.getChildren().clear();
        targetPane.setAlignment(Pos.CENTER);
        targetPane.getChildren().add(removeBtn());
        for (Node node : sourcePane.getChildren()) {
            if (node instanceof Label) {
                Label originalLabel = (Label) node;
                Label clonedLabel = new Label(originalLabel.getText());
                clonedLabel.getStyleClass().add("element-label");
                targetPane.getChildren().add(clonedLabel);
            } else if (node instanceof TextField) {
                TextField originalTextField = (TextField) node;
                TextField clonedTextField = new TextField(originalTextField.getText());
                clonedTextField.setPrefWidth(originalTextField.getPrefWidth());
                clonedTextField.setPromptText(originalTextField.getPromptText());
                clonedTextField.getStyleClass().add("element-input");
                HBox.setMargin(clonedTextField, new Insets(0, 0, 0, 10));
                clonedTextField.setPrefHeight(30);
                targetPane.getChildren().add(clonedTextField);
            }
        }
    }

    @FXML
    public static HBox placeHolder() {
        HBox placeHolder = new HBox();
        placeHolder.setPrefWidth(300);
        placeHolder.setPrefHeight(75);
        placeHolder.setAlignment(Pos.CENTER);
        VBox.setMargin(placeHolder, new Insets(20, 0, 0, 0));
        placeHolder.getStyleClass().add("placeholder");
        Label label = new Label("Select Element");
        label.getStyleClass().add("element-label");
        placeHolder.getChildren().add(label);
        return placeHolder;
    }

    @FXML 
    private static Button removeBtn() {
        Button removeBtn = new Button("X");
        removeBtn.getStyleClass().add("element-delete");
        removeBtn.setPrefWidth(20);
        removeBtn.setPrefHeight(20);
        HBox.setMargin(removeBtn, new Insets(0, 10, 0, 0));
        removeBtn.setOnAction(event -> {
            Node parentNode = ((Button) event.getSource()).getParent();
            ((VBox) parentNode.getParent()).getChildren().remove(parentNode);
        });
        return removeBtn;
    }
}
