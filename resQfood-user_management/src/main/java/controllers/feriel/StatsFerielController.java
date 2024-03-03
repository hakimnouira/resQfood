package controllers.feriel;

import controllers.Controller;
import javafx.event.ActionEvent;
import models.feriel.User;
import services.feriel.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StatsFerielController extends Controller {
    UserService us;
    List<User> listUsers= new ArrayList<>();

    public void initialize(){
        calc();
    }
    public void logoutDash(ActionEvent actionEvent) {
    }

    public void eventsBt(ActionEvent actionEvent) {
    }

    public void stockBt(ActionEvent actionEvent) {
    }

    public void forumBt(ActionEvent actionEvent) {
    }

    public void donationBt(ActionEvent actionEvent) {
    }

    void calc(){
        try {
            listUsers= us.read();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listUsers.stream()
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()))
                .forEach((role, count) -> System.out.println(role + ": " + count));
    }
}
