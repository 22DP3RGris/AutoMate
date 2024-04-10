package org.openjfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Sorter {

    // Sorts the users by username
    public static void sort(ArrayList<User> users, boolean inverse) {
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
}
