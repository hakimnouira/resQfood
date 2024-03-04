package controllers.fatma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {
    @FXML
    private AnchorPane main_form;


    @FXML
    void gotoevent(ActionEvent event) {

    }

    @FXML
    void gotoc(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/Calendar.fxml"));
            main_form.getScene().setRoot(root);



        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }


    }
    @FXML
    void EventBtn(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/Showevent.fxml"));
            main_form.getScene().setRoot(root);



        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }

    }

    @FXML
    void ForumBtn(ActionEvent event) {

    }

    @FXML
    void donbck(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/siwar/dashboard.fxml"));
            main_form.getScene().setRoot(root);



        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }
    }

    @FXML
    void homebtn(ActionEvent event) {

    }
}
