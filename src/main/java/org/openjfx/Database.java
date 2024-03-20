package org.openjfx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

public class Database{

    private static boolean exist;
    private static DatabaseReference baseRef;

    public static void init() throws IOException{
        
        FileInputStream serviceAccount = new FileInputStream("Firebase/key.json");
        FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app")
        .build();
        FirebaseApp.initializeApp(options);
        baseRef = FirebaseDatabase.getInstance("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
    }

    public static boolean loginUser(String username, String password) {

        exist = false;

        DatabaseReference ref = baseRef.child(username);

        final Semaphore semaphore = new Semaphore(0);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("password").getValue().equals(password)) {
                        User.setUsername(username);
                        User.setEmail(dataSnapshot.child("email").getValue().toString());
                        User.setPassword(password);
                        exist = true;
                    }
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        }); 

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public static boolean usernameExist(String username) {

        if (username.equals("")) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(username);

        final Semaphore semaphore = new Semaphore(0);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static boolean emailExist(String email) {

        if (email.equals("")) return false;
        exist = false;

        DatabaseReference ref = baseRef;

        final Semaphore semaphore = new Semaphore(0);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("email").getValue().equals(email)) {
                        exist = true;
                        break;
                    }
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static boolean registerUser(String username, String password, String email) {
   
        DatabaseReference ref = baseRef.child(username);

        ref.child("email").setValueAsync(email);
        ref.child("password").setValueAsync(password);

        return true;
    }

    public static void saveMacro(String macroName, HashMap<String, HashMap<String, String>> commands) {
        DatabaseReference ref = baseRef.child(User.getUsername()).child("macros").child(macroName);

        ref.setValueAsync(commands);
    }

    public static boolean macroExist(String macroName) {

        if (macroName.equals("")) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(User.getUsername()).child("macros").child(macroName);

        final Semaphore semaphore = new Semaphore(0);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }
}
