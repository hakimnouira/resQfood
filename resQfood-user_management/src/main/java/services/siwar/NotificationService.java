/*package services;

import models.Notification;
import utils.SiwarDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private  Connection connection;
    public NotificationService() {
        connection = SiwarDatabase.getInstance().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public NotificationService(Connection connection) {
        this.connection = connection;
    }

    public void insertNotification(String message) throws SQLException {
        String sql = "INSERT INTO notifications (message_notif, timestamp) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, message);
            statement.setObject(2, LocalDateTime.now()); // Current timestamp
            statement.executeUpdate();
        }
    }

    public List<Notification> getNotifications() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_notif");
                String message = resultSet.getString("message_notif");
                LocalDateTime timestamp = resultSet.getObject("timestamp", LocalDateTime.class);
                Notification notification = new Notification(id, message, timestamp);
                notifications.add(notification);
            }
        }
        return notifications;
    }

    public void markNotificationsAsRead(List<Integer> notificationIds) throws SQLException {
        String sql = "UPDATE notifications SET is_read = ? WHERE id_notif = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Mark each notification as read
            for (int notificationId : notificationIds) {
                statement.setBoolean(1, true); // Assuming is_read is a boolean column
                statement.setInt(2, notificationId);
                statement.addBatch();
            }
            // Execute batch update
            statement.executeBatch();
        }
    }

}
*/