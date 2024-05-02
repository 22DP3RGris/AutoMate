package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Stage stage; // Stores window
    private static String currentScene; // Stores current FXML file name
    private static Scene scene; // Stores current scene
    private static final Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds(); // Stores user screen bounds

    // Happens when the application starts, sets up the application window
    @Override
    public void start(Stage appStage) throws Exception {

        stage = appStage;
        scene = new Scene(loadFXML("login"));
        stage.setTitle("AutoMate");
        stage.initStyle(StageStyle.TRANSPARENT); // Removes built-in topbar
        stage.setResizable(false);

        Topbar.init(scene); // Initialize topbar functionality

        stage.setScene(scene);
        stage.show();

        Database.init();
        JsonManager.init();

    }

    // Changes the scene to the specified FXML file
    public static void setRoot(String fxml, boolean newScene) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Topbar.init(scene);
        if (newScene){ // If window size changes
            stage.sizeToScene();
            centerStage();                                            
        } 
        // If page has SideNav then initialize it
        if (!fxml.equals("login") && !fxml.equals("register")){ 
            SideNav.init(scene);
        }
    }

    // Loads the FXML file, by file name
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        currentScene = fxml;
        return fxmlLoader.load();
    }

    // Main method, initializes the database and launches the application
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    // Sets the window resizable
    public static void setResizable(boolean resizable){
        stage.setResizable(resizable);
    }

    // Gets the window
    public static Stage getStage(){
        return stage;
    }

    // Gets the FXML file name of the current scene
    public static String getCurrentScene(){
        return currentScene;
    }

    public static Scene getScene(){
        return scene;
    }

    // Centers the window
    private static void centerStage(){
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    // Creates a dialog stage and sets up the topbar.
    public static Stage createDialogStage(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        // Create the dialog stage.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        dialogStage.setScene(new Scene(loader.load()));
        dialogStage.setResizable(false);
        dialogStage.sizeToScene();

        // Initialize the topbar functionality.
        Topbar.dragDialog((AnchorPane) dialogStage.getScene().lookup("#topBar"), dialogStage);
        Topbar.closeDialog((Button) dialogStage.getScene().lookup("#exitBtn"), dialogStage);

        return dialogStage;
    }
}