package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class Validator {

    private final static String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REGEX = "^.{6,20}$";

    public static boolean validateUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validateEmail(String email){
        return email.matches(EMAIL_REGEX) && email.length() <= 50;
    }

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    @FXML
    public static void numberField(HBox source) {
        for (Node node : source.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getPromptText().equals("Key")){
                    keyField(textField);
                    continue;
                }
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }
        }
    }

    @FXML
    public static void keyField(TextField textField) {
        textField.setOnMouseClicked(event -> {
            textField.setText("");
        });
        textField.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            textField.setText(keyCode.getName());
        });
    }
}
