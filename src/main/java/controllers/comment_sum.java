package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.Comment;

public class comment_sum {

@FXML
Text content;

@FXML
    Button update;
@FXML
Button delete;
    @FXML
    HBox ban88;
    public HBox getBan88(){
        return ban88;

    }

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
