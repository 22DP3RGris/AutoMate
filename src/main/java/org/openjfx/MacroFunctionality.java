package org.openjfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
            for (String key : command.keySet()) {
                switch (key) {
                    case "LC":
                        for(int j = 0; j < Integer.parseInt(command.get(key)); j++){
                            leftMouseClick();
                            sleep(1);
                        }
                        break;
                    default:
                        break;
                }
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
}
