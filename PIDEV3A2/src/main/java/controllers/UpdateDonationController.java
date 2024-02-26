package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import models.Donation;
import services.DonationService;

import java.sql.SQLException;

public class UpdateDonationController {

    @FXML
    private Label categoryLabel;

    @FXML
    private TextField categoryTextField;

    @FXML
    private Label amountLabel;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label foodNameLabel;

    @FXML
    private TextField foodNameTextField;

    @FXML
    private Label foodQuantityLabel;

    @FXML
    private TextField foodQuantityTextField;

    private Donation selectedDonation;
    private Donation updatedDonation; // Store the updated donation

    @FXML
    public void initialize() {
        // Disable editing of categoryTextField
        categoryTextField.setEditable(false);

        // Add listener to categoryTextField
        categoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check the selected category and update visibility of fields accordingly
            String selectedCategory = newValue.trim().toLowerCase();
            switch (selectedCategory) {
                case "money":
                    // Show amount field
                    amountLabel.setVisible(true);
                    amountTextField.setVisible(true);

                    // Hide food-related fields
                    foodNameLabel.setVisible(false);
                    foodNameTextField.setVisible(false);
                    foodQuantityLabel.setVisible(false);
                    foodQuantityTextField.setVisible(false);
                    break;
                case "rawmaterials":
                case "food":
                    // Show food-related fields
                    foodNameLabel.setVisible(true);
                    foodNameTextField.setVisible(true);
                    foodQuantityLabel.setVisible(true);
                    foodQuantityTextField.setVisible(true);

                    // Hide amount field
                    amountLabel.setVisible(false);
                    amountTextField.setVisible(false);
                    break;
                default:
                    // Hide all fields by default
                    foodNameLabel.setVisible(false);
                    foodNameTextField.setVisible(false);
                    foodQuantityLabel.setVisible(false);
                    foodQuantityTextField.setVisible(false);
                    amountLabel.setVisible(false);
                    amountTextField.setVisible(false);
            }
        });
        // Add a listener to the text property of foodNameTextField
        foodNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z ]*$")) {
                foodNameTextField.setText(newValue.replaceAll("[^a-zA-Z ]", ""));
            }
        });
    }



    public void initData(Donation donation, String category) {
        selectedDonation = donation;
        categoryTextField.setText(category);

        // Set the current values of the selected donation in the text fields
        if (selectedDonation != null) {
            amountTextField.setText(String.valueOf(selectedDonation.getDonation_amount()));
            foodNameTextField.setText(selectedDonation.getFood_name());
            foodQuantityTextField.setText(String.valueOf(selectedDonation.getFood_quantity()));
        }
    }

    @FXML
    void updateDonation(ActionEvent event) {


        try {
            // Get the updated information from the text fields based on the category
            String category = categoryTextField.getText().trim().toLowerCase();
            String foodName = foodNameTextField.getText();
            double foodQuantity = Double.parseDouble(foodQuantityTextField.getText());

            double amount = 0.0;
            if (category.equals("money")) {
                amount = Double.parseDouble(amountTextField.getText());
            }


            // Other parameters for the Donation constructor
            int dCategoryId = selectedDonation.getDcategory_id();
            int uDonorId = selectedDonation.getUdonor_id();

            // Create a new Donation object with the updated information
            updatedDonation = new Donation(selectedDonation.getDonation_id(), category, amount, foodName, foodQuantity, dCategoryId, uDonorId);

            // Close the dialog or view
            categoryTextField.getScene().getWindow().hide();

            // Update the donation in the database
            DonationService donationService = new DonationService();
            donationService.update(updatedDonation);
            System.out.println("Donation updated successfully in the database.");
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric values in quantity or amount fields)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid type.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.err.println("Error updating donation in the database: " + e.getMessage());
        }

    }


    public Donation getUpdatedDonation() {
        return updatedDonation;
    }
}
