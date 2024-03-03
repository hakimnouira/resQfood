package controllers.hakim;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.hakim.Post;

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

    void initialize(models.hakim.Comment c) {
        messageTextArea.setText(c.getContent());





    }

public Button get_submit(){
    return submit;

}



    public String get_submit_information() {
        String message = messageTextArea.getText().trim(); // Trim to remove leading and trailing whitespaces

        if (message.isEmpty()) {
            // Display an alert indicating that the message is required
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a message.");
            alert.showAndWait();

            // Return null or handle the error as needed in your application
            return null;
        } else {
            return message;
        }
    }




}
