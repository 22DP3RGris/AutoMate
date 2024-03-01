package org.openjfx;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

import org.junit.Test;

public class MacroFunctionalityTest {

    @Test
    public void testInitialize(){
        try {
            MacroFunctionality.initialize();

            assertTrue(true);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSleep(){
        try {
            MacroFunctionality.sleep(1000);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLeftMouseClick(){
        try {
            MacroFunctionality.leftMouseClick();
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
