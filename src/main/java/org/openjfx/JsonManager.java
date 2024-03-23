package org.openjfx;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {
    
    public static void writeMacroToJson(String macroName, HashMap<String, HashMap<String, String>> commands) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + User.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // If the file already exists, read it into the macros map
        try {
            macros = mapper.readValue(file, new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + User.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

    
        try {
            macros = mapper.readValue(file, new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return macros;
    }

    public static void removeMacroFromJson(String macroName){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + User.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        try {
            macros = mapper.readValue(file, new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

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