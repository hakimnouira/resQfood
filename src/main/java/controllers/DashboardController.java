package controllers;

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
    void donbck(ActionEvent event) {

    }
    @FXML
    void gotoevent(ActionEvent event) {

    }

    @FXML
    void gotoc(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/Calendar.fxml"));
            main_form.getScene().setRoot(root);



        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }


    }
}
