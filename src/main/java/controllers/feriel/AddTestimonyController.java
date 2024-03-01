package controllers.feriel;

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

import java.io.IOException;
import java.sql.SQLException;

public class AddTestimonyController {
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
       // assert user !=null;
        currUser = user;
    }

    @FXML
    void addTestimonyBt(ActionEvent event) {
        //TODO: ds displayTest d'admin rajouter bouton pour accepter les testim

        if (titleFl.getText().isEmpty() || !titleFl.getText().matches("^[a-zA-Z]+$")) {
            titleFl.requestFocus();
            MyAnimation.shake(titleFl);

            return;
        }
        if (txtFl.getText().isEmpty()) {
            txtFl.requestFocus();
            MyAnimation.shake(txtFl);

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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/DisplayTestimonies.fxml"));
                Parent root = loader.load();
                titleFl.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println("error" + e.getMessage());
            }

        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fereil/ParticipDash.fxml"));
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
