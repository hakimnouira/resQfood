package controllers.siwar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Category;
import services.siwar.CategoryService;

import java.sql.SQLException;

public class UpdateCategoryController {

    @FXML
    private TextField updateCategory_category_name;

    @FXML
    private TextArea updateCategory_category_description;

    private CategoryService categoryService;
    private Category selectedCategory;

    public void initialize(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
        this.categoryService = new CategoryService();

        // Set the existing category details in the form
        updateCategory_category_name.setText(selectedCategory.getDcategory_name());
        updateCategory_category_description.setText(selectedCategory.getDcategory_description());
    }

    @FXML
    void update(ActionEvent event) {
        // Get the updated values from the form
        String updatedName = updateCategory_category_name.getText();
        String updatedDescription = updateCategory_category_description.getText();

        // Check if any of the fields is empty
        if (updatedName.isEmpty() || updatedDescription.isEmpty()) {
            showAlert("Error", "Please fill in both category name and description.");
            return;
        }

        // Update the selected category
        Category updatedCategory = new Category(
                selectedCategory.getDcategory_id(),
                updatedName,
                updatedDescription
        );

        try {
            // Update category in the database
            categoryService.update(updatedCategory);

            // Show success message
            showAlert("Success", "Category updated successfully.");

            // Close the UpdateCategory.fxml window
            updateCategory_category_name.getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace
            showAlert("Error", "Failed to update category: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
