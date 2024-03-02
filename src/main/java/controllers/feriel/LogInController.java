package controllers.feriel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.feriel.User;
import nl.captcha.Captcha;

import java.awt.*;

import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import services.feriel.UserService;


import javafx.embed.swing.SwingFXUtils;
import toolkit.MyAnimation;
import toolkit.MyTools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogInController {

    User user= new User();
    UserService us= new UserService();

    @FXML
    private ImageView captchaImg;

    @FXML
    private TextField captchaInput= new TextField();
    ;

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
    private ImageView captchImg;
    Captcha captcha= new Captcha.Builder(250, 150).build();
    boolean captchaIsCorrect=false;




    public void initialize() {
        generateCaptcha();
    }

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

                    if (value.getPwd().equals(pwdInput.getText())) {
                        user = value;
                        UserService.loggedIn=user;
                        System.out.println("user" + user);
                        System.out.println("logedin"+UserService.loggedIn);
                    }
                }
            }
            if (isValidCaptcha()){

                if (user != null && user.getRole() != null && isValidCaptcha()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("exists");
                alert.showAndWait();
                //find role to redirect to appropriate interface

                           if (!user.getRole().equals("Admin")){
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
                            MyTools.goTo("/feriel/DisplayUsers.fxml",createAcc);

                        }

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
        goTo("/feriel/AddNewuser.fxml",createAcc);

    }

    @FXML
    void pwdForgotten(MouseEvent event) {
        goTo("/feriel/ForgottenPwd.fxml",createAcc);

    }

    public Captcha generateCaptcha() {

        Captcha.Builder builder = new Captcha.Builder(250, 150)
                .addText()
                .addBackground(new FlatColorBackgroundProducer(Color.PINK))
                .addNoise()
                .gimp(new FishEyeGimpyRenderer())
                .addBorder();

        Captcha captcha = builder.build();
        System.out.println(captcha.getAnswer());
        captchImg.setImage(SwingFXUtils.toFXImage(captcha.getImage(), null));
        captchaInput.clear();
        return captcha;

        /*
        Captcha.Builder builder = new Captcha.Builder(250, 150);
        builder.addText();
        builder.addBackground(new FlatColorBackgroundProducer(Color.YELLOW));
        builder.addNoise();
        builder.gimp(new FishEyeGimpyRenderer());
        builder.addBorder();

        Captcha captchaV = builder.build();

        System.out.println(captchaV.getImage());
        Image image = SwingFXUtils.toFXImage(captchaV.getImage(), null);

        captchImg.setImage(image);
        captchaInput.setText("");



        return captchaV;
        */

    }

    boolean isValidCaptcha(){

        if (captcha != null) {
            System.out.println(captcha.getAnswer());

            if (captcha.isCorrect(captchaInput.getText())) {
                captchaIsCorrect = true;
                return true;
            } else {
                MyAnimation.shake(captchaInput);
                captcha = generateCaptcha();
                captchaInput.clear();
                return false;
            }
        }
        System.err.println("Captcha is not initialized");
        return false;
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



