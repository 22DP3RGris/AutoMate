package org.openjfx;

public class Validator {

    private final static String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final static String PASSWORD_REGEX = "^.{6,20}$";

    public static boolean validateUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    public static boolean validateEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_REGEX);
    }
}
