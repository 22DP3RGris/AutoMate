package org.openjfx;

import java.util.HashMap;

public class User {

    private static String username;
    private static String email;
    private static String password;

    public static void clear(){
        MacroElements.setMacro(new HashMap<>());
        username = null;
        email = null;
        password = null;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String display(){
        return "Username: " + username + " Email: " + email + " Password: " + password;
    }
}
