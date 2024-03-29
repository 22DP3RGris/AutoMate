package org.openjfx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("password").getValue().equals(password)) {
                        CurrentUser.setUsername(username);
                        CurrentUser.setEmail(dataSnapshot.child("email").getValue().toString());
                        CurrentUser.setPassword(password);
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

        if (username.isEmpty()) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(username);

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

        if (email.isEmpty()) return false;
        exist = false;

        DatabaseReference ref = baseRef;

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static void registerUser(String username, String password, String email) {
   
        DatabaseReference ref = baseRef.child(username);

        ref.child("email").setValueAsync(email);
        ref.child("password").setValueAsync(password);
    }

    public static void saveMacro(String macroName, HashMap<String, HashMap<String, String>> commands) {
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        ref.setValueAsync(commands);
    }

    public static void deleteMacro(String macroName) {
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        ref.removeValueAsync();
    }

    public static boolean macroExist(String macroName) {

        if (macroName.isEmpty()) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static HashMap<String, HashMap<String, HashMap<String, String>>> getMacros() {

        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros");

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot MacroName : dataSnapshot.getChildren()) {
                    HashMap<String, HashMap<String, String>> commands = new HashMap<>();
                    for (DataSnapshot command : MacroName.getChildren()) {
                        HashMap<String, String> commandMap = new HashMap<>();
                        for (DataSnapshot parameter : command.getChildren()) {
                            commandMap.put(parameter.getKey(), parameter.getValue().toString());
                        }
                        commands.put(command.getKey(), commandMap);
                    }
                    macros.put(MacroName.getKey(), commands);
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

        return macros;
    }

    public static void setFriendRequest(String fromUser, String toUser) {
        DatabaseReference ref = baseRef.child(toUser).child("friendRequests").child(fromUser);

        ref.setValueAsync(fromUser);
    }

    public static boolean friendRequestExist(String fromUser, String toUser) {

        if (fromUser.isEmpty() || toUser.isEmpty()) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(toUser).child("friendRequests").child(fromUser);

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

    public static ArrayList<User> getIncomingFriendRequests() {

        ArrayList<User> requests = new ArrayList<>();

        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friendRequests");

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot request : dataSnapshot.getChildren()) {
                    User user = new User(request.getKey());
                    requests.add(user);
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

        return requests;
    }

    public static void removeFriendRequest(String fromUser) {
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friendRequests").child(fromUser);

        ref.removeValueAsync();
    }

    public static void addFriend(String username) {
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        ref.setValueAsync(username);

        ref = baseRef.child(username).child("friends").child(CurrentUser.getUsername());

        ref.setValueAsync(CurrentUser.getUsername());
    }

    public static ArrayList<User> getFriendList() {

        ArrayList<User> friends = new ArrayList<>();

        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends");

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot friend : dataSnapshot.getChildren()) {
                    User user = new User(friend.getKey());
                    friends.add(user);
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

        return friends;
    }

    public static void removeFriend(String username) {
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        ref.removeValueAsync();

        ref = baseRef.child(username).child("friends").child(CurrentUser.getUsername());

        ref.removeValueAsync();
    }

    public static boolean friendExists(String username) {
        
        if (username.isEmpty()) return false;
        exist = false;

        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
