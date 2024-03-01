package controllers.siwar;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button donationButton;

    @FXML
    private void initialize() {
        Exit.setOnMouseClicked(event -> System.exit(0));

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> slideMenu(0));
        MenuClose.setOnMouseClicked(event -> slideMenu(-176));

       // donationButton.setOnAction(this::handleDonationButton);
    }

    private void slideMenu(double value) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4), slider);
        slide.setToX(value);
        slide.play();

        slider.setTranslateX(-176);

        slide.setOnFinished((ActionEvent e) -> {
            Menu.setVisible(value == -176);
            MenuClose.setVisible(value == 0);
        });
    }

    public void AddDonation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddDonation.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
