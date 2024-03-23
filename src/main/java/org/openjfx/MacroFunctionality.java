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

    public static void runMacro(HashMap<String, HashMap<String, String>> commands) throws Exception{
        if (commands.isEmpty()) return;

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
                    for (int j = 0; j < Integer.parseInt(command.get("count")); j++) {
                        keyPress(KeyCode.valueOf(command.get("letter").toUpperCase()));
                        int delay = (Integer.parseInt(command.get("delay")) > 100) ? Integer.parseInt(command.get("delay")) : 100;
                        sleep(delay);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void sleep(int duration) throws AWTException{
        robot.delay(duration);
    }

    private static void leftMouseClick() throws AWTException{
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    private static void rightMouseClick() throws AWTException{
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    private static void keyPress(KeyCode keyCode) throws AWTException{
        robot.keyPress(keyCode.getCode());
        robot.keyRelease(keyCode.getCode());
    }
}
