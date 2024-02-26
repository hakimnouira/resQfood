package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Category;
import utils.SiwarDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;

public class CategoryService implements PService<Category> {
    private Connection connection;

    public CategoryService() {
        connection = SiwarDatabase.getInstance().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void create(Category category) throws SQLException {
        String sql = "INSERT INTO dcategory (dcategory_name, dcategory_description) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getDcategory_name());
            ps.setString(2, category.getDcategory_description());
            ps.executeUpdate();
        }
    }

    public void update(Category category) throws SQLException {
        String query = "UPDATE dcategory SET dcategory_name = ?, dcategory_description = ? WHERE dcategory_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getDcategory_name());
            stmt.setString(2, category.getDcategory_description());
            stmt.setInt(3, category.getDcategory_id());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Category updated successfully");
            } else {
                System.out.println("No category updated");
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM dcategory WHERE dcategory_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Category> read(String category) throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM dcategory WHERE dcategory_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category cat = new Category();
                    cat.setDcategory_id(rs.getInt("dcategory_id"));
                    cat.setDcategory_name(rs.getString("dcategory_name"));
                    cat.setDcategory_description(rs.getString("dcategory_description"));
                    categories.add(cat);
                }
            }
        }
        return categories;
    }

    public List<Category> readAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM dcategory";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int categoryId = rs.getInt("dcategory_id");
                String categoryName = rs.getString("dcategory_name");
                String categoryDescription = rs.getString("dcategory_description");

                Category category = new Category(categoryId, categoryName, categoryDescription);
                categories.add(category);
            }
        }
        return categories;
    }
    public int getIdByName(String categoryName) throws SQLException {
        String sql = "SELECT dcategory_id FROM dcategory WHERE dcategory_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dcategory_id");
                }
            }
        }
        // Return -1 if category name not found
        return -1;
    }
}
