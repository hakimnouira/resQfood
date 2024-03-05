package test;

import controllers.SearchController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       // load the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addUser.fxml"));
        // load fxml code in a sceen
        Parent root= loader.load();
       Scene scene = new Scene(root);



        // set a scene in stage
        stage.setScene(scene);
        stage.setTitle("add user form");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
