package test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainFX extends Application {
    //double x, y = 0;

   /* @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file with the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddDonation.fxml"));
        //loader.setController(new controllers.AddDonationController());
        Parent root = loader.load();

        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
//sample.fxml scene
        //primaryStage.setScene(new Scene(root, 700, 400));
        //primaryStage.show();
        //dashboard scene:
        primaryStage.setScene(new Scene(root, 1162, 749));
        primaryStage.show();
    }*/
   @Override
   public void start(Stage primaryStage) {
       try {

          FXMLLoader loader = new FXMLLoader(getClass().getResource("/designation.fxml"));
          //FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
           Parent root = loader.load();
           Scene scene = new Scene(root);
           primaryStage.setScene(scene);
           primaryStage.show();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }



    public static void main(String[] args) {
        launch(args);
    }
}
