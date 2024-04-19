package org.openjfx;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SorterTest {

    @Test
    public void testUserSortByName() {
        User user1 = new User("John", "Doe", "1234");
        User user2 = new User("12345", "Doe", "1234");
        User user3 = new User("Jane", "Doe", "1234");
        User user4 = new User("Alice", "Doe", "1234");
        User user5 = new User("Zack", "Doe", "1234");

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        Sorter.sortUserName(users, false);
        assertEquals(users.get(0).getUsername(), "12345");
        assertEquals(users.get(1).getUsername(), "Alice");
        assertEquals(users.get(2).getUsername(), "Jane");
        assertEquals(users.get(3).getUsername(), "John");
        assertEquals(users.get(4).getUsername(), "Zack");

        Sorter.sortUserName(users, true);
        assertEquals(users.get(0).getUsername(), "Zack");
        assertEquals(users.get(1).getUsername(), "John");
        assertEquals(users.get(2).getUsername(), "Jane");
        assertEquals(users.get(3).getUsername(), "Alice");
        assertEquals(users.get(4).getUsername(), "12345");
    }

    @Test
    public void testMacroSortByName() {
        ArrayList<String> macroNames = new ArrayList<>();
        macroNames.add("Open Notepad");
        macroNames.add("Open Paint");
        macroNames.add("Auto Clicker");
        macroNames.add("1");
        macroNames.add("Watch YouTube");

        Sorter.sortMacroName(macroNames, false);
        assertEquals(macroNames.get(0), "1");
        assertEquals(macroNames.get(1), "Auto Clicker");
        assertEquals(macroNames.get(2), "Open Notepad");
        assertEquals(macroNames.get(3), "Open Paint");
        assertEquals(macroNames.get(4), "Watch YouTube");

        Sorter.sortMacroName(macroNames, true);
        assertEquals(macroNames.get(0), "Watch YouTube");
        assertEquals(macroNames.get(1), "Open Paint");
        assertEquals(macroNames.get(2), "Open Notepad");
        assertEquals(macroNames.get(3), "Auto Clicker");
        assertEquals(macroNames.get(4), "1");
    }

}
