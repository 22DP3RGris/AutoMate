package org.openjfx;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

    public static void init(){ // Create the Macros directory if it doesn't exist
        File dir = new File("Macros");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    // Update the macros JSON file with the new macros
    public static void updateMacros(HashMap<String, HashMap<String, HashMap<String, String>>> macros) {

        // Create the object mapper and get the file
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        try {
            // Write the macros to the file
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, macros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeMacro(String macroName, HashMap<String, HashMap<String, String>> commands) {

        // Create the object mapper and get the file
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        // Create a new map to store the macros
        HashMap<String, HashMap<String, HashMap<String, String>>> macros = readMacrosFromJson();

        // Add the new macro to the macros map
        macros.put(macroName, commands);

        // Write the macros map back to the file
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, macros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, HashMap<String, HashMap<String, String>>> readMacrosFromJson(){

        // Create the object mapper and get the file
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // If the file already exists, read it into the macros map
        if (file.exists()) {
            try {
                // Read the macros from the file
                macros = mapper.readValue(file, new TypeReference<>() {}); // Read the macros from the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return macros;
    }

    public static void removeMacro(String macroName){

        // Create the object mapper and get the file
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = readMacrosFromJson();

        // Remove the macro from the macros map
        macros.remove(macroName);

        // Write the macros map back to the file
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, macros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}