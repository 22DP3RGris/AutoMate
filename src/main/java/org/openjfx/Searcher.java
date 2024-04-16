package org.openjfx;

import java.util.ArrayList;

public class Searcher {

    public static ArrayList<User> searchFriendByStr(ArrayList<User> friends, String str){
        // Create a list to store the search results
        ArrayList<User> searchResults = new ArrayList<>();
        // Search for the users that match the search string
        for (User user : friends) {
            if (user.getUsername().toLowerCase().startsWith(str.toLowerCase())) {
                searchResults.add(user);
            }
        }
        return searchResults;
    }

    public static ArrayList<String> searchMacroByStr(ArrayList<String> macros, String str){
        // Create a list to store the search results
        ArrayList<String> searchResults = new ArrayList<>();
        // Search for the macros that match the search string
        for (String macro : macros) {
            if (macro.toLowerCase().startsWith(str.toLowerCase())) {
                searchResults.add(macro);
            }
        }
        return searchResults;
    }
}
