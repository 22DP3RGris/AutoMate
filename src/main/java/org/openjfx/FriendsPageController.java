package org.openjfx;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FriendsPageController {

    @FXML
    private VBox friendsList;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private void initialize() throws IOException{
        friendsList.prefWidthProperty().bind(mainScroll.widthProperty());
        updateFriendBoxes();
    }

    private void updateFriendBoxes(){
        ArrayList<User> friends = Database.getFriendList();
        while (friendsList.getChildren().size() > 1) {
            friendsList.getChildren().remove(friendsList.getChildren().size() - 1);
        }
        for (User friend : friends){
            HBox friendBox = new HBox();
            friendBox.setAlignment(Pos.CENTER);
            friendBox.setPrefWidth(800);
            friendBox.setPrefHeight(75);
            VBox.setMargin(friendBox, new Insets(30, 60, 0, 0));

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

    @FXML
    private void openRequests() throws IOException{

        Stage dialogStage = App.createDialogStage("IncomingRequests");

        ScrollPane scroll = (ScrollPane) dialogStage.getScene().lookup("#scroll");

        VBox requests = (VBox) scroll.getContent();

        requests.prefWidthProperty().bind(scroll.widthProperty());

        ArrayList<User> users = Database.getIncomingFriendRequests();
        for (User user : users){
            HBox request = new HBox();
            request.setAlignment(Pos.CENTER);
            request.setPrefWidth(300);
            request.setPrefHeight(50);
            VBox.setMargin(request, new Insets(20, 20, 10, 0));

            Button declineBtn = new Button("X");
            declineBtn.getStyleClass().add("element-delete");
            declineBtn.setPrefWidth(30);
            declineBtn.setPrefHeight(30);
            HBox.setMargin(declineBtn, new Insets(0, 10, 0, 0));
            declineBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
            declineBtn.setOnAction(event -> {
                requests.getChildren().remove(request);
                Database.removeFriendRequest(user.getUsername());
            });
            request.getChildren().add(declineBtn);

            Button acceptBtn = new Button("✔");
            acceptBtn.getStyleClass().add("element-delete");
            acceptBtn.setPrefWidth(30);
            acceptBtn.setPrefHeight(30);
            HBox.setMargin(acceptBtn, new Insets(0, 20, 0, 0));
            acceptBtn.cursorProperty().set(javafx.scene.Cursor.HAND);
            acceptBtn.setOnAction(event -> {
                requests.getChildren().remove(request);
                Database.removeFriendRequest(user.getUsername());
                Database.addFriend(user.getUsername());
                updateFriendBoxes();
            });
            request.getChildren().add(acceptBtn);

            Label username = new Label(user.getUsername());
            username.setPrefHeight(50);
            username.setAlignment(Pos.CENTER);
            username.setPrefWidth(300);
            username.getStyleClass().add("friend-label");
            request.getChildren().add(username);

            requests.getChildren().add(request);

        }
        dialogStage.showAndWait();
    }

    @FXML
    private void sendRequest() throws IOException{
        
        Stage dialogStage = App.createDialogStage("SendRequest");

        TextField friendsName = (TextField) dialogStage.getScene().lookup("#friendsName");
        Text friendsNameError = (Text) dialogStage.getScene().lookup("#friendsNameError");
        Button sendBtn = (Button) dialogStage.getScene().lookup("#sendBtn");
        AnchorPane window = (AnchorPane) dialogStage.getScene().lookup("#window");

        window.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendBtn.fire();
            }
        });

        sendBtn.setOnAction(event ->{

            friendsNameError.setVisible(false);
            if (friendsName.getText().isEmpty()){
                friendsNameError.setText("Username cannot be empty.");
                friendsNameError.setVisible(true);
            } else if (!Database.usernameExist(friendsName.getText())){
                friendsNameError.setText("User does not exist.");
                friendsNameError.setVisible(true);
            } else if (CurrentUser.getUsername().equals(friendsName.getText())){
                friendsNameError.setText("Cannot send request to yourself.");
                friendsNameError.setVisible(true);
            } else if (Database.friendRequestExist(CurrentUser.getUsername(), friendsName.getText())){
                friendsNameError.setText("Friend request already sent.");
                friendsNameError.setVisible(true);
            } else if (Database.friendRequestExist(friendsName.getText(), CurrentUser.getUsername())){
                Database.removeFriendRequest(friendsName.getText());
                Database.addFriend(friendsName.getText());
                updateFriendBoxes();
                dialogStage.close();
            } else if (Database.friendExists(friendsName.getText())){
                friendsNameError.setText("User is already your friend.");
                friendsNameError.setVisible(true);
            } 
            else{
                Database.setFriendRequest(CurrentUser.getUsername(), friendsName.getText());
                dialogStage.close();
            }
        });

        dialogStage.showAndWait();
    }
}
