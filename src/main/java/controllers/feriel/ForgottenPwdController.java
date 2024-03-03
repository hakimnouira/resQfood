package controllers.feriel;


import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import models.feriel.User;
import services.feriel.UserService;
import toolkit.MyAnimation;
import toolkit.MyTools;
import toolkit.MyEmailSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.Collectors;

public class ForgottenPwdController extends Controller {

    //TODO : complete class !!

    @FXML
    private TextField codeTf;

    @FXML
    private TextField mailTf;
    String mail;
    UserService us= new UserService();
    User currUser;
    int OTP ;


    @FXML
    void cancel(ActionEvent event) {
        MyTools.goTo("/feriel/LogIn.fxml",mailTf);
    }

    @FXML
    void resetPassword(ActionEvent event) {

        if (codeTf.getText().equals(String.valueOf(OTP))){

            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ResetPwd.fxml"));
                Parent root = loader.load();

                // Get the controller instance
               ResetPwdController controller = loader.getController();

                controller.initData(currUser);
                mailTf.getScene().setRoot(root);

            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }

        }else {
            MyAnimation.shake(codeTf);
        }
    }

    @FXML
    void sendCode(ActionEvent event) {
        mail=mailTf.getText().trim();
        System.out.println("mail=mailTf.getText().trim();\n");
        currUser = us.userByMail(mail);

        System.out.println(currUser+"userToResetPwd");
        if (currUser != null) {
            currUser.setCode(OTP);
            System.out.println("userToResetPwd: "+currUser);

            MyEmailSender.send(mail, "hii", getHtmlContent(generateOTP()));
            MyTools.showAlertInfo("Message sent","Your code was sent by email successfully");
        }

    }

    private String generateOTP() {
        Random random = new Random();
        OTP = random.nextInt(100000);
        System.out.println(OTP);

        return String.valueOf(OTP);
    }

    /**
     *
     * @param otp (String): one time pass
     * @return (String): Html mail
     */
    public String getHtmlContent(String otp) {
        try {

            InputStream inputStream = getClass().getResourceAsStream("/feriel/ForgottenPwdmail.html");
            System.out.println("l77 fpwdc");
            if (inputStream == null) {
                throw new IOException("Failed to load email template. File not found.");
            }

            String htmlContent;
            System.out.println("l83 fpwdc");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                htmlContent = reader.lines().collect(Collectors.joining("\n"));
                System.out.println("l87");
            }

            htmlContent = htmlContent.replace("{otp}", otp);
            return htmlContent;
        } catch (IOException e) {
            // Log the error or display a message to the user
            throw new RuntimeException("Failed to load email template: " + e.getMessage(), e);
        }
    }

}