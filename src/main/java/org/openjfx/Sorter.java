package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Sorter {

    // Sorts the users by username
    public static void sortUserName(ArrayList<User> users, boolean inverse) {
        // Bubble sort
        for (int i = 0; i < users.size(); i++){
            for (int j = 0; j < users.size() - 1; j++) {
                // If not inverse sort
                if (!inverse) {
                    // If the username is greater than the next username
                    if (users.get(j).getUsername().toLowerCase().compareTo(users.get(j + 1).getUsername().toLowerCase()) > 0) {
                        // Swap the users
                        User temp = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set(j + 1, temp);
                    }
                } else {
                    // If the username is less than the next username
                    if (users.get(j).getUsername().toLowerCase().compareTo(users.get(j + 1).getUsername().toLowerCase()) < 0) {
                        // Swap the users
                        User temp = users.get(j);
                        users.set(j, users.get(j + 1));
                        users.set(j + 1, temp);
                    }
                }
            }
        }
    }

    // Sorts the macros by name
    public static void sortMacroName(ArrayList<String> macroNames, boolean inverse){
        // Bubble sort
        for (int i = 0; i < macroNames.size(); i++){
            for (int j = 0; j < macroNames.size() - 1; j++) {
                // If not inverse sort
                if (!inverse) {
                    // If the macro name is greater than the next macro name
                    if (macroNames.get(j).toLowerCase().compareTo(macroNames.get(j + 1).toLowerCase()) > 0) {
                        // Swap the macro names
                        String temp = macroNames.get(j);
                        macroNames.set(j, macroNames.get(j + 1));
                        macroNames.set(j + 1, temp);
                    }
                } else {
                    // If the macro name is less than the next macro name
                    if (macroNames.get(j).toLowerCase().compareTo(macroNames.get(j + 1).toLowerCase()) < 0) {
                        // Swap the macro names
                        String temp = macroNames.get(j);
                        macroNames.set(j, macroNames.get(j + 1));
                        macroNames.set(j + 1, temp);
                    }
                }
            }
        }
    }
}
