package org.openjfx;

public class KeyCodeReverse {

    // Convert a key code to a macro key code
    public static String reverseKeyCodeToMacro(String keyCode) {
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

    // Convert a key code to a user key code
    public static String reverseKeyCodeToUser(String KeyCode){
        switch (KeyCode){
            case "BACK_SPACE":
                return "Backspace";
            case "ESCAPE":
                return "Esc";
            case "CAPS":
                return "Caps Lock";
            case "NUM_LOCK":
                return "Num Lock";
            case "CONTEXT_MENU":
                return "Context Menu";
            case "BACK_SLASH":
                return "Back Slash";
            case "SCROLL_LOCK":
                return "Scroll Lock";
            case "PRINTSCREEN":
                return "Print Screen";
            case "CLOSE_BRACKET":
                return "Close Bracket";
            case "OPEN_BRACKET":
                return "Open Bracket";
            case "BACK_QUOTE":
                return "Back Quote";
            case "ALT_GRAPH":
                return "Alt Graph";
            case "CONTROL":
                return "Ctrl";
            case "PAGE_DOWN":
                return "Page Down";
            case "PAGE_UP":
                return "Page Up";
            case "DIGIT0":
                return "0";
            case "DIGIT1":
                return "1";
            case "DIGIT2":
                return "2";
            case "DIGIT3":
                return "3";
            case "DIGIT4":
                return "4";
            case "DIGIT5":
                return "5";
            case "DIGIT6":
                return "6";
            case "DIGIT7":
                return "7";
            case "DIGIT8":
                return "8";
            case "DIGIT9":
                return "9";
            case "NUMPAD0":
                return "Numpad 0";
            case "NUMPAD1":
                return "Numpad 1";
            case "NUMPAD2":
                return "Numpad 2";
            case "NUMPAD3":
                return "Numpad 3";
            case "NUMPAD4":
                return "Numpad 4";
            case "NUMPAD5":
                return "Numpad 5";
            case "NUMPAD6":
                return "Numpad 6";
            case "NUMPAD7":
                return "Numpad 7";
            case "NUMPAD8":
                return "Numpad 8";
            case "NUMPAD9":
                return "Numpad 9";
            case "":
                return "";
            default:
                return KeyCode.toLowerCase().substring(0, 1).toUpperCase() + KeyCode.toLowerCase().substring(1);
        }
    }
}
