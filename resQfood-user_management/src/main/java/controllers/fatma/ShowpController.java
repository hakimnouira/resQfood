package controllers.fatma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import models.fatma.Participations;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.fatma.ParticipationService;

public class ShowpController {
    @FXML
    private TableColumn<Participations, Integer> idp_col;

    @FXML
    private TableView<Participations> particip_view;
    @FXML
    private TableColumn<Participations, String> pn_col;

    private  ParticipationService ps = new ParticipationService();

    @FXML
    void initialize() {
        try {
            List<Participations> plist = ps.read();
            ObservableList<Participations> observableList = FXCollections.observableList(plist);
            particip_view.setItems(observableList);
            //idp_col.setCellValueFactory(new PropertyValueFactory<>("event's id"));
            idp_col.setCellValueFactory(new PropertyValueFactory<>("id_event"));
            pn_col.setCellValueFactory(new PropertyValueFactory<>("particip_name") );


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    @FXML
    void back(ActionEvent event) {
        try {
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            //  Parent root= loader.load();

            Parent root = FXMLLoader.load(getClass().getResource("/fatma/Showevent.fxml"));
            particip_view.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }

    }
}
