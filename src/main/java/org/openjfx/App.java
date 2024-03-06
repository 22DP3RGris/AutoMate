package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;
    private static Scene scene;
    private static Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage AppStage) throws IOException, Exception {
        MacroFunctionality.initialize();
        
        stage = AppStage;
        stage.setTitle("AutoMate");
        scene = new Scene(loadFXML("Login"));
        stage.initStyle(StageStyle.TRANSPARENT);
        setResizable(false);

        TopBarController.dragWindow((AnchorPane) scene.lookup("#topBar"));

        stage.setScene(scene);

        stage.show();
        
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        stage.sizeToScene();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        TopBarController.dragWindow((AnchorPane) scene.lookup("#topBar"));
    }

    public static void setResizable(boolean resizable){
        stage.setResizable(resizable);
    }

    public static Stage getStage(){
        return stage;
    }

    public static void minimizeStage(){
        stage.setIconified(true);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}