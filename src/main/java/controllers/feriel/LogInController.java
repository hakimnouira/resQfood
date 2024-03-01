package controllers.feriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.feriel.User;
import services.feriel.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogInController {

    User user= new User();
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
    void logInbt(ActionEvent event) {
        System.out.println("loginbtn");
        try {
            //get all users
            List<User> usersL= us.read();
            //loop through all users
            for (User value : usersL) {
                //decrypt pwd to compare it w input from usr
               // decrpyted=PasswordEncryptor.decrypt(value.getPwd());

            // find user with matching email and pswd
                if (value.getEmail().equals(loginMail_input.getText())) {
                    us.setLoggedInUser(value);//TODO/ essayer meth de hamza ici pr pwd retriev

                    if (value.getPwd().equals(pwdInput.getText())) {
                        user = value;
                        System.out.println("user" + user);
                        System.out.println("logedin"+UserService.loggedIn);
                    }
                }
            }

            if (user != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("exists");
                alert.showAndWait();
                //find role to redirect to appropriate interface

                           if (user.getId()==0||!user.getRole().equals("Admin")){
                            try {
                              //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/ParticipDash.fxml"));
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/designation.fxml"));

                                Parent root= loader.load();
                                createAcc.getScene().setRoot(root);
                               // ParticipDashController controller = loader.getController();
                              //  controller.initData(user);
                            } catch (IOException e) {
                                System.out.println("pb is here");
                                System.out.println("error"+e.getMessage());
                            }

                        }else {
                            goTo("/fereil/DisplayUsers.fxml",createAcc);

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

    @FXML
    void createUserfromlogin(MouseEvent event) {
        goTo("/fereil/AddNewuser.fxml",createAcc);

    }

    @FXML
    void pwdForgotten(MouseEvent event) {
        goTo("/fereil/ForgottenPwd.fxml",createAcc);
    }

    void goTo(String file, Node node){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            Parent root= loader.load();
            node.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("error"+e.getMessage());
            throw new RuntimeException(e);
        }

    }








}



