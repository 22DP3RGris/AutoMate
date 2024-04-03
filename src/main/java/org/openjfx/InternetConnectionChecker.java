package org.openjfx;

import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.InetAddress;

public class InternetConnectionChecker {

    private static Stage dialogStage = new Stage();
    public static void waitForInternet(){
        while (!hasConnection()) {
            if (!dialogStage.isShowing())
            {
                try {
                    showError();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Check if the user has an internet connection
    private static boolean hasConnection() {
        try {
            // Check if the user can reach the Google DNS server
            InetAddress inetAddress = InetAddress.getByName("8.8.8.8");
            return inetAddress.isReachable(1000);
        } catch (Exception e) {
            return false;
        }
    }

    // Show an error dialog
    private static void showError() throws IOException {

        dialogStage = App.createDialogStage("InternetError");

        Button closeAppBtn = (Button) dialogStage.getScene().lookup("#closeAppBtn");

        closeAppBtn.setOnMouseClicked(event -> {
            System.exit(0);
        });

        dialogStage.showAndWait();
    }

}
