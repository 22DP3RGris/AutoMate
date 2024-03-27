package org.openjfx;

import java.util.HashMap;

public class CurrentUser{

    private static String username;
    private static String email;
    private static String password;

    public static void clear(){
        MacroElements.setMacro(new HashMap<>());
        username = null;
        email = null;
        username = null;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CurrentUser.password = password;
    }

    public static String UserDisplay(){
        return "Username: " + username + " Email: " + email + " Password: " + password;
    }
}
