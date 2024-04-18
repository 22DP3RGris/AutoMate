package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class Validator {

    // Regular expressions
    private final static String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REGEX = "^.{6,20}$";

    // Validate username based on regex
    public static boolean validateUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    // Validate email based on regex
    public static boolean validateEmail(String email){
        return email.matches(EMAIL_REGEX) && email.length() <= 50;
    }

    // Validate password based on regex
    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    // Validate number field
    @FXML
    public static void numberField(HBox source) {
        for (Node node : source.getChildren()) { // For each element in the source
            if (node instanceof TextField textField) { // If the element is a text field
                if (textField.getPromptText().equals("Key")){ // If the element is a key
                    keyField(textField); // Validate the key field
                    continue; // Skip the rest of the loop
                }
                if (textField.getPromptText().equals("Text")){ // If the element is a text
                    continue;
                }
                // Validate the number field
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) { // If the value is not a number
                        // Replace the value with an empty string
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            }
        }
    }

    // Validate the key field
    public static void keyField(TextField textField) {
        // Clear the text field on click
        textField.setOnMouseClicked(event -> {
            textField.setText("");
        });
        // Set the key code
        textField.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            textField.setText(keyCode.getName());
        });
    }
}
