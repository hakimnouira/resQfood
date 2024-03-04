package controllers.siwar;

import controllers.ali.AddLine;
import controllers.ali.ShowProduct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class designationController {



    @FXML
    private Button Backb;

    @FXML
    void DonationBtnClick(ActionEvent event) {
        try {
            // Load the AddDonation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/AddDonation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 730, 343)); // Set width and height according to the preferred dimensions in the FXML
            stage.show();
            // Get the stage from the action event
           // Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

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

    @FXML
    void BackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void EXITBTN(ActionEvent event) {

    }

    public void StockBtnClick(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ali/addLine.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the FXML file
            AddLine showProductController = loader.getController();

            // Optionally, pass any data to the controller before showing the scene

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the ActionEvent
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential errors loading the FXML file
        }
    }

    @FXML
    void ForumBtnClick(ActionEvent event) {
        try {
            // Load the addUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hakim/addUser.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void TestimoniesBtnClick(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ParticipViewTestim.fxml"));
            Parent root = loader.load();

            // Get the stage from the ActionEvent
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UsersBtnClick(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ParticipDash.fxml"));
            Parent root = loader.load();

            // Get the stage from the ActionEvent
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
