package org.openjfx;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FriendsPageController {

    // Scene elements
    @FXML
    private VBox friendsList;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private ToggleGroup sort;

    @FXML // Initialize the scene
    private void initialize(){

        // Set the width of the friends list to the width of the scroll pane
        friendsList.prefWidthProperty().bind(mainScroll.widthProperty());

        // Update the friend boxes
        updateFriendBoxes();
    }

    @FXML // Update the friend boxes
    private void updateFriendBoxes(){

        // Get the friends from the database
        ArrayList<User> friends = Database.getFriendList();

        // Sort the friends based on the selected toggle
        if (((ToggleButton) sort.getSelectedToggle()).getId().equals("aToZ")){
            Sorter.sort(friends, false);
        }
        else if (((ToggleButton) sort.getSelectedToggle()).getId().equals("zToA")){
            Sorter.sort(friends, true);
        }

        // Remove the current friend boxes
        while (friendsList.getChildren().size() > 2) { // Keep the buttons, filters and search bar
            friendsList.getChildren().remove(friendsList.getChildren().size() - 1);
        }

        // Create the new friend boxes
        for (User friend : friends){ // For each friend

            // Create the friend box
            HBox friendBox = new HBox();
            friendBox.setAlignment(Pos.CENTER);
            friendBox.setPrefWidth(800);
            friendBox.setPrefHeight(75);
            VBox.setMargin(friendBox, new Insets(30, 60, 0, 0));

            // Create the delete button
            Button deleteBtn = new Button("X");
            deleteBtn.getStyleClass().add("element-delete");
            deleteBtn.setPrefWidth(30);
            deleteBtn.setPrefHeight(30);
            HBox.setMargin(deleteBtn, new Insets(0, 20, 0, 0));
            deleteBtn.cursorProperty().set(Cursor.HAND);
            deleteBtn.setOnAction(event -> {
                friendsList.getChildren().remove(friendBox);
                Database.removeFriend(friend.getUsername());
            });
            friendBox.getChildren().add(deleteBtn);

            // Create the username label
            Label username = new Label(friend.getUsername());
            username.setPrefHeight(75);
            username.setAlignment(Pos.CENTER);
            username.setPrefWidth(600);
            username.getStyleClass().add("placeholder");
            username.getStyleClass().add("element-label");
            friendBox.getChildren().add(username);

            friendsList.getChildren().add(friendBox);
        }
    }

    @FXML // Open the incoming friend requests dialog
    private void openRequests() throws IOException{

        // Create the dialog stage
        Stage dialogStage = App.createDialogStage("IncomingRequests");

        // Get the scroll pane
        ScrollPane scroll = (ScrollPane) dialogStage.getScene().lookup("#scroll");

        // Get the requests vbox
        VBox requests = (VBox) scroll.getContent();

        // Set the width of the requests vbox to the width of the scroll pane
        requests.prefWidthProperty().bind(scroll.widthProperty());

        // Get the incoming friend requests
        ArrayList<User> users = Database.getIncomingFriendRequests();

        for (User user : users){ // For each incoming friend request

            // Create the request box
            HBox request = new HBox();
            request.setAlignment(Pos.CENTER);
            request.setPrefWidth(300);
            request.setPrefHeight(50);
            VBox.setMargin(request, new Insets(20, 20, 10, 0));

            // Create the decline button
            Button declineBtn = new Button("X");
            declineBtn.getStyleClass().add("element-delete");
            declineBtn.setPrefWidth(30);
            declineBtn.setPrefHeight(30);
            HBox.setMargin(declineBtn, new Insets(0, 10, 0, 0));
            declineBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
            declineBtn.setOnAction(event -> { // Decline the friend request
                requests.getChildren().remove(request);
                Database.declineFriendRequest(user.getUsername());
            });
            request.getChildren().add(declineBtn);

            // Create the accept button
            Button acceptBtn = new Button("✔");
            acceptBtn.getStyleClass().add("element-delete");
            acceptBtn.setPrefWidth(30);
            acceptBtn.setPrefHeight(30);
            HBox.setMargin(acceptBtn, new Insets(0, 20, 0, 0));
            acceptBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
            acceptBtn.setOnAction(event -> { // Accept the friend request
                requests.getChildren().remove(request);
                Database.declineFriendRequest(user.getUsername());
                Database.addFriend(user.getUsername());
                updateFriendBoxes();
            });
            request.getChildren().add(acceptBtn);

            // Create the username label
            Label username = new Label(user.getUsername());
            username.setPrefHeight(50);
            username.setAlignment(Pos.CENTER);
            username.setPrefWidth(300);
            username.getStyleClass().add("friend-label");
            request.getChildren().add(username);

            requests.getChildren().add(request);

        }
        // Show the dialog stage
        dialogStage.showAndWait();
    }

    @FXML // Send a friend request
    private void sendRequest() throws IOException{

        // Create the dialog stage
        Stage dialogStage = App.createDialogStage("SendRequest");

        // Get the scene elements
        TextField friendsName = (TextField) dialogStage.getScene().lookup("#friendsName");
        Text friendsNameError = (Text) dialogStage.getScene().lookup("#friendsNameError");
        Button sendBtn = (Button) dialogStage.getScene().lookup("#sendBtn");
        AnchorPane window = (AnchorPane) dialogStage.getScene().lookup("#window");

        // If the enter key is pressed, send the request
        window.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendBtn.fire();
            }
        });

        // Send the friend request
        sendBtn.setOnAction(event ->{

            friendsNameError.setVisible(false);

            // Check if the username is valid
            if (friendsName.getText().isEmpty()){
                // If the username is empty
                friendsNameError.setText("Username cannot be empty.");
                friendsNameError.setVisible(true);
            }
            else if (!Database.usernameExist(friendsName.getText())){
                // If the username does not exist
                friendsNameError.setText("User does not exist.");
                friendsNameError.setVisible(true);
            }
            else if (CurrentUser.getUsername().equals(friendsName.getText())){
                // If the username is the same as the current user
                friendsNameError.setText("Cannot send request to yourself.");
                friendsNameError.setVisible(true);
            }
            else if (Database.friendRequestExist(CurrentUser.getUsername(), friendsName.getText())){
                // If the friend request already exists
                friendsNameError.setText("Friend request already sent.");
                friendsNameError.setVisible(true);
            }
            else if (Database.friendRequestExist(friendsName.getText(), CurrentUser.getUsername())){
                // If the friend request already exists
                Database.declineFriendRequest(friendsName.getText());
                Database.addFriend(friendsName.getText());
                updateFriendBoxes();
                dialogStage.close();
            }
            else if (Database.friendExists(friendsName.getText())){
                // If the user is already a friend
                friendsNameError.setText("User is already your friend.");
                friendsNameError.setVisible(true);
            } 
            else{
                // Send the friend request
                Database.setFriendRequest(CurrentUser.getUsername(), friendsName.getText());
                dialogStage.close();
            }
        });

        // Show the dialog stage
        dialogStage.showAndWait();
    }

    @FXML // Open the sent requests dialog
    private void sentRequests() throws IOException{

        // Create the dialog stage
        Stage dialogStage = App.createDialogStage("SentRequests");

        // Get the scroll pane
        ScrollPane scroll = (ScrollPane) dialogStage.getScene().lookup("#scroll");

        // Get the requests vbox
        VBox requests = (VBox) scroll.getContent();

        // Set the width of the requests vbox to the width of the scroll pane
        requests.prefWidthProperty().bind(scroll.widthProperty());

        // Get the sent friend requests
        ArrayList<User> users = Database.getFriendRequestList();

        for (User user : users){ // For each sent friend request

            // Create the request box
            HBox request = new HBox();
            request.setAlignment(Pos.CENTER);
            request.setPrefWidth(300);
            request.setPrefHeight(50);
            VBox.setMargin(request, new Insets(20, 20, 10, 0));

            // Create the cancel button
            Button cancelBtn = new Button("X");
            cancelBtn.getStyleClass().add("element-delete");
            cancelBtn.setPrefWidth(30);
            cancelBtn.setPrefHeight(30);
            HBox.setMargin(cancelBtn, new Insets(0, 10, 0, 0));
            cancelBtn.cursorProperty().set(Cursor.HAND);
            cancelBtn.setOnAction(event -> {
                requests.getChildren().remove(request);
                Database.removeSentRequest(user.getUsername());
            });
            request.getChildren().add(cancelBtn);

            // Create the username label
            Label username = new Label(user.getUsername());
            username.setPrefHeight(50);
            username.setAlignment(Pos.CENTER);
            username.setPrefWidth(300);
            username.getStyleClass().add("friend-label");
            request.getChildren().add(username);

            requests.getChildren().add(request);
        }

        // Show the dialog stage
        dialogStage.showAndWait();
    }
}
