package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import models.Category;
import models.Donation;
import services.CategoryService;
import services.DonationService;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddDonationController implements Initializable {

    @FXML
    private Button AddB;

    @FXML
    private Button Backb;

    @FXML
    private Label amountid;

    @FXML
    private ComboBox<String> comb;

    @FXML
    private Label fnameid;

    @FXML
    private Label fquantityid;

    @FXML
    private TextField moneyamountid;

    @FXML
    private TextField namefoodid;

    @FXML
    private TextField quantityfoodid;
    @FXML
    private ImageView fimageamountid;

    @FXML
    private ImageView fimagequantityid;
    @FXML
    private ImageView fnameimageid;

     private CategoryService categoryservice = new CategoryService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ObservableList<String> list;
        list = FXCollections.observableArrayList("Money", "RawMaterials", "Food");
        comb.setItems(list);*/

        try {
            List<Category> liste = categoryservice.readAll();

            // Créer une liste de noms d'équipes
            List<String> categories = new ArrayList<>();
            for (Category category : liste) {
                categories.add(category.getDcategory_name());
            }

            // Créer une observable list à partir de la liste de noms de categories
            ObservableList<String> observableNomEquipes = FXCollections.observableArrayList(categories);

            // Ajouter la liste de noms categories au ComboBox
            comb.setItems(observableNomEquipes);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Add listener to namefoodid textProperty
        namefoodid.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z ]*$")) {
                // If the input does not match the specified pattern, revert to the old value
                namefoodid.setText(oldValue);
            }
        });
    }
/*
    }*/
    @FXML
    void AddDonationButton(ActionEvent event) {
        try {
            // Validate input
            String donationCategory = comb.getSelectionModel().getSelectedItem().toString();
            double donationAmount = 0; // Default value

            // Check if the category is Money and validate the amount field
            if (donationCategory.equals("Money")) {
                if (moneyamountid.getText().isEmpty()) {
                    showAlert("Error", "Please enter the donation amount.");
                    return; // Exit the method if the amount field is empty
                }
                donationAmount = Double.parseDouble(moneyamountid.getText());
            }

            // For other categories (e.g., Raw Materials or Food), additional fields are needed
            String foodName = namefoodid.getText();
            double foodQuantity = 0; // Default value

            // Validate the name and quantity fields for Food and Raw Materials categories
            if (!donationCategory.equals("Money")) {
                if (foodName.isEmpty()) {
                    showAlert("Error", "Please enter the food name.");
                    return; // Exit the method if the food name field is empty
                }
                if (quantityfoodid.getText().isEmpty()) {
                    showAlert("Error", "Please enter the food quantity.");
                    return; // Exit the method if the quantity field is empty
                }
                foodQuantity = Double.parseDouble(quantityfoodid.getText());
            }

            int categoryId= categoryservice.getIdByName(donationCategory);
            // Create a new Donation object
            Donation donation = new Donation(donationCategory, donationAmount, foodName, foodQuantity, categoryId, 1); // Assuming 2 is the ID for the "Food" category

            // Save the donation to the database
            DonationService donationService = new DonationService();
            donationService.create(donation);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Thank you for your donation!");
            alert.showAndWait();

            // Load ShowDonation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowDonation.fxml"));
            Parent root = loader.load();
            ShowDonationController controller = loader.getController();
            controller.initialize(1); // Assuming udonor_id is 1
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException | NumberFormatException | SQLException e) {
            // Show error message for invalid input or database error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid input or error adding donation to the database: " + e.getMessage());
            alert.showAndWait();
        }
    }



    @FXML
    void BackButton(ActionEvent event) {
        try {
            // Load designation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designation.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene from designation.fxml to the current stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void Select(ActionEvent event) {
        String selectedValue = comb.getSelectionModel().getSelectedItem().toString();

        if (selectedValue.equals("Money")) {
            amountid.setVisible(true);
            moneyamountid.setVisible(true);
            fimageamountid.setVisible(true);


            fnameid.setVisible(false);
            namefoodid.setVisible(false);
            fquantityid.setVisible(false);
            quantityfoodid.setVisible(false);
            fimagequantityid.setVisible(false);
            fnameimageid.setVisible(false);

        } else {
            amountid.setVisible(false);
            moneyamountid.setVisible(false);
            fimageamountid.setVisible(false);

            fnameid.setVisible(true);
            namefoodid.setVisible(true);
            fquantityid.setVisible(true);
            quantityfoodid.setVisible(true);
            fimagequantityid.setVisible(true);
            fnameimageid.setVisible(true);
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}