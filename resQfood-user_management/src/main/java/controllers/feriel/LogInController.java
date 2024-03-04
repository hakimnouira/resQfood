package controllers.feriel;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.feriel.User;
import nl.captcha.Captcha;

import java.awt.*;

import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.FishEyeGimpyRenderer;
import services.feriel.UserService;


import javafx.embed.swing.SwingFXUtils;
import toolkit.MyAnimation;
import toolkit.MyTools;
import toolkit.PasswordEncryptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LogInController extends Controller {

    User user= new User();
    UserService us= new UserService();



    @FXML
    private TextField captchaInput;
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
    boolean captchaIsCorrect;




    public void initialize() {
        generateCaptcha();
        captchaIsCorrect=false;


    }

    @FXML
    void logInbt(ActionEvent event) {
        System.out.println("loginbtn");
        try {
            //get all users
            List<User> usersL= us.read();
            System.out.println(captchaInput.getText()+" c le input");

            System.out.println("captchaInput.getText().isEmpty()"+captchaInput.getText().isEmpty());
            if (captchaInput.getText().isEmpty()) {
                MyAnimation.shake(captchaInput);
                MyTools.showAlertError("Captcha is required. Please enter the captcha.");

                return;
            }

            System.out.println("isValidCaptcha()"+isValidCaptcha());
            if (!isValidCaptcha()) {
               MyAnimation.shake(captchaInput);
                MyTools.showAlertError("Captcha is incorrect. Please try again");
                return;
            }


            //loop through all users
            for (User value : usersL) {
                //encrypt pwd to compare it w input from usr
               String encrypted= PasswordEncryptor.encrypt(pwdInput.getText());
                String normal=pwdInput.getText();
            // find user with matching email and pswd
                if (value.getEmail().equals(loginMail_input.getText())) {
                    if (value.getPwd().equals(encrypted)) {
                        user = value;
                        UserService.loggedIn=user;
                        System.out.println("user" + user);
                        System.out.println("logedin"+UserService.loggedIn);
                    }
                }
            }


                if (user != null && user.getRole() != null  ){
                    MyTools.showAlertInfo("exists","exists");

                //find role to redirect to appropriate interface

                           if (!user.getRole().equals("Admin")){
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/designation.fxml"));
                               // FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/designation.fxml"));

                                Parent root= loader.load();
                                // Set dimensions for the window
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setWidth(872); // Set width
                                stage.setHeight(300); // Set height

                                // Set the loaded FXML file as the root of the scene
                                stage.setScene(new Scene(root));
                                createAcc.getScene().setRoot(root);
                                ParticipDashController controller = loader.getController();
                              controller.initData(user);
                            } catch (IOException e) {
                                System.out.println("pb is here");
                                System.out.println("error"+e.getMessage());
                            }

                        }else {
                            MyTools.goTo("/siwar/dashboard.fxml",createAcc);

                        }
                }
                else {
                   MyTools.showAlertError("User credentials incorrect. Please try again");
                }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    @FXML
    void createUserfromlogin(MouseEvent event) {
        MyTools.goTo("/feriel/AddNewuser.fxml",createAcc);

    }

    @FXML
    void pwdForgotten(MouseEvent event) {
        MyTools.goTo("/feriel/ForgottenPwd.fxml",createAcc);

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
        return captcha;



    }

    boolean isValidCaptcha(){

        if (captcha != null) {
            System.out.println(captcha.getAnswer());

           // if (captcha.isCorrect(captchaInput.getText())) {
                if (captcha.getAnswer().equals(captchaInput.getText())) {

                    System.out.println("isValidCaptcha if");
                    captchaIsCorrect = true;
                return true;
            } else {
                System.out.println("isValidCaptcha else");
                MyAnimation.shake(captchaInput);
                captcha = generateCaptcha();
                //captchaInput.clear();
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



