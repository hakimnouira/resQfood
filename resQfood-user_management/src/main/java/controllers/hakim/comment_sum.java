package controllers.hakim;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import models.hakim.Comment;

public class comment_sum {

@FXML
Text content;

@FXML
    Button update;
@FXML
Button delete;


    void initialize(Comment c ) {

content.setText(c.getContent());
content.setWrappingWidth(200);
    }


public String get_content(){

        return  content.getText();
}
public Button get_update_button(){return update;}
    public Button get_delete_button(){return delete;}





}
