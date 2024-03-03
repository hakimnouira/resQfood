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

public class ModifTestimonyController extends Controller {

    @FXML
    private TextField titleFl;

    @FXML
    private TextField txtFl;

    Testimony t= new Testimony();
    User currUser=new User();
    TestimonyService ts = new TestimonyService();

    void initData(Testimony t,User user){
        System.out.println("not in if");
        if (t != null) {
            System.out.println(t);
            this.t = t;
            titleFl.setText(t.getTitle());
            txtFl.setText(t.getTxt());
            currUser=user;
        }

    }

    @FXML
    void cancelbt(ActionEvent event) {
        //TODO / MODIF INIT DATA PR QU4ELLE PRENNE CURR PR QUE CANCEL MARCHE


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





    @FXML
    void modifTestimonyBt(ActionEvent event) {
        if (titleFl.getText().isEmpty() || !titleFl.getText().matches("^[a-zA-Z]+$")) {
            showInputIncorect(titleFl);
            return;
        }
        if (txtFl.getText().isEmpty()) {
            showInputIncorect(txtFl);
            return;
        }

        try {
            System.out.println(t.getUserId());
            t.setUserId(t.getUserId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        t.setTitle(titleFl.getText());
        t.setTxt(txtFl.getText());


        try{
            ts.update(t);
            MyTools.showAlertInfo("Success","User added successfully");
            cancelbt(event);
        } catch (SQLException e) {
           // System.out.println("l 182 modiftestimcontr"+e.getMessage());
            throw new RuntimeException(e);

        }

    }

}
