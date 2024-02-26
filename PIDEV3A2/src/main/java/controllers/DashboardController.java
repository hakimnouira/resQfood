package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Category;
import models.Donation;
import services.DonationService;
import services.CategoryService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class DashboardController {

    @FXML
    private Button addCategory_addBtn;

    @FXML
    private TextArea addCategory_category_description;

    @FXML
    private TextField addCategory_category_name;

    @FXML
    private Button addCategory_clearBtn;
    @FXML
    private TableColumn<Donation, Integer> DAmountCol1;

    @FXML
    private TableColumn<Donation, String> DCategoryCol1;

    @FXML
    private TableColumn<Donation, Integer> DidCol1;

    @FXML
    private TableColumn<Donation, String> FNameCol1;

    @FXML
    private TableColumn<Donation, Integer> FQuantityCol1;
    @FXML
    private TableColumn<Category, Integer> addCategory_col_category_id;

    @FXML
    private TableColumn<Category, String> addCategory_col_category_name;

    @FXML
    private TableColumn<Category, String> addCategory_col_category_description;
    @FXML
    private TextField addCategory_search;
    @FXML
    private ComboBox<String> sortcategoriesbckComboBox;

    @FXML
    private Button addCategory_deleteBtn;

    @FXML
    private AnchorPane addCategory_form;

    @FXML
    private TableView<Donation> bdonationsTableView;

    @FXML
    private TableView<Category> addCategory_tableView;

    @FXML
    private Button addCategory_updateBtn;
    @FXML
    private AnchorPane board;
    private ObservableList<Donation> originalDonations;
    private CategoryService categoryService;
    private DonationService donationService;

    public DashboardController() {
        this.categoryService = new CategoryService();
        this.donationService = new DonationService();
    }




    @FXML
    public void initialize() {
        // Initialize TableView columns for categories
        addCategory_col_category_id.setCellValueFactory(new PropertyValueFactory<>("dcategory_id"));
        addCategory_col_category_name.setCellValueFactory(new PropertyValueFactory<>("dcategory_name"));
        addCategory_col_category_description.setCellValueFactory(new PropertyValueFactory<>("dcategory_description"));

        // Initialize TableView columns for Donations
        DidCol1.setCellValueFactory(new PropertyValueFactory<>("donation_id"));
        DCategoryCol1.setCellValueFactory(new PropertyValueFactory<>("donation_category"));
        DAmountCol1.setCellValueFactory(new PropertyValueFactory<>("donation_amount"));
        FNameCol1.setCellValueFactory(new PropertyValueFactory<>("food_name"));
        FQuantityCol1.setCellValueFactory(new PropertyValueFactory<>("food_quantity"));

        // Load categories into TableView
        loadCategories();
        // Populate the bdonationsTableView with data
        loadDonations();
        board.setVisible(false);
        // Add a listener to the search TextField
        addCategory_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search();
            }
        });

        // Populate the sort categories combo box
        sortcategoriesbckComboBox.getItems().addAll("Category Name", "Category Description Length");


        // Add a listener to the combo box selection
        sortcategoriesbckComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Sort categories based on the selected criteria
                switch (newValue) {
                    case "Category Name":
                        sortCategoriesByName();
                        break;
                    case "Category Description Length":
                        sortCategoriesByDescriptionLength(addCategory_tableView.getItems());

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void sortCategoriesByName() {
        // Get the current list of categories
        ObservableList<Category> categories = addCategory_tableView.getItems();

        // Sort categories alphabetically based on category name
        categories.sort(Comparator.comparing(Category::getDcategory_name));

        // Set the sorted categories back to the table view
        addCategory_tableView.setItems(categories);
    }

    private void sortCategoriesByDescriptionLength(ObservableList<Category> categories) {
        // Sort categories based on the length of their descriptions
        categories.sort(Comparator.comparingInt(category -> {
            String description = category.getDcategory_description();
            return description != null ? description.length() : 0;
        }));
    }



    @FXML
    void addCategory(ActionEvent event) {
        String categoryName = addCategory_category_name.getText();
        String categoryDescription = addCategory_category_description.getText();

        if (categoryName.isEmpty() || categoryDescription.isEmpty()) {
            showAlert("Error", "Please fill in both category name and description.");
            return;
        }

        try {
            // Check if category with the same name already exists
            List<Category> existingCategories = categoryService.read(categoryName);
            if (!existingCategories.isEmpty()) {
                showAlert("Error", "A category with the same name already exists.");
                return;
            }

            // If no category with the same name exists, create the new category
            Category category = new Category(categoryName, categoryDescription);
            categoryService.create(category);
            showAlert("Success", "Category added successfully.");

            // Update TableView after adding category
            loadCategories();
        } catch (SQLException e) {
            showAlert("Error", "Failed to add category: " + e.getMessage());
        }
    }


    @FXML
    void updateCategory(ActionEvent event) {
        // Get the selected category from the TableView
        Category selectedCategory = addCategory_tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert("Error", "Please select a category to update.");
            return;
        }

        try {
            // Load UpdateCategory.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCategory.fxml"));
            Parent root = loader.load();

            // Initialize the UpdateCategoryController and pass the selected category
            UpdateCategoryController updateCategoryController = loader.getController();
            updateCategoryController.initialize(selectedCategory);

            // Set up the new stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload categories into TableView after update
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load UpdateCategory.fxml");
        }
    }

    private void loadCategories() {
        try {
            List<Category> categories = categoryService.readAll();
            addCategory_tableView.getItems().setAll(categories);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load categories: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }




    @FXML
    void deleteCategory(ActionEvent event) {
        // Get the selected category from the TableView
        Category selectedCategory = addCategory_tableView.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            showAlert("Error", "Please select a category to delete.");
            return;
        }

        // Confirm deletion with user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Category");
        alert.setContentText("Are you sure you want to delete this category?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                try {
                    // Delete category from the database
                    categoryService.delete(selectedCategory.getDcategory_id());

                    // Delete category from the TableView
                    addCategory_tableView.getItems().remove(selectedCategory);

                    showAlert("Success", "Category deleted successfully.");
                } catch (SQLException e) {
                    showAlert("Error", "Failed to delete category: " + e.getMessage());
                }
            }
        });
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void donbck(ActionEvent event) {
        // Toggle the visibility of the board AnchorPane
        board.setVisible(!board.isVisible());
    }
    @FXML
    void searchbck(ActionEvent event) {
        search();
    }

    private void search() {
        String keyword = addCategory_search.getText().toLowerCase(); // Get the keyword from the search field
        // If the keyword is empty, reset the categories table view
        if (keyword.isEmpty()) {
            loadCategories();
            return;
        }

        // Filter donations and set the filtered list to the bdonationsTableView
        ObservableList<Donation> filteredDonations = filterDonations(originalDonations, keyword);
        bdonationsTableView.setItems(filteredDonations);

        // Filter categories and set the filtered list to the addCategory_tableView
        ObservableList<Category> filteredCategories = filterCategories(addCategory_tableView.getItems(), keyword);
        addCategory_tableView.setItems(filteredCategories);
    }

    private ObservableList<Donation> filterDonations(ObservableList<Donation> donations, String keyword) {
        ObservableList<Donation> filteredList = FXCollections.observableArrayList();

        for (Donation donation : donations) {
            // Check if any of the columns in the current row contain the keyword
            if ((donation.getDonation_category() != null && donation.getDonation_category().toLowerCase().contains(keyword)) ||
                    (donation.getFood_name() != null && donation.getFood_name().toLowerCase().contains(keyword)) ||
                    String.valueOf(donation.getDonation_amount()).toLowerCase().contains(keyword) ||
                    String.valueOf(donation.getFood_quantity()).toLowerCase().contains(keyword)) {
                filteredList.add(donation);
            }
        }

        return filteredList;
    }

    private ObservableList<Category> filterCategories(ObservableList<Category> categories, String keyword) {
        ObservableList<Category> filteredList = FXCollections.observableArrayList();

        for (Category category : categories) {
            // Check if any of the columns in the current row contain the keyword
            if ((category.getDcategory_name() != null && category.getDcategory_name().toLowerCase().contains(keyword)) ||
                    (category.getDcategory_description() != null && category.getDcategory_description().toLowerCase().contains(keyword))) {
                filteredList.add(category);
            }
        }

        return filteredList;
    }



    private void loadDonations() {
        try {
            // Retrieve donations data from the database
            List<Donation> donations = donationService.getAllDonations();
            originalDonations = FXCollections.observableArrayList(donations); // Store the original list
            bdonationsTableView.setItems(originalDonations); // Set the data to the bdonationsTableView
        } catch (SQLException e) {
            showAlert("Error", "Failed to load donations: " + e.getMessage());
        }
    }




}