package org.openjfx;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreatePageController {

    // Scene elements
    @FXML
    private HBox placeholder, leftClick, rightClick, wait, oneKey;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private VBox elements;

    @FXML 
    private Button run;

    @FXML // Initialize the scene
    private void initialize(){

        // If the macro is not empty then create the macro boxes
        if (!MacroElements.getMacro().isEmpty()) {
            placeholder = MacroElements.createMacroBoxes(placeholder, elements);
        }

        elements.prefWidthProperty().bind(mainScroll.widthProperty()); // Center the elements
        setElements(leftClick, rightClick, wait, oneKey); // Set the elements to be clicked
        run.setOnMouseReleased(event -> {
            App.getScene().getRoot().requestFocus(); // Set the focus to the scene
            run.setDisable(true); // Disable the run button
            
            // Run the macro in a new thread to prevent the UI from freezing
            new Thread(() -> { 
                try {
                    MacroElements.setMacro(getCommands());
                    MacroFunctionality.init();
                    MacroFunctionality.runMacro(getCommands());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    run.setDisable(false);
                }
            }).start();
        });
    }

    @FXML // Set the elements to be clicked
    private void setElements(HBox... sourcePanes) {
        for (HBox sourcePane : sourcePanes) {
            sourcePane.setOnMouseClicked(event -> { 
                // If the element is clicked then clone it and add it to the elements and add a new placeholder
                MacroElements.cloneElement(sourcePane, placeholder);
                placeholder = MacroElements.appendPlaceHolder(placeholder, elements);
            });
        }
    }

    @FXML // Clear the commands
    private void clearCommands() {
        while (elements.getChildren().size() > 1){
            elements.getChildren().remove(elements.getChildren().size() - 1);
        }
        placeholder = MacroElements.appendPlaceHolder(placeholder, elements);
    }

    @FXML // Save the macro
    private void saveMacro() throws IOException {

        Stage dialogStage = App.createDialogStage("SaveMacro");

        TextField macroName = (TextField) dialogStage.getScene().lookup("#macroName");
        Text macroNameError = (Text) dialogStage.getScene().lookup("#macroNameError");
        Button saveAsBtn = (Button) dialogStage.getScene().lookup("#saveAsBtn");
        Button saveBtn = (Button) dialogStage.getScene().lookup("#saveBtn");

        // Save the macro as a new macro
        saveAsBtn.setOnAction(event ->{
            macroNameError.setVisible(false);
            if (macroName.getText().isEmpty()){
                macroNameError.setText("Name cannot be empty.");
                macroNameError.setVisible(true);
            }
            else if (Database.macroExist(macroName.getText())){
                macroNameError.setText("Macro with that name already exists.");
                macroNameError.setVisible(true);
            }
            else{
                // Save the macro to the database and the json file
                Database.saveMacro(macroName.getText(), getCommands());
                JsonManager.writeMacro(macroName.getText(), getCommands());
                dialogStage.close();
            }

        });

        // Save the macro
        saveBtn.setOnAction(event ->{
            macroNameError.setVisible(false);
            if (MacroElements.getMacroName() == null){
                macroNameError.setText("Save the macro as a new macro first.");
                macroNameError.setVisible(true);
                return;
            }
            // Save the macro to the database and the json file
            Database.saveMacro(MacroElements.getMacroName(), getCommands());
            JsonManager.writeMacro(MacroElements.getMacroName(), getCommands());
            dialogStage.close();
        });

        // Show the dialog stage
        dialogStage.showAndWait();
    }

    @FXML // Get the commands from UI elements.
    private HashMap<String, HashMap<String, String>> getCommands() {

        HashMap<String, HashMap<String, String>> commands = new HashMap<>();

        String name;
        String count;
        String delay;
        String letter;
        byte parameterCounter;
        byte counter = 0;

        // Loop through the elements and get the commands
        for (Node node : elements.getChildren()) {
            counter++;
            if (counter < 2) continue; // Skip the first element (Buttons)
            if (node instanceof HBox) {
                // If there is remove button, commands are in the second(1) HBox, otherwise in the first(0) HBox.
                HBox hbox = ((HBox) node).getChildren().size() == 2 ? (HBox) ((HBox) node).getChildren().get(1) : (HBox) ((HBox) node).getChildren().get(0);
                parameterCounter = 0;
                name = "";
                letter = "";
                count = "";
                delay = "";

                // Loop through the HBox and get the command.
                for (Node child : hbox.getChildren()) {
                    if (child instanceof Label label) { // Get the name of the command
                        parameterCounter++;
                        for (char c : label.getText().toCharArray()) {
                            if (Character.isUpperCase(c)) {
                                name += String.valueOf(c);
                            }
                        }
                    } else if (child instanceof TextField textField) { // Get the parameters of the command
                        parameterCounter++;
                        if (textField.getPromptText().equals("Count")) { // Get the count
                            if (textField.getText().isEmpty()){
                                count = "1";
                            }
                            else{
                                count = textField.getText();
                            }
                        } else if (textField.getPromptText().equals("Delay") || textField.getPromptText().equals("Time")){ // Get the delay
                            if (textField.getText().isEmpty()){
                                delay = "100";
                            }
                            else{
                                delay = textField.getText();
                            }
                        } else if (textField.getPromptText().equals("Key")){ // Get the letter
                            letter = KeyCodeReverse.reverseKeyCodeToMacro(textField.getText());
                        }
                    }
                }
                if (!letter.isEmpty() && parameterCounter == 4){ // If the command has all parameters
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("letter", letter);
                    command.put("count", count);
                    command.put("delay", delay);
                    commands.put(String.valueOf(commands.size()), command);
                } else
                if (!count.isEmpty() && !delay.isEmpty() && parameterCounter == 3) { // If the command has count and delay
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("count", count);
                    command.put("delay", delay);
                    commands.put(String.valueOf(commands.size()), command);
                } else if (!delay.isEmpty() && parameterCounter == 2){ // If the command has only delay
                    HashMap<String, String> command = new HashMap<>();
                    command.put("name", name);
                    command.put("delay", delay);
                    commands.put(String.valueOf(commands.size()), command);
                }
            }
        }
        return commands;
    }
}
