package controllers;

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
import services.CategoryService;
import services.DonationService;
import java.io.IOException;
import java.sql.SQLException;
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
    private Button addCategory_deleteBtn;

    @FXML
    private AnchorPane addCategory_form;

    @FXML
    private TextField addCategory_search;
    @FXML
    private TableView<Donation> bdonationsTableView;

    @FXML
    private TableView<Category> addCategory_tableView;

    @FXML
    private Button addCategory_updateBtn;

    private CategoryService categoryService;

    public DashboardController() {
        this.categoryService = new CategoryService();
        this.donationService = new DonationService();
    }




    @FXML
    public void initialize() {
        // Initialize TableView columns
        addCategory_col_category_id.setCellValueFactory(new PropertyValueFactory<>("dcategory_id"));
        addCategory_col_category_name.setCellValueFactory(new PropertyValueFactory<>("dcategory_name"));
        addCategory_col_category_description.setCellValueFactory(new PropertyValueFactory<>("dcategory_description"));

        // Load categories into TableView
        loadCategories();
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

}