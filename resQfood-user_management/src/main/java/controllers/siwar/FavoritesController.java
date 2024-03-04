package controllers.siwar;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.siwar.Category;
import javafx.collections.ObservableList;

public class FavoritesController {
    private final ObservableList<Category> favorites = FXCollections.observableArrayList();

    @FXML
    private TableView<Category> favoritesTableView;

    public void initialize() {
        // Initialize TableView columns
        TableColumn<Category, String> categoryNameColumn = new TableColumn<>("Category Name");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("dcategory_name"));

        TableColumn<Category, String> categoryDescriptionColumn = new TableColumn<>("Category Description");
        categoryDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("dcategory_description"));

        // Add columns to the TableView
        favoritesTableView.getColumns().addAll(categoryNameColumn, categoryDescriptionColumn);
    }

    public void setFavorites(ObservableList<Category> favorites) {
        // Set the data to display in the TableView
        favoritesTableView.setItems(favorites);
    }

    public void addToFavorites(Category category) {
        // Add category to favorites list
        favorites.add(category);
        favoritesTableView.getItems().add(category);
    }
}
