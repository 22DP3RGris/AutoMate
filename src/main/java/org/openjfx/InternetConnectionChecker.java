package org.openjfx;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;

public class InternetConnectionChecker {

    // Check if the user has an internet connection
    private static boolean checkConnection() {
        try {
            // Check if the user can reach the Google DNS server
            InetAddress inetAddress = InetAddress.getByName("8.8.8.8");
            return inetAddress.isReachable(1000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if the user has an internet connection, if not show an error dialog
    public static boolean hasInternetConnection() throws IOException {

        boolean internetConnection = checkConnection();

        if (internetConnection) {
            return true;
        }

        Stage dialogStage = App.createDialogStage("InternetError");
        dialogStage.showAndWait();
        return false;
    }
}
