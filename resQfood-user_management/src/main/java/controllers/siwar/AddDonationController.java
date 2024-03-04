package controllers.siwar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
//import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import models.siwar.Category;
import models.siwar.Donation;
import models.siwar.User;
import javafx.scene.image.ImageView;
import services.siwar.DonationService;
import services.siwar.EmailService;
import services.siwar.UserService;
import services.siwar.CategoryService;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.mail.*;
import javafx.scene.control.Alert;
import utils.ChatGPTController;


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

    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextArea chatDisplayArea;


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
                showAlert("Error", "Please select a category first.", AlertType.ERROR);
                return; // Exit the method if no category is selected
            }

            double donationAmount = 0; // Default value

            // Check if the category is Money and validate the amount field
            if (donationCategory.equals("Money")) {
                if (moneyamountid.getText().isEmpty()) {
                    showAlert("Error", "Please enter the donation amount.", AlertType.ERROR);
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
                    showAlert("Error", "Please enter the food name.", AlertType.ERROR);
                    return; // Exit the method if the food name field is empty
                }
                if (quantityfoodid.getText().isEmpty()) {
                    showAlert("Error", "Please enter the food quantity.", AlertType.ERROR);
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


            // Generate HTML content for the email body
            // Generate HTML content for the email body
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Email Confirmation</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        h1 {\n" +
                    "            color: #083A39;\n" +
                    "            font-size: 24px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "\n" +
                    "        p {\n" +
                    "            font-size: 16px;\n" +
                    "            line-height: 1.5;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "\n" +
                    "        .button {\n" +
                    "            background-color: #F9EFF0;\n" +
                    "            color: #fff;\n" +
                    "            padding: 10px 20px;\n" +
                    "            border: none;\n" +
                    "            border-radius: 5px;\n" +
                    "            cursor: pointer;\n" +
                    "            display: block;\n" +
                    "            margin: 20px auto;\n" +
                    "            text-decoration: none; /* Remove default underline */\n" +
                    "            text-align: center; /* Center the text */\n" +
                    "        }\n" +
                    "\n" +
                    "        .container {\n" +
                    "            background-color: #fff;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 5px;\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            position: relative; /* Add relative positioning to the container */\n" +
                    "        }\n" +
                    "\n" +
                    "        .image-container {\n" +
                    "            position: absolute; /* Set image container to absolute position */\n" +
                    "            top: 0; /* Align to the top of the container */\n" +
                    "            left: 0; /* Align to the left of the container */\n" +
                    "            width: 100%; /* Occupy the full width of the container */\n" +
                    "            text-align: center; /* Center the content horizontally */\n" +
                    "        }\n" +
                    "\n" +
                    "        img {\n" +
                    "            width: 100%; /* Set to whatever percentage you want */\n" +
                    "            max-width: 300px; /* Set the maximum width */\n" +
                    "            height: auto; /* Maintain aspect ratio */\n" +
                    "            display: block;\n" +
                    "            margin: auto;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"image-container\">\n" +
                    "            <img src=\"https://cdn.dribbble.com/users/1551941/screenshots/6346538/thankyoudribble.gif\" alt=\"Your Image\">\n" +
                    "        </div>\n" +
                    "        <h1>Email Confirmation</h1>\n" +
                    "        <p>Dear Donor,</p>\n" +
                    "        <p>We extend our heartfelt gratitude for your remarkable generosity, which has brought light and hope to those in need.\n" +
                    "\n" +
                    "Your thoughtful donation not only sustains our mission but also inspires our team to continue striving for a brighter, more equitable future for all.</p>\n" +
                    "        <p><a href=\"#\" class=\"button\" >You can find below a file containing the list of donations</a> <a href=\"file:///C:/Users/MSI/Desktop/3eme année ESPRIT/Semestre 2/Pidev/resQfood-user_management/resQfood-user_management/Donations List.pdf\"\"></a></p>\n" +



                    "        <p>With profound appreciation,</p>\n" +
                    "        <p>The ResQFood Team</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";










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
                String pdfFilePath = "C:/Users/MSI/Desktop/3eme année ESPRIT/Semestre 2/Pidev/Donations List.pdf";
                try {
                    EmailService.sendEmailWithAttachmentAndHTML(donorEmail, subject, htmlContent, pdfFilePath);

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to send email: " + e.getMessage(), AlertType.ERROR);
                }
            }

            // Show a success message with an information icon
            showAlert("Success", "Donation added successfully,check your email.", AlertType.INFORMATION);

            // Insert a notification for adding a donation
            //String message = "A donation was added";
            // NotificationService notificationService = new NotificationService();
            // notificationService.insertNotification(message);

            // Load ShowDonation.fxml
            FXMLLoader showDonationLoader = new FXMLLoader(getClass().getResource("/siwar/ShowDonation.fxml"));
            Parent showDonationRoot = showDonationLoader.load();
            double preferredWidth = 873.0; // Adjust this value according to your FXML
            double preferredHeight = 585.0; // Adjust this value according to your FXML
            Scene scene = new Scene(showDonationRoot, preferredWidth, preferredHeight);
            ShowDonationController showDonationController = showDonationLoader.getController();
            showDonationController.initialize(1); // Assuming udonor_id is 1
            //Scene scene = new Scene(showDonationRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numeric values for amount and quantity.", AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage(), AlertType.ERROR);
        } catch (MessagingException | UnsupportedEncodingException e) {
            showAlert("Error", "Failed to send email: " + e.getMessage(), AlertType.ERROR);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/siwar/designation.fxml"));
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



    private void showAlert(String title, String message, AlertType alertType) {
        Alert.AlertType javafxAlertType;
        switch (alertType) {
            case ERROR:
                javafxAlertType = Alert.AlertType.ERROR;
                break;
            case INFORMATION:
                javafxAlertType = Alert.AlertType.INFORMATION;
                break;
            default:
                throw new IllegalArgumentException("Unsupported alert type: " + alertType);
        }

        Alert alert = new Alert(javafxAlertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /*private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/




    @FXML
    void showChatGPTWindow(ActionEvent event) {
        ChatGPTController.showChatGPTWindow();
    }





    public void closeChatWindow(ActionEvent actionEvent) {
    }
}