package services;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Bans;
import models.Post;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class banService {
    private static Connection connection = MyDatabase.getInstance().getConnection();

    private static final MyDatabase database = MyDatabase.getInstance();

    // Example method to retrieve user information from the database
    public static Bans getUserInfo(int userId) {
        Bans userInfo = new Bans();
        String query = "SELECT * FROM ban WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {


                    userInfo.setBannedBy( resultSet.getInt("BannedBy"));
                    userInfo.setUserID( resultSet.getInt("UserID"));
                    userInfo.setBanReason( resultSet.getString("username"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    // Example method to ban a user
    public static void banUser(int userId, int bannedBy, String banReason) {
        String query = "INSERT INTO ban (UserID, BannedBy, BanReason) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bannedBy);
            preparedStatement.setString(3, banReason);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
