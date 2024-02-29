package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Donation;
import org.controlsfx.control.Notifications;
import services.DonationService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Comparator;

public class ShowDonationController{

    @FXML
    private TableView<Donation> donationsTableView;

    @FXML
    private TableColumn<Donation, String> DCategoryCol;

    @FXML
    private TableColumn<Donation, Double> DAmountCol;

    @FXML
    private TableColumn<Donation, String> FNameCol;

    @FXML
    private TableColumn<Donation, Double> FQuantityCol;

    @FXML
    private Button Backb;

    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> sortDonationComboBox;

    private final DonationService donationService = new DonationService();
    private ObservableList<Donation> originalDonations; // Maintain a reference to the original list
    private int udonor_id;

    public void initialize(int udonor_id) {
        this.udonor_id = udonor_id;
        // Initialize TableView columns
        DCategoryCol.setCellValueFactory(cellData -> cellData.getValue().donation_categoryProperty());
        DAmountCol.setCellValueFactory(cellData -> cellData.getValue().donation_amountProperty().asObject());
        FNameCol.setCellValueFactory(cellData -> cellData.getValue().food_nameProperty());
        FQuantityCol.setCellValueFactory(cellData -> cellData.getValue().food_quantityProperty().asObject());

        populateTableView();
        originalDonations = donationsTableView.getItems(); // Store the original list
        // Add a listener to the search text field to update the table view as the user types
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData(newValue.toLowerCase());
        });
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Trigger the search action whenever the text changes
            search();
        });
        sortDonationComboBox.getItems().addAll("Category", "Amount");

        // Set a default value for the ComboBox
        sortDonationComboBox.setValue("Category");

        // Add a listener to the ComboBox to handle sorting
        sortDonationComboBox.setOnAction(event -> {
            String selectedOption = sortDonationComboBox.getValue();
            switch (selectedOption) {
                case "Category":
                    sortTableViewByCategory();
                    break;
                case "Amount":
                    sortTableViewByAmount();
                    break;
            }
        });
        showNotification();
    }
    @FXML
    void SearchBtn(ActionEvent event) {
        search();
    }
    private void search() {
        String keyword = searchTextField.getText().toLowerCase();
        ObservableList<Donation> filteredList = FXCollections.observableArrayList();
        boolean found = false;

        for (Donation donation : originalDonations) { // Iterate over the original list
            // Check if any of the columns in the current row contain the keyword
            if ((donation.getDonation_category() != null && donation.getDonation_category().toLowerCase().contains(keyword)) ||
                    (donation.getFood_name() != null && donation.getFood_name().toLowerCase().contains(keyword)) ||
                    String.valueOf(donation.getDonation_amount()).toLowerCase().contains(keyword) ||
                    String.valueOf(donation.getFood_quantity()).toLowerCase().contains(keyword)) {
                filteredList.add(donation);
                found = true;
            }
        }

        if (found) {
            donationsTableView.setItems(filteredList); // Set the filtered list
        } else {
            // If no rows containing the keyword are found, reset the table view to display the original list
            donationsTableView.setItems(originalDonations);
        }
    }

    private void populateTableView() {
        try {
            List<Donation> donations = donationService.getDonationsByDonorId(udonor_id);
            ObservableList<Donation> donationObservableList = FXCollections.observableArrayList(donations);
            donationsTableView.setItems(donationObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void removeDonation(ActionEvent actionEvent) {
        Donation selectedDonation = donationsTableView.getSelectionModel().getSelectedItem();
        if (selectedDonation != null) {
            try {
                donationService.delete(selectedDonation.getDonation_id());
                // Remove the selected donation from the observable list
                donationsTableView.getItems().remove(selectedDonation);
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error removing donation from the database: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a donation to remove.");
            alert.showAndWait();
        }
    }


    @FXML
    void updateDonation(ActionEvent event) {
        Donation selectedDonation = donationsTableView.getSelectionModel().getSelectedItem();
        if (selectedDonation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateDonation.fxml"));
                Parent root = loader.load();
                UpdateDonationController controller = loader.getController();
                controller.initData(selectedDonation, selectedDonation.getDonation_category());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Donation");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                Donation updatedDonation = controller.getUpdatedDonation();
                int index = donationsTableView.getItems().indexOf(selectedDonation);
                if (index != -1) {
                    donationsTableView.getItems().set(index, updatedDonation);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a donation to update.");
            alert.showAndWait();
        }
    }

    @FXML
    void BackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFilteredData(String filter) {
        FilteredList<Donation> filteredData = new FilteredList<>(originalDonations, p -> true);

        // Set the predicate to filter the data based on the current text in the search field
        filteredData.setPredicate(donation -> {
            // If filter text is empty, display all rows
            if (filter == null || filter.isEmpty()) {
                return true;
            }

            // Check if any of the columns in the current row contain the filter text
            return donation.getDonation_category().toLowerCase().contains(filter) ||
                    donation.getFood_name().toLowerCase().contains(filter) ||
                    String.valueOf(donation.getDonation_amount()).toLowerCase().contains(filter) ||
                    String.valueOf(donation.getFood_quantity()).toLowerCase().contains(filter);
        });

        // Update the table view with the filtered data
        donationsTableView.setItems(filteredData);
    }
    private void sortTableViewByCategory() {
        // Create a sorted list based on the TableView's items
        SortedList<Donation> sortedList = new SortedList<>(donationsTableView.getItems());

        // Set the comparator to sort by category name
        sortedList.setComparator(Comparator.comparing(Donation::getDonation_category));

        // Bind the sorted list to the TableView
        donationsTableView.setItems(sortedList);
    }
    private void sortTableViewByAmount() {
        // Create a sorted list based on the TableView's items
        SortedList<Donation> sortedList = new SortedList<>(donationsTableView.getItems());

        // Set the comparator to sort by donation amount
        sortedList.setComparator(Comparator.comparingDouble(Donation::getDonation_amount));

        // Bind the sorted list to the TableView
        donationsTableView.setItems(sortedList);
    }
    @FXML
    void backtoaddD(ActionEvent event) {
        try {
            // Load AddDonation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddDonation.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene from AddDonation.fxml to the current stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if unable to load AddDonation.fxml
        }
    }
    @FXML
    public void NotifBtn(ActionEvent actionEvent) {
        try {
            Image image = new Image("/Imagesdesignf/notiif.png");

            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Donation added successfully");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNotification() {
        try {
            Image image = new Image("/Imagesdesignf/notiif.png");

            Notifications notifications = Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Donation added successfully");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            notifications.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
