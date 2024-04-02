package org.openjfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;

import javafx.scene.input.KeyCode;

public class MacroFunctionality {

    // Robot object to control the mouse and keyboard
    private static Robot robot;

    // Initialize the robot object
    public static void init() throws AWTException{
        robot = new Robot();
    }

    // Run the macro commands
    public static void runMacro(HashMap<String, HashMap<String, String>> commands){

        // If the commands are empty, return nothing
        if (commands.isEmpty()) return;

        // Wait for 4 seconds
        sleep(4000);

        // Loop through the commands
        for (int i = 0; i < commands.size(); i++){
            HashMap<String, String> command = commands.get(String.valueOf(i)); // Get the current command

            // Check the command name
            switch (command.get("name")) {
                case "LC":
                    // Complete the left click command
                    for(int j = 0; j < Integer.parseInt(command.get("count")); j++){
                        leftMouseClick();
                        sleep(Integer.parseInt(command.get("delay")));
                    }
                    break;
                case "RC":
                    // Complete the right click command
                    for (int j = 0; j < Integer.parseInt(command.get("count")); j++) {
                        rightMouseClick();
                        sleep(Integer.parseInt(command.get("delay")));
                    }
                    break;
                case "W":
                    // Wait for the specified duration
                    sleep(Integer.parseInt(command.get("delay")));
                    break;
                case "P":
                    // Complete the key press command
                    if (command.get("letter").isEmpty()) break;
                    for (int j = 0; j < Integer.parseInt(command.get("count")); j++) {
                        keyPress(KeyCode.valueOf(command.get("letter").toUpperCase()));
                        int delay = Math.max(Integer.parseInt(command.get("delay")), 100);
                        sleep(delay);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // Wait for the specified duration
    public static void sleep(int duration){
        robot.delay(duration);
    }

    // Perform a left mouse click
    private static void leftMouseClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    // Perform a right mouse click
    private static void rightMouseClick(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    // Perform a key press
    private static void keyPress(KeyCode keyCode){
        robot.keyPress(keyCode.getCode());
        robot.keyRelease(keyCode.getCode());
    }
}
