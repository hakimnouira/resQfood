package controllers.feriel;

import controllers.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.feriel.Testimony;
import services.feriel.TestimonyService;
import services.feriel.UserService;
import toolkit.MyTools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *For ADMIN only
 *
 */

public class DisplayTestimonyController extends Controller {
    TestimonyService ts= new TestimonyService();

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


    @FXML
    private TableView<Testimony> userTable;

    @FXML
    private TableColumn<Testimony, Integer> idtestv;

    @FXML
    private TableColumn<Testimony,String> titlelv;

    @FXML
    private TableColumn<Testimony, String> textv;

    @FXML
    private TableColumn<Testimony, Integer> user_idv;

    @FXML
    private TableColumn<Testimony, String> statusv;

   // @FXML
   // private ComboBox<String> filtrerSearchcombobox=new ComboBox<>();
    private final String[] filter= {"By first or last name","By id","By email","By role", "By area"};

    private ObservableList<Testimony> observableList;
    private List<Testimony> testimonyList;






    @FXML
    void initialize() {

      //  filtrerSearchcombobox.getItems().addAll(filter);

        try {
            testimonyList = ts.read();
            observableList = FXCollections.observableList(testimonyList);
            userTable.setItems(observableList);
            idtestv.setCellValueFactory(new PropertyValueFactory<>("t_id"));
            titlelv.setCellValueFactory(new PropertyValueFactory<>("title"));
            textv.setCellValueFactory(new PropertyValueFactory<>("txt"));
            user_idv.setCellValueFactory(new PropertyValueFactory<>("userId"));
            statusv.setCellValueFactory(new PropertyValueFactory<>("status"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }

    }

    /**
     *
     * ADD
     */
    @FXML
    void addTestimonyBt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/AddTestimony.fxml"));
            Parent root = loader.load();
            addtestbt.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     * DELETE Testimony
     */
    @FXML
    public void delTestbt(ActionEvent actionEvent) {


        Testimony selectedItem = userTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null ) {
            // Remove the selected user from the TableView
            observableList.remove(selectedItem);

            try {
                // Delete the user from the database
                ts.delete(selectedItem.getT_id());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    @FXML
    void acceptTestimBt(ActionEvent event) {
        Testimony selectedItem = userTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null ) {
            // Accept the selected user
                selectedItem.setStatus("Accepted");
            try {

                // Update the user from the database
                ts.update(selectedItem);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }
        observableList = FXCollections.observableList(testimonyList);
        reloadTestimony(event);

    }


    /**
     * GO TO TESTIMONY DISPLAY
     */
    @FXML
    void reloadTestimony(ActionEvent event) {
       MyTools.goTo("/feriel/DisplayTestimonies.fxml",addtestbt);

    }


    @FXML
    void logoutDash(ActionEvent event) {
        UserService.loggedIn=null;
        MyTools.goTo("/feriel/LogIn.fxml",addtestbt);

    }

    /**
     * Go to Users Display dash for ADMIN
     */
    @FXML
    void usersBt(ActionEvent event) {
        MyTools.goTo("/feriel/DisplayUsers.fxml",addtestbt);

    }

    @FXML
    void donationBt(ActionEvent event) {

    }





    @FXML
    void eventsBt(ActionEvent event) {

    }

    @FXML
    void forumBt(ActionEvent event) {

    }


    @FXML
    void stockBt(ActionEvent event) {

    }

    void goTo(String file){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            Parent root= loader.load();
            addtestbt.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }




}

