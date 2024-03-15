package org.openjfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MacroFunctionality {

    private static Robot robot;

    public static void initialize() throws AWTException{
        robot = new Robot();
    }

    public static void runMacro(HashMap<Integer, HashMap<String, String>> commands){
        if (commands.isEmpty()) {
            return;
        }
        for (int i = 5; i > 0; i--){
            sleep(1000);
        }
        for (int i : commands.keySet()) {
            HashMap<String, String> command = commands.get(i);
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
                case "W":
                    sleep(Integer.parseInt(command.get("delay")));
                    break;
                case "P":
                    for (int j = 0; j < Integer.parseInt(command.get("count")); j++) {
                        keyPress(command.get("letter"));
                        sleep(Integer.parseInt(command.get("delay")));
                    }
                default:
                    break;
            }
        }
    }

    public static void sleep(int duration){
        robot.delay(duration);
    }

    public static void leftMouseClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void rightMouseClick(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void keyPress(String key){
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
