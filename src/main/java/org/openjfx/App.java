package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;
    private static String currentScene;
    private static Scene scene;
    private static Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage appStage) throws Exception {
        
        MacroFunctionality.initialize();
        stage = appStage;
        scene = new Scene(loadFXML("Login"));
        stage.setTitle("AutoMate");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        initTopbar();

        stage.setScene(scene);
        stage.show();

        // ResizeHelper.addResizeListener(stage);
    }

    public static void setRoot(String fxml, boolean newScene) throws IOException {
        scene.setRoot(loadFXML(fxml));
        if (newScene){
            stage.sizeToScene();
            centerStage();
        }
        initTopbar();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        currentScene = fxml;
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        Database.init();
        launch(args);
    }

    // Additional functionality
    public static void setResizable(boolean resizable){
        stage.setResizable(resizable);
    }

    public static Stage getStage(){
        return stage;
    }

    public static String getCurrentScene(){
        return currentScene;
    }

    private static void centerStage(){
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    } 

    private static void initTopbar() throws IOException{
        Topbar.maximize((Button) scene.lookup("#maxBtn"));
        Topbar.hide((Button) scene.lookup("#hideBtn"));
        Topbar.close((Button) scene.lookup("#exitBtn"));
        Topbar.dragWindow((AnchorPane) scene.lookup("#topBar"));
    }

}