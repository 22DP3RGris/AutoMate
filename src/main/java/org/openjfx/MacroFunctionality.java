package org.openjfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;

import javafx.scene.input.KeyCode;

public class MacroFunctionality {

    private static Robot robot;

    public static void init() throws AWTException{
        robot = new Robot();
    }

    public static void runMacro(HashMap<String, HashMap<String, String>> commands){
        if (commands.isEmpty()) return;
        sleep(4000);
        for (int i = 0; i < commands.size(); i++){
            HashMap<String, String> command = commands.get(String.valueOf(i));
            switch (command.get("name")) {
                case "LC":
                    for(int j = 0; j < Integer.parseInt(command.get("count")); j++){
                        leftMouseClick();
                        sleep(Integer.parseInt(command.get("delay")));
                    }
                    break;
                case "RC":
                    for (int j = 0; j < Integer.parseInt(command.get("count")); j++) {
                        rightMouseClick();
                        sleep(Integer.parseInt(command.get("delay")));
                    }
                    break;
                case "W":
                    sleep(Integer.parseInt(command.get("delay")));
                    break;
                case "P":
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

    public static void sleep(int duration){
        robot.delay(duration);
    }

    private static void leftMouseClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static void rightMouseClick(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    private static void keyPress(KeyCode keyCode){
        robot.keyPress(keyCode.getCode());
        robot.keyRelease(keyCode.getCode());
    }
}
