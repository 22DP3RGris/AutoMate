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

    // Flag to check if something exists
    private static boolean exist;

    // Firebase database reference
    private static DatabaseReference baseRef;

    public static void init() throws IOException{ // Initialize the database connection
        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        FileInputStream serviceAccount = new FileInputStream("Firebase/key.json");
        FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app")
        .build();
        FirebaseApp.initializeApp(options);
        baseRef = FirebaseDatabase.getInstance("https://automate-a6cf3-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
    }

    public static boolean loginUser(String username, String password) { // Login the user
        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        exist = false;

        // Get the user's information
        DatabaseReference ref = baseRef.child(username);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the user exists and the password is correct
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("password").getValue().equals(password)) {
                        // Set the current user's information
                        CurrentUser.setUsername(username);
                        CurrentUser.setEmail(dataSnapshot.child("email").getValue().toString());
                        CurrentUser.setPassword(password);
                        exist = true;
                    }
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        }); 

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public static boolean usernameExist(String username) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // If the username is empty return false
        if (username.isEmpty()) return false;
        exist = false;

        // Get the user's information
        DatabaseReference ref = baseRef.child(username);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the user exists
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static boolean emailExist(String email) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // If the email is empty return false
        if (email.isEmpty()) return false;
        exist = false;

        // Get the user's information
        DatabaseReference ref = baseRef;

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    // If the email exists
                    if (child.child("email").getValue().equals(email)) {
                        exist = true;
                        break;
                    }
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static void registerUser(String username, String password, String email) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Get the user's information
        DatabaseReference ref = baseRef.child(username);

        // Set the user's information
        ref.child("email").setValueAsync(email);
        ref.child("password").setValueAsync(password);
    }

    public static void saveMacro(String macroName, HashMap<String, HashMap<String, String>> commands) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the user's macros path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        // Set the macro's commands
        ref.setValueAsync(commands);
    }

    public static void deleteMacro(String macroName) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the user's macros path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        // Remove the macro
        ref.removeValueAsync();
    }

    public static boolean macroExist(String macroName) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // If the macro name is empty return false
        if (macroName.isEmpty()) return false;
        exist = false;

        // Get the user's macro
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the macro exists
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static HashMap<String, HashMap<String, String>> getMacro(String friendName, String macroName){

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new hashmap to store the macro
        HashMap<String, HashMap<String, String>> macro = new HashMap<>();

        // Get the user's macro
        DatabaseReference ref = baseRef.child(friendName).child("macros").child(macroName);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // For each command
                for (DataSnapshot command : dataSnapshot.getChildren()) {
                    HashMap<String, String> commandMap = new HashMap<>();
                    // For each parameter
                    for (DataSnapshot parameter : command.getChildren()) {
                        commandMap.put(parameter.getKey(), parameter.getValue().toString());
                    }
                    macro.put(command.getKey(), commandMap);
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return macro;
    }

    public static HashMap<String, HashMap<String, HashMap<String, String>>> getMacros() {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new hashmap to store the macros
        HashMap<String, HashMap<String, HashMap<String, String>>> macros = new HashMap<>();

        // Get the user's macros
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros");

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // For each macro
                for (DataSnapshot MacroName : dataSnapshot.getChildren()) {
                    HashMap<String, HashMap<String, String>> commands = new HashMap<>();
                    // For each command
                    for (DataSnapshot command : MacroName.getChildren()) {
                        HashMap<String, String> commandMap = new HashMap<>();
                        // For each parameter
                        for (DataSnapshot parameter : command.getChildren()) {
                            commandMap.put(parameter.getKey(), parameter.getValue().toString());
                        }
                        commands.put(command.getKey(), commandMap);
                    }
                    macros.put(MacroName.getKey(), commands);
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return macros;
    }

    public static void setFriendRequest(String fromUser, String toUser) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the current user's friend requests path
        DatabaseReference ref = baseRef.child(toUser).child("friendRequests").child(fromUser);

        // Set the friend request
        ref.setValueAsync(fromUser);

        // Open the other user's sent friend requests path
        ref = baseRef.child(fromUser).child("sentFriendRequests").child(toUser);

        // Set the sent friend request
        ref.setValueAsync(toUser);
    }

    public static boolean friendRequestExist(String fromUser, String toUser) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // If the usernames are empty return false
        if (fromUser.isEmpty() || toUser.isEmpty()) return false;
        exist = false;

        // Get the friend request path
        DatabaseReference ref = baseRef.child(toUser).child("friendRequests").child(fromUser);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the friend request exists
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static ArrayList<User> getIncomingFriendRequests() {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new arraylist to store the incoming friend requests
        ArrayList<User> requests = new ArrayList<>();

        // Get the current user's friend requests
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friendRequests");

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // For each friend request
                for (DataSnapshot request : dataSnapshot.getChildren()) {
                    User user = new User(request.getKey());
                    requests.add(user);
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public static void declineFriendRequest(String username) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the current user's friend requests path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friendRequests").child(username);

        // Remove the friend request
        ref.removeValueAsync();

        // Open the other user's sent friend requests path
        ref = baseRef.child(username).child("sentFriendRequests").child(CurrentUser.getUsername());

        // Remove the sent friend request
        ref.removeValueAsync();
    }

    public static void addFriend(String username) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the current user's friends path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        // Set the friend
        ref.setValueAsync(username);

        // Open the other user's friends path
        ref = baseRef.child(username).child("friends").child(CurrentUser.getUsername());

        // Set the friend
        ref.setValueAsync(CurrentUser.getUsername());

        // Remove the friend's friend request
        ref = baseRef.child(username).child("sentFriendRequests").child(CurrentUser.getUsername());

        // Remove the sent friend request
        ref.removeValueAsync();
    }

    public static ArrayList<User> getFriendList() {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new arraylist to store the friends
        ArrayList<User> friends = new ArrayList<>();

        // Get the current user's friends
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends");

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot friend : dataSnapshot.getChildren()) {
                    User user = new User(friend.getKey());
                    friends.add(user);
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return friends;
    }

    public static void removeFriend(String username) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the current user's friends path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        // Remove the friend
        ref.removeValueAsync();

        // Open the other user's friends path
        ref = baseRef.child(username).child("friends").child(CurrentUser.getUsername());

        // Remove the friend
        ref.removeValueAsync();
    }

    public static boolean friendExists(String username) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // If the username is empty return false
        if (username.isEmpty()) return false;
        exist = false;

        // Get the current user's friends
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("friends").child(username);

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the friend exists
                if (dataSnapshot.exists()) {
                    exist = true;
                }
                // Release the semaphore
                semaphore.release();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });

        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static ArrayList<User> getFriendRequestList(){

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new arraylist to store the friend requests
        ArrayList<User> requests = new ArrayList<>();

        // Get the current user's friend requests
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("sentFriendRequests");

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot request : dataSnapshot.getChildren()) {
                    User user = new User(request.getKey());
                    requests.add(user);
                }
                // Release the semaphore
                semaphore.release();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });
        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static void removeSentRequest(String username){

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the current user's sent friend requests path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("sentFriendRequests").child(username);

        // Remove the sent friend request
        ref.removeValueAsync();

        // Open the other user's friend requests path
        ref = baseRef.child(username).child("friendRequests").child(CurrentUser.getUsername());

        // Remove the friend request
        ref.removeValueAsync();
    }

    // Sent macro share request
    public static void sentMacroShareRequest(String friend, String macroName) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the friend's shared macros path
        DatabaseReference ref = baseRef.child(friend).child("incomingMacros").child(CurrentUser.getUsername()).child(macroName);

        // Set the shared macro
        ref.setValueAsync(macroName);
    }

    public static HashMap<String, HashMap<String, String>> getIncomingMacros(){

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Create a new hashmap to store the incoming macros
        HashMap<String, HashMap<String, String>> incomingMacros = new HashMap<>();

        // Get the current user's incoming macros
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("incomingMacros");

        // Wait for the database response
        final Semaphore semaphore = new Semaphore(0);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot friend : dataSnapshot.getChildren()) {
                    HashMap<String, String> macroNames = new HashMap<>();
                    for (DataSnapshot macroName : friend.getChildren()) {
                        macroNames.put(macroName.getKey(), macroName.getValue().toString());
                    }
                    incomingMacros.put(friend.getKey(), macroNames);
                }
                // Release the semaphore
                semaphore.release();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                semaphore.release();
            }
        });
        try {
            // Wait for the database response (semaphore release)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return incomingMacros;
    }

    public static void removeIncomingMacro(String friend, String macroName) {

        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the friend's shared macros path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("incomingMacros").child(friend).child(macroName);

        // Remove the shared macro
        ref.removeValueAsync();
    }

    public static void acceptIncomingMacro(String friend, String macroName, HashMap<String, HashMap<String, String>> macro) {
        // Wait for internet connection
        InternetConnectionChecker.waitForInternet();

        // Open the macros path
        DatabaseReference ref = baseRef.child(CurrentUser.getUsername()).child("macros").child(macroName);

        // Set the shared macro
        ref.setValueAsync(macro);
    }
}
