package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.User;
import services.UserService;
import toolkit.PasswordEncryptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogInController {

    User user= null;
    UserService us= new UserService();

    @FXML
    private ImageView captchaImg;

    @FXML
    private TextField captchaInput;

    @FXML
    private Button idlogbt;

    @FXML
    private TextField loginMail_input;
    @FXML
    private PasswordField pwdInput;

    @FXML
    private Text createAcc;

    @FXML
    private Label umLbLog1;

    @FXML
    private ImageView um_logoviewLogin;


    @FXML
    void navigate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(getClass().getResource("/ShowUser.fxml"));
            // ageTf.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }



    @FXML
    void logInbt(ActionEvent event) {
        System.out.println("loginbtn");
        try {
           // String decrpyted="";

            //get all users
            List<User> usersL= us.read();
            //loop through all users
            for (User value : usersL) {
                //decrypt pwd to compare it w input from usr
               // decrpyted=PasswordEncryptor.decrypt(value.getPwd());

            // find user with matching email and pswd
                if (value.getEmail().equals(loginMail_input.getText())) {
                   // assert decrpyted != null;
                    if (value.getPwd().equals(pwdInput.getText())) {
                        System.out.println("in if");

                        user = value;
                        System.out.println("user" + user);
                    }
                }
            }

            if (user != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("exists");
                alert.showAndWait();
                //find role to redirect to appropriate interface

                  //      if(user.getRole().equals("Participant")|| user.getRole().equals("Donor")||user.getRole().equals("Volunteer")){
                           if (!user.getRole().equals("Admin")){
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParticipDash.fxml"));
                               // System.out.println("loader= "+loader);
                                Parent root= loader.load();
                                createAcc.getScene().setRoot(root);
                                ParticipDashController controller = loader.getController();
                                controller.initData(user);
                            } catch (IOException e) {
                                System.out.println("pb is here");
                                System.out.println("error"+e.getMessage());
                            }
/*
                        } else if (user.getRole().equals("Donor")||user.getRole().equals("Volunteer")) {
                           // goTo("/AddTestimony.fxml",createAcc);
*/
                        }else {
                            goTo("/DisplayUsers.fxml",createAcc);

                        }

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("User credentials incorrect. Please try again");
                alert.showAndWait();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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




    @FXML
    void createUserfromlogin(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewuser.fxml"));
            Parent root= loader.load();

            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LogIn.fxml")));


            createAcc.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
        }

    }



}



