package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.User;
import services.UserService;
import toolkit.MyTools;
import utils.MyEmailSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.Collectors;

public class ForgottenPwdController {

    //TODO : complete class !!

    @FXML
    private TextField codeTf;

    @FXML
    private TextField mailTf;
    String mail;
    UserService us= new UserService();
    User user;
    int OTP ;

    @FXML
    void resetPassword(ActionEvent event) {

        if (codeTf.getText().equals(String.valueOf(OTP))){
            MyTools.goTo("/ResetPwd.fxml",mailTf);

        }
    }

    @FXML
    void sendCode(ActionEvent event) {
        mail=mailTf.getText();

        user = us.userByMail(mail);
        user.setCode(OTP);


        MyEmailSender.send(mail,"hii",getHtmlContent(generateOTP()));

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
    private String getHtmlContent(String otp) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/ForgottenPwdmail.html");
            if (inputStream == null) {
                throw new IOException("Failed to load email template. File not found.");
            }

            String htmlContent;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                htmlContent = reader.lines().collect(Collectors.joining("\n"));
            }

            htmlContent = htmlContent.replace("{otp}", otp);
            return htmlContent;
        } catch (IOException e) {
            // Log the error or display a message to the user
            throw new RuntimeException("Failed to load email template: " + e.getMessage(), e);
        }
    }

}