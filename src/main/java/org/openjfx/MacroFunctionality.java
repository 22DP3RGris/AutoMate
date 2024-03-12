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

    public static void runMacro(HashMap<String, String> commands){
        for (int i = 5; i > 0; i--) {
            System.out.println(i);
            sleep(1000);
        }
        for (String key : commands.keySet()) {
            switch (key) {
                case "LC":
                    for(int i = 0; i < Integer.parseInt(commands.get(key)); i++){
                        leftMouseClick();
                        sleep(1);
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

    public static void leftMouseClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
