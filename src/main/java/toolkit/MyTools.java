package toolkit;

import controllers.Controller;
import controllers.feriel.ModifUserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import models.feriel.User;

import java.io.IOException;

public class MyTools {


    private MyTools() {}

    public static void goTo(String file, Node node){
        try {
            FXMLLoader loader = new FXMLLoader(MyTools.class.getResource(file));
            Parent root = loader.load();

            // Get the controller instance
            if (node.getScene() != null) {
                node.getScene().setRoot(root);
            }else {
                System.err.println("Scene is null, cannot navigate.");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void showAlertError(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static void showAlertInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void goTop(String file, Node node, User user, Controller c){

        try {
        FXMLLoader loader = new FXMLLoader(MyTools.class.getResource("/feriel/ModifUser.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        Controller controller = loader.getController();
        controller.initSelectedUser(user);

        node.getScene().setRoot(root);

    } catch (IOException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException(e);
    }
    }
}
