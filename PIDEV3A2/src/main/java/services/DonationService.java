package services;

import models.Donation;
import utils.SiwarDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonationService implements IService<Donation> {
    private Connection connection;

    public DonationService() {
        connection = SiwarDatabase.getInstance().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void create(Donation donation) throws SQLException {
        String sql = "INSERT INTO donation (donation_category, donation_amount, food_name, food_quantity, dcategory_id, udonor_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donation.getDonation_category());
            ps.setDouble(2, donation.getDonation_amount());
            ps.setString(3, donation.getFood_name());
            ps.setDouble(4, donation.getFood_quantity());
            ps.setInt(5, donation.getDcategory_id());
            ps.setInt(6, donation.getUdonor_id());
            ps.executeUpdate();
        }
    }
    @Override
    public void update(Donation donation) throws SQLException {
        String sql = "UPDATE donation SET donation_category = ?, donation_amount = ?, food_name = ?, food_quantity = ? WHERE donation_id = ?";
        System.out.println("SQL: " + sql);
        System.out.println("Parameters: " + donation.getDonation_category() + ", " + donation.getDonation_amount() + ", " + donation.getFood_name() + ", " + donation.getFood_quantity() + ", " + donation.getDonation_id());
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donation.getDonation_category());
            ps.setDouble(2, donation.getDonation_amount());
            ps.setString(3, donation.getFood_name());
            ps.setDouble(4, donation.getFood_quantity());
            ps.setInt(5, donation.getDonation_id());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Donation updated successfully in the database.");
            } else {
                System.err.println("No rows affected. Donation update may have failed.");
            }
        } catch (SQLException e) {
            // Log the exception message
            System.err.println("Error updating donation: " + e.getMessage());
            throw e; // Re-throw the exception to handle it in the calling code
        }
    }



    @Override
    public void delete(int donationId) throws SQLException {
        String sql = "DELETE FROM donation WHERE donation_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, donationId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Donation> readDonationsByCategory(String category) throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donation WHERE donation_category = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation();
                    donation.setDonation_id(rs.getInt("donation_id"));
                    donation.setDonation_category(rs.getString("donation_category"));
                    donation.setDonation_amount(rs.getDouble("donation_amount"));
                    donation.setFood_name(rs.getString("food_name"));
                    donation.setFood_quantity(rs.getDouble("food_quantity"));
                    donation.setDcategory_id(rs.getInt("dcategory_id"));
                    donation.setUdonor_id(rs.getInt("udonor_id"));
                    donations.add(donation);
                }
            }
        }
        return donations;
    }

    public List<Donation> getAllDonations() throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donation";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation();
                    donation.setDonation_id(rs.getInt("donation_id"));
                    donation.setDonation_category(rs.getString("donation_category"));
                    donation.setDonation_amount(rs.getDouble("donation_amount"));
                    donation.setFood_name(rs.getString("food_name"));
                    donation.setFood_quantity(rs.getDouble("food_quantity"));
                    donation.setDcategory_id(rs.getInt("dcategory_id"));
                    donation.setUdonor_id(rs.getInt("Udonor_id"));
                    donations.add(donation);
                }
            }
        }
        return donations;
    }


    public Donation getDonationByCategoryAndDonorId(String category, int udonorId) throws SQLException {
        Donation donation = null;
        String sql = "SELECT * FROM donation WHERE donation_category = ? AND udonor_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category);
            ps.setInt(2, udonorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    donation = new Donation();
                    donation.setDonation_id(rs.getInt("donation_id"));
                    donation.setDonation_category(rs.getString("donation_category"));
                    donation.setDonation_amount(rs.getDouble("donation_amount"));
                    donation.setFood_name(rs.getString("food_name"));
                    donation.setFood_quantity(rs.getDouble("food_quantity"));
                    donation.setDcategory_id(rs.getInt("dcategory_id"));
                    donation.setUdonor_id(rs.getInt("udonor_id"));
                }
            }
        }
        return donation;
    }

    public List<Donation> getDonationsByDonorId(int udonorId) throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String query = "SELECT * FROM donation WHERE udonor_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, udonorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Donation donation = new Donation(
                            resultSet.getInt("donation_id"),
                            resultSet.getString("donation_category"),
                            resultSet.getDouble("donation_amount"),
                            resultSet.getString("food_name"),
                            resultSet.getDouble("food_quantity"),
                            resultSet.getInt("dcategory_id"),
                            resultSet.getInt("udonor_id")
                    );
                    donations.add(donation);
                }
            }
        }
        return donations;
    }
   /* public List<Donation> searchDonations(String searchQuery) throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donation WHERE donation_category LIKE ? OR donation_amount LIKE ? OR food_name LIKE ? OR food_quantity LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the search query parameters
            String likeQuery = "%" + searchQuery + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, likeQuery);
            }

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation();
                    donation.setDonation_id(rs.getInt("donation_id"));
                    donation.setDonation_category(rs.getString("donation_category"));
                    donation.setDonation_amount(rs.getDouble("donation_amount"));
                    donation.setFood_name(rs.getString("food_name"));
                    donation.setFood_quantity(rs.getDouble("food_quantity"));
                    donation.setDcategory_id(rs.getInt("dcategory_id"));
                    donation.setUdonor_id(rs.getInt("udonor_id"));
                    donations.add(donation);
                }
            }
        }
        return donations;
    }*/



}