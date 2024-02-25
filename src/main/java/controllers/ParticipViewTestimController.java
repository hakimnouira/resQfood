package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.Testimony;
import models.User;
import services.TestimonyService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Specific for Participant
 *
 *
 */

public class ParticipViewTestimController {
    Testimony tt= new Testimony();
    TestimonyService ts= new TestimonyService();
    User currUser= new User();


    @FXML
    private Button addtestbt;

    @FXML
    private Button btn_testim;

    @FXML
    private Button btn_users;
    @FXML
    private Pane inner_panel;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private AnchorPane side_ankerpane;


    /**
     * Personal Testim table
     */
    @FXML
    private TableView<Testimony> UsrPersonalTestim;


    @FXML
    private TableColumn<Testimony, Integer> idtestv;
    @FXML
    private TableColumn<Testimony, Integer> user_idv;
    @FXML
    private TableColumn<Testimony, String> titlelv;
    @FXML
    private TableColumn<Testimony, String> textv;
    @FXML
    private TableColumn<Testimony, String> statusv;

/**
 * testim of All users that have stat accepted
 */
    @FXML
    private TableView<Testimony> testimvAllusers;
    @FXML
    private TableColumn<Testimony, String> idtestv1;
    @FXML
    private TableColumn<Testimony, Integer> user_idv1;
    @FXML
    private TableColumn<Testimony, String> textv1;
    @FXML
    private TableColumn<Testimony, String> titlelv1;
    @FXML
    private TableColumn<Testimony, String> statusv1;

    ObservableList<Testimony> observableListGeneral ;
    ObservableList<Testimony> observableListPersonal;


    public void initData(User user) {
        assert user != null;
        //fnametxt.setText(user.getFirstName());
        currUser = user;

        try {

            List<Testimony> testimonyList = ts.read();
            List<Testimony> acceptedGen = testimonyList.stream().filter(t -> {
                        if (t.getStatus() != null) {
                            return t.getStatus().equals("accepted");
                        }
                        return false;
                    })
                    .toList();
            List<Testimony> personalT = testimonyList.stream().filter(t -> t.getUserId() == currUser.getId()).toList();


            //set up personal table view
            observableListPersonal = FXCollections.observableList(personalT);
            UsrPersonalTestim.setItems(observableListPersonal);
            idtestv.setCellValueFactory(new PropertyValueFactory<>("t_id"));
            titlelv.setCellValueFactory(new PropertyValueFactory<>("title"));
            textv.setCellValueFactory(new PropertyValueFactory<>("txt"));
            user_idv.setCellValueFactory(new PropertyValueFactory<>("userId"));
            statusv.setCellValueFactory(new PropertyValueFactory<>("status"));


            //set up general table view

            observableListGeneral = FXCollections.observableList(acceptedGen);
            testimvAllusers.setItems(observableListGeneral);
            idtestv1.setCellValueFactory(new PropertyValueFactory<>("t_id"));
            titlelv1.setCellValueFactory(new PropertyValueFactory<>("title"));
            textv1.setCellValueFactory(new PropertyValueFactory<>("txt"));
            user_idv1.setCellValueFactory(new PropertyValueFactory<>("userId"));
            statusv1.setCellValueFactory(new PropertyValueFactory<>("status"));




        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }


    }



    @FXML
    void addTestbt(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTestimony.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AddTestimonyController controller = loader.getController();
            controller.initData(currUser);

            addtestbt.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void delTestbt(ActionEvent event) {

        Testimony selectedItem = UsrPersonalTestim.getSelectionModel().getSelectedItem();
        //user can only delete his testimonies
        if (selectedItem != null && selectedItem.getUserId()== currUser.getId() ) {
            // Remove the selected user from the TableView
            observableListPersonal.remove(selectedItem);

            try {
                // Delete the user from the database
                ts.delete(selectedItem.getT_id());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }



    }

    @FXML
    void modifTestimbt(ActionEvent event) {

        Testimony selectedItem = UsrPersonalTestim.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifTestimony.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ModifTestimonyController controller = loader.getController();
            //give data to modifying page
            controller.initData(selectedItem);

            addtestbt.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void eventsBt(ActionEvent event) {

    }

    @FXML
    void forumBt(ActionEvent event) {

    }

    @FXML
    void logoutDash(ActionEvent event) {
        goTo("/LogIn.fxml",btn_users);
    }



    @FXML
    void stockBt(ActionEvent event) {

    }

    /**
     *
     *
     */

    @FXML
    void testimBt(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParticipDash.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ParticipDashController controller = loader.getController();
            controller.initData(currUser);

            btn_users.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param file : path for fxml
     * @param node : node from scene
     */
    void goTo(String file, Node node){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            Parent root= loader.load();
            node.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }

}
