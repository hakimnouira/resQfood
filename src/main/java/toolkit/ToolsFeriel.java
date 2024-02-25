package toolkit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public class ToolsFeriel {


    private ToolsFeriel() {}

    static void goTo(String file, Node node){
        try {
            FXMLLoader loader = new FXMLLoader(ToolsFeriel.class.getResource(file));
            Parent root = loader.load();

            // Get the controller instance

          node.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
