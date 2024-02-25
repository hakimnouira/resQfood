package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Post;

import java.sql.SQLException;
import java.util.List;

public class Comment {


@FXML
 public TextField messageTextArea;
@FXML
   public Button update;
@FXML
public Button delete ;

@FXML
public Button submit;

public void set_update(){
    update.setVisible(true);


}

public Button get_button_update(){


    return update;
}
    public Button get_button_delete(){
return delete;


    }

    void initialize(models.Comment c) {
        messageTextArea.setText(c.getContent());





    }

public Button get_submit(){
    return submit;

}



public String get_submit_information(){
    return  messageTextArea.getText();

}



}
