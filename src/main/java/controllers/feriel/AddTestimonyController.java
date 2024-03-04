package controllers.feriel;

import controllers.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.feriel.Testimony;
import models.feriel.User;
import services.feriel.TestimonyService;
import toolkit.MyAnimation;
import toolkit.MyTools;

import java.io.IOException;
import java.sql.SQLException;

import static controllers.feriel.AddNewuserController.showInputIncorect;
import static toolkit.MyTools.showAlertInfo;

public class AddTestimonyController extends Controller {
    Testimony t= new Testimony();
    TestimonyService ts= new TestimonyService();
    User currUser= new User();

    @FXML
    private TextField titleFl;

    @FXML
    private TextField txtFl;


    /**
     * Data given by page that's calling this one
     * @param user (User): cuurent user
     */
    public void initData(User user){
        currUser = user;
    }

    @FXML
    void addTestimonyBt(ActionEvent event) {

        if (titleFl.getText().isEmpty() || !titleFl.getText().matches("^[a-zA-Z]+$")) {
            showInputIncorect(titleFl);
            return;
        }
        if (txtFl.getText().isEmpty()) {
            showInputIncorect(txtFl);
            return;
        }

        try {
            t.setTxt(txtFl.getText());
            t.setTitle(titleFl.getText());
            if (currUser != null || currUser.getRole().equals("Admin")) {
                t.setUserId(currUser.getId());
            } else
                t.setUserId(0);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            ts.create(t);
            showAlertInfo("Success","User added successfully");
            cancelbt(event);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * go to DisplayTestimonies.fxml if admin
     * or go to ParticipViewTestim.fxml
     */

    @FXML
    void cancelbt(ActionEvent event) {

        //redirect admin or non user
        //System.out.println("l 93"+currUser);
        if (currUser.getId() == 0) {
            currUser = new User();
            currUser.setId(0);
            currUser.setRole("Admin");
            //  System.out.println("l 98"+currUser);
        }

        if ( currUser.getId() == 0 || currUser.getRole().equals("Admin")) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/DisplayTestimonies.fxml"));
                Parent root = loader.load();
                titleFl.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println("error" + e.getMessage());
            }

        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/ParticipDash.fxml"));
                Parent root = loader.load();

                // Get the controller instance
                ParticipDashController controller = loader.getController();
                controller.initData(currUser);

                titleFl.getScene().setRoot(root);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

}
