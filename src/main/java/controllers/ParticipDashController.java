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
import javafx.scene.text.Text;
import models.User;
import services.UserService;
import toolkit.ToolsFeriel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipDashController {

    List<User> usersList= new ArrayList<>();
    User currUser= new User();
    UserService us= new UserService();

    private ObservableList<User> observableList;



    @FXML
    private Button btn_testim;

    @FXML
    private Button btn_users;

    @FXML
    private Button deluserbt;

    @FXML
    private Text fnametxt;
    @FXML
    private Button modifuserbt;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private Pane inner_panel;
    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private TableView<User> userTable;


    @FXML
    private TableColumn<User, String> fnamev;

    @FXML
    private TableColumn<User, Integer> idv;


    @FXML
    private TableColumn<User, String> lnamev;

    @FXML
    private TableColumn<User, String> mailv;


    @FXML
    private TableColumn<User, String> pwdv;

    @FXML
    private TableColumn<User,String> rolev;
    @FXML
    private TableColumn<User, String> areav;
    @FXML
    private TableColumn<User, Integer> phonev;



    public void initData(User user){
       assert user !=null;
            fnametxt.setText(user.getFirstName());
            currUser=user;
            usersList.add(currUser);
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





    }

    @FXML
    void modifUserD(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifUser.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ModifUserController controller = loader.getController();
            controller.initData(currUser);

            userTable.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @FXML
    void deleteUserD(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("attention");

        alert.showAndWait();

        try {
            us.delete(currUser.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void toTestimDash(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParticipViewTestim.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ParticipViewTestimController controller = loader.getController();
            controller.initData(currUser);

            userTable.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @FXML
    void dowloadInfo(ActionEvent event) {

    }

    //TODO AJOUTER UN GOTO USERS URGG

    @FXML
    void eventsBt(ActionEvent event) {


    }

    @FXML
    void forumBt(ActionEvent event) {

    }

    @FXML
    void logoutDash(ActionEvent event) {
        goTo("/LogIn.fxml",deluserbt);
    }



    @FXML
    void stockBt(ActionEvent event) {

    }

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
