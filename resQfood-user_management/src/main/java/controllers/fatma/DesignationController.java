package controllers.fatma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DesignationController {

    @FXML
    void DonationBtnClick(ActionEvent event) {
        try {
            // Load the AddDonation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/AddDonation.fxml"));
            Parent root = loader.load();

            // Get the stage from the action event
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotoevent(ActionEvent event) {
        try {
            // Load the AddDonation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fatma/showusers.fxml"));
            Parent root = loader.load();

            // Get the stage from the action event
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
