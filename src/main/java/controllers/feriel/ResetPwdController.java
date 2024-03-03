package controllers.feriel;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.feriel.User;
import services.feriel.UserService;
import toolkit.MyTools;

import java.sql.SQLException;

public class ResetPwdController extends Controller {
    UserService us= new UserService();
    String userMail="";

    @FXML
    private TextField newpwdTf;


    void initData(User currUser){
        userMail= currUser.getEmail();
    }

    @FXML
    void cancel(ActionEvent event) {
        MyTools.goTo("/feriel/LogIn.fxml",newpwdTf);


    }

    @FXML
    void confirmNewPwd(ActionEvent event) {
        try {
            us.updatePassword(userMail,newpwdTf.getText());
            MyTools.showAlertInfo("Password updated","Your Password was updated successfully! Go to the login page to access your account.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
