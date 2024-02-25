package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Testimony;
import services.TestimonyService;
import toolkit.MyAnimation;

import java.sql.SQLException;

public class ModifTestimonyController {

    @FXML
    private TextField titleFl;

    @FXML
    private TextField txtFl;

    Testimony t= new Testimony();
    TestimonyService ts = new TestimonyService();

    void initData(Testimony t){
        System.out.println("not in if");
        if (t != null) {
            System.out.println(t);
            this.t = t;
            titleFl.setText(t.getTitle());
            txtFl.setText(t.getTxt());
        }

    }

    @FXML
    void cancelbt(ActionEvent event) {



    }

    @FXML
    void modifTestimonyBt(ActionEvent event) {
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("l 182 modiftestimcontr"+e.getMessage());
            throw new RuntimeException(e);

        }

    }

}
