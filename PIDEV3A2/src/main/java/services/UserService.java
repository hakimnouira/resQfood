package services;

import models.User;
import utils.SiwarDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UService<User> {

    private final Connection connection;

    public UserService() {
        connection = SiwarDatabase.getInstance().getConnection();
    }

    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO user (fName, lName, pwd, email, phone, area, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLName());
        statement.setString(3, user.getPwd());
        statement.setString(4, user.getEmail());
        statement.setInt(5, user.getPhone());
        statement.setString(6, user.getArea());
        statement.setString(7, user.getRole());
        statement.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "update user set fName=?, lName=?, pwd=?, email=?, phone=?, area=?, role=? where id = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLName());
        ps.setString(3, user.getPwd());
        ps.setString(4, user.getEmail());
        ps.setInt(5, user.getPhone());
        ps.setString(6, user.getArea());
        ps.setString(7, user.getRole());
        ps.setInt(8, user.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String rq = "DELETE FROM `3A2`.`user` WHERE `user`.`id`= ?";
        PreparedStatement ps = connection.prepareStatement(rq);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<User> read() throws SQLException {
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> people = new ArrayList<>();
        while (rs.next()) {
            User p = new User();
            p.setId(rs.getInt("id"));
            p.setFirstName(rs.getString("fName"));
            p.setlName(rs.getString("lName"));
            p.setEmail(rs.getString("email"));
            p.setPwd(rs.getString("pwd"));
            p.setPhone(rs.getInt("phone"));
            p.setArea(rs.getString("area"));
            p.setRole(rs.getString("role"));
            people.add(p);
        }
        return people;
    }


    @Override
    public List<User> getUsersByRole(String role) throws SQLException {
        // Implementation to get users by role
        String sql = "SELECT * FROM user WHERE role = ?";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirstName(resultSet.getString("fName"));
                    user.setlName(resultSet.getString("lName"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPwd(resultSet.getString("pwd"));
                    user.setPhone(resultSet.getInt("phone"));
                    user.setArea(resultSet.getString("area"));
                    user.setRole(resultSet.getString("role"));
                    userList.add(user);
                }
            }
        }
        return userList;
    }
}
