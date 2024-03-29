package org.openjfx;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

    public static void init(){
        File dir = new File("Macros");
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void updateMacros(HashMap<String, HashMap<String, HashMap<String, String>>> macros) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, macros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeMacro(String macroName, HashMap<String, HashMap<String, String>> commands) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // If the file already exists, read it into the macros map
        if (file.exists()) {
            try {
                macros = mapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // If the file already exists, read it into the macros map
        if (file.exists()) {
            try {
                macros = mapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return macros;
    }

    public static void removeMacro(String macroName){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Macros/" + CurrentUser.getUsername() + ".json");

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // If the file already exists, read it into the macros map
        if (file.exists()) {
            try {
                macros = mapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
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