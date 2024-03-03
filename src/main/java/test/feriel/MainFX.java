package test.feriel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       // load the fxml file
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/feriel/LogIn.fxml"));
      // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fatma/Showevent.fxml"));

        //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDash.fxml"));
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifUser.fxml"));
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayUsers.fxml"));
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTestimony.fxml"));
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddNewuser.fxml"));
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParticipViewTestim.fxml"));
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayTestimonies.fxml"));

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgottenPwd.fxml"));


        // load fxml code in a sceen
        Parent root= loader.load();
        // put the fxml file in a sceene
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
