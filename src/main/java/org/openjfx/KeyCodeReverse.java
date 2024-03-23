package org.openjfx;

public class KeyCodeReverse {
    public static String reverseKeyCode(String keyCode) {
        switch (keyCode) {
            case "Backspace":
                return "BACK_SPACE";
            case "Esc":
                return "ESCAPE";
            case "Caps Lock":
                return "CAPS";
            case "Num Lock":
                return "NUM_LOCK";
            case "Context Menu":
                return "CONTEXT_MENU";
            case "Back Slash":
                return "BACK_SLASH";
            case "Scroll Lock":
                return "SCROLL_LOCK";
            case "Print Screen":
                return "PRINTSCREEN";
            case "Close Bracket":
                return "CLOSE_BRACKET";
            case "Open Bracket":
                return "OPEN_BRACKET";
            case "Back Quote":
                return "BACK_QUOTE";
            case "Alt Graph":
                return "ALT_GRAPH";
            case "Ctrl":
                return "CONTROL";
            case "Page Down":
                return "PAGE_DOWN";
            case "Page Up":
                return "PAGE_UP";
            case "0":
                return "DIGIT0";
            case "1":
                return "DIGIT1";
            case "2":
                return "DIGIT2";
            case "3":
                return "DIGIT3";
            case "4":
                return "DIGIT4";
            case "5":
                return "DIGIT5";
            case "6":
                return "DIGIT6";
            case "7":
                return "DIGIT7";
            case "8":
                return "DIGIT8";
            case "9":
                return "DIGIT9";
            case "Numpad 0":
                return "NUMPAD0";
            case "Numpad 1":
                return "NUMPAD1";
            case "Numpad 2":
                return "NUMPAD2";
            case "Numpad 3":
                return "NUMPAD3";
            case "Numpad 4":
                return "NUMPAD4";
            case "Numpad 5":
                return "NUMPAD5";
            case "Numpad 6":
                return "NUMPAD6";
            case "Numpad 7":
                return "NUMPAD7";
            case "Numpad 8":
                return "NUMPAD8";
            case "Numpad 9":
                return "NUMPAD9";
            default:
                return keyCode; 
        }
    }
}
