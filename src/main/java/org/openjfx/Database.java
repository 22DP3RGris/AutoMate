package org.openjfx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

public class Database{

    private static boolean userExist;

    public static void init() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("Firebase/key.json");
        FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app")
        .build();

        FirebaseApp.initializeApp(options);
    }

    public static boolean checkUser(String username, String password) {
        if (username.equals("") || password.equals("")) return false;
        userExist = false;

        DatabaseReference ref = FirebaseDatabase.getInstance("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users/" + username);

        final Semaphore semaphore = new Semaphore(0);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("password").getValue().equals(password)) {
                        userExist = true;
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
        return userExist;
    }
}
