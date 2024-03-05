package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Post;
import services.PostService;

import java.sql.SQLException;
import java.util.List;






public class ShowUserController {
private final PostService ps = new PostService();
  private  ObservableList<Post> observableList;

    @FXML
    private TableColumn<Post, Integer> ageCol;

    @FXML
    private TableColumn<Post, String> firstNameCol;

    @FXML
    private TableColumn<Post, String> lastNameCol;

    @FXML
    private TableView<Post> usersTableView;


    @FXML
    void initialize() {
        try {
            List<Post> usersList = ps.read();
            observableList = FXCollections.observableList(usersList);
            usersTableView.setItems(observableList);
            ageCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }

    }
    @FXML
    void removeUser(ActionEvent event) {
        observableList.remove(0);
    }


}
