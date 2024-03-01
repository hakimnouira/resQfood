package controllers;

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
import models.User;
import services.UserService;
import toolkit.MyTools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayUsersController {
   private final UserService us=new UserService();
    private ObservableList<User> observableList;
    private final String[] filter= {"By first or last name","By id","By email","By role", "By area","None"};
    private List<User> usersList;
    List<User> filtered;


    @FXML
    private Button adduserbt;
    @FXML
    private Button deluserbt;
    @FXML
    private Button modifuserbt;
    @FXML
    private TextField searchTf;

    @FXML
    private Button btn_testim;

    @FXML
    private Button btn_users;
    @FXML
    private Pane most_inner_pane;
    @FXML
    private Pane inner_panel;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private ComboBox<String> filtrerSearchcombobox;



    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idv;

    @FXML
    private TableColumn<User, String> fnamev;

    @FXML
    private TableColumn<User, String> lnamev= new TableColumn<>("lName");

    @FXML
    private TableColumn<User, String> mailv;

    @FXML
    private TableColumn<User, String> pwdv;
    @FXML
    private TableColumn<User, String> areav;

    @FXML
    private TableColumn<User, String> rolev;
    @FXML
    private TableColumn<User, Integer> phonev;





    @FXML
    void initialize() {
        filtrerSearchcombobox.getItems().addAll(filter);

        try {
            usersList = us.read();
            observableList = FXCollections.observableList(usersList);
            userTable.setItems(observableList);
            idv.setCellValueFactory(new PropertyValueFactory<>("id"));
            lnamev.setCellValueFactory(new PropertyValueFactory<>("LName"));
            fnamev.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            mailv.setCellValueFactory(new PropertyValueFactory<>("email"));
            pwdv.setCellValueFactory(new PropertyValueFactory<>("pwd"));
            areav.setCellValueFactory(new PropertyValueFactory<>("area"));
            rolev.setCellValueFactory(new PropertyValueFactory<>("role"));
            phonev.setCellValueFactory(new PropertyValueFactory<>("phone"));

            //Search

            searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
                String criteria = filtrerSearchcombobox.getSelectionModel().getSelectedItem();

                if (newValue != null && criteria != null) {
                System.out.println(criteria);

                String req = newValue.trim().toLowerCase();
                filtered= new ArrayList<>(usersList);


                //List<User> filtered;
                switch (criteria) {
                    case "Search by":
                        break;
                    case "By first or last name":
                        filtered = usersList.stream()
                                .filter(u -> u.getFirstName().toLowerCase().startsWith(req) || u.getLName().toLowerCase().startsWith(req))
                                .collect(Collectors.toList());
                        break;
                    case "By id":
                        filtered = usersList.stream()
                                .filter(u -> String.valueOf(u.getId()).startsWith(req))
                                .collect(Collectors.toList());
                        break;
                    case "By email":
                        filtered = usersList.stream()
                                .filter(u -> u.getEmail().toLowerCase().startsWith(req))
                                .collect(Collectors.toList());
                        break;
                    case "By role":
                        filtered = usersList.stream()
                                .filter(u -> u.getRole().toLowerCase().startsWith(req))
                                .collect(Collectors.toList());
                        break;
                    case "By area":
                        filtered = usersList.stream()
                                .filter(u -> u.getArea().toLowerCase().startsWith(req))
                                .collect(Collectors.toList());
                        break;
                    default:
                        System.out.println("Invalid criteria: " + criteria);
                        // Handle or display error message for unknown criteria
                        filtered = Collections.emptyList();
                        break;
                }

                // Update the table with the filtered data
                observableList = FXCollections.observableList(filtered);
                userTable.setItems(observableList);
            }
          }
        );




        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();        }

    }


    @FXML
    void addUserD(ActionEvent event) {

        MyTools.goTo("/AddNewuser.fxml",btn_users);
        try {

            //TODO mettre init data pr accepter un user et coder le cancel
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewuser.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LogIn.fxml")));

            btn_users.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }

    /**
     * button delete
     */
    @FXML
    void deleteUserD(ActionEvent event) {

        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Remove the selected user from the TableView
            observableList.remove(selectedUser);

            try {
                // Delete the user from the database
                us.delete(selectedUser.getId()); // Assuming you have a method deleteUser in your DAO
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    @FXML
    void logoutDash(ActionEvent event) {
       MyTools.goTo("/LogIn.fxml",btn_users);

    }


    @FXML
    void modifUserD(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        // Load the FXML file for the modify user window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifUser.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ModifUserController controller = loader.getController();
            controller.initSelectedUser(selectedUser);

            userTable.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }

    @FXML
    void searchFor(ActionEvent event) {

        //TODO METIER COMPTER LE NBR DE PERS PAR ROLE ET LES REGIONS EN FONCTION DE LEUR ROLE AKA CHART
        

    }

    @FXML
    void testimBtn(ActionEvent event) {
        MyTools.goTo("/DisplayTestimonies.fxml",btn_users);


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

//    void goTo(String file){
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
//            Parent root= loader.load();
//            btn_users.getScene().setRoot(root);
//        } catch (IOException e) {
//            System.out.println("error"+e.getMessage());
//        }
//
//    }



}
