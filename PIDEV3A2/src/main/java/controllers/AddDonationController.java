package controllers;
import com.mysql.cj.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.*;

import java.io.IOException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
import models.User;
import javafx.scene.image.ImageView;
import services.UserService;

import java.io.*;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.text.Document;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


public class AddDonationController implements Initializable  {

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
    private final UserService userService = new UserService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ObservableList<String> list;
        list = FXCollections.observableArrayList("Money", "RawMaterials", "Food");
        comb.setItems(list);*/

        try {
            List<Category> liste = categoryservice.readAll();

            // Créer une liste de noms categories
            List<String> categories = new ArrayList<>();
            for (Category category : liste) {
                categories.add(category.getDcategory_name());
            }

            // Créer une observable list à partir de la liste de noms de categories
            ObservableList<String> observablecategories = FXCollections.observableArrayList(categories);

            // Ajouter la liste de noms categories au ComboBox
            comb.setItems(observablecategories);


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
        String donationCategory = comb.getSelectionModel().getSelectedItem(); // Get selected category
        if (donationCategory == null) {
            showAlert("Error", "Please select a category first.");
            return; // Exit the method if no category is selected
        }

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

        // Get the donor's ID
        int donorId = 1; // Assuming donor_id is 1
        // Get the category ID
        int categoryId = categoryservice.getIdByName(donationCategory);

        // Create a new Donation object
        Donation donation = new Donation(donationCategory, donationAmount, foodName, foodQuantity, categoryId, donorId); // Assuming donor_id is 1

        // Save the donation to the database
        DonationService donationService = new DonationService();
        donationService.create(donation);

        // Generate PDF containing the list of donations
        //String pdfFilePath = generateDonationListPDF(donorId);

        // Get the user service
        UserService userService = new UserService();

// Retrieve all users with the role "Donor"
        List<User> donorUsers = userService.getUsersByRole("Donor");


        // Send emails to each donor
        for (User donor : donorUsers) {
            String donorEmail = donor.getEmail();
            String subject = "Thank you for your donation!";
            String message = "Dear Donor,\n\nThank you for your generous donation.\n\nSincerely,\nResQFood Team";
            // Assuming the attachment file path is the same for all donors
            String pdfFilePath ="C:/Users/MSI/Desktop/3eme année ESPRIT/Semestre 2/Pidev/Donations List.pdf";
            try {
                EmailService.sendEmailWithAttachment(donorEmail, subject, message, pdfFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to send email: " + e.getMessage());
            }
        }

        // Show a success message
        showAlert("Success", "Donation added successfully and email sent to donors.");

        // Insert a notification for adding a donation
       // String message = "A donation was added";
        //NotificationService notificationService = new NotificationService();
        //notificationService.insertNotification(message);

        // Load ShowDonation.fxml
        FXMLLoader showDonationLoader = new FXMLLoader(getClass().getResource("/ShowDonation.fxml"));
        Parent showDonationRoot = showDonationLoader.load();
        ShowDonationController showDonationController = showDonationLoader.getController();
        showDonationController.initialize(1); // Assuming udonor_id is 1
        Scene scene = new Scene(showDonationRoot);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (NumberFormatException e) {
        showAlert("Error", "Please enter valid numeric values for amount and quantity.");
    } catch (SQLException e) {
        showAlert("Error", "Database error: " + e.getMessage());
    } catch (MessagingException | UnsupportedEncodingException e) {
        showAlert("Error", "Failed to send email: " + e.getMessage());
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}


    private String generateDonationListPDF(int donorId) {
        // Create an instance of DonationService
        DonationService donationService = new DonationService();

        try {
            // Call the non-static method to retrieve donations by donorId
            List<Donation> donations = donationService.getDonationsByDonorId(donorId);

            // Now you have the list of donations, generate the PDF
            String pdfFilePath = "donation_list.pdf";
            // Use a PDF library (e.g., iText) to create a PDF document and populate it with donation information
            // Example:
            // PDFGenerator.generateDonationListPDF(donations, pdfFilePath);

            // For demonstration purposes, let's assume the PDF file is created successfully
            return pdfFilePath;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return null;
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