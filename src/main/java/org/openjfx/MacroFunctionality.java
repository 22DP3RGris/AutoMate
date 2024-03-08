package org.openjfx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class MacroFunctionality {

    private static Robot robot;

    public static void initialize() throws AWTException{
        robot = new Robot();
    }

    public static void sleep(int duration){
        robot.delay(duration);
    }

    public static void leftMouseClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    // double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
    // double mouseY = MouseInfo.getPointerInfo().getLocation().getY();



}
