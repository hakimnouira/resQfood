package services.feriel;

import models.feriel.User;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User>{

    private final Connection connection;
    public static User loggedIn ;
    List<User> people = new ArrayList<>();
    public UserService(){connection = MyDatabase.getInstance().getConnection();}
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
    Statement ste =connection.createStatement();
    String rq = "DELETE FROM `3A2`.`user` WHERE `user`.`id`= '"+ id +"';";
    ste.executeUpdate(rq);
    }

    /**
     * read() gets all user info from db and creates a list containing User objects.
     * It gets info from db and creates User instances that are then added to the list
     * @return  List of users
     */
    @Override
    public List<User> read() throws SQLException {


        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
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

    public void setLoggedInUser(User user){
        loggedIn= user;
    }

    public User userByMail(String mail){
       // User user= new User();
        if (people.isEmpty()){
            try {
                read();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        for (User user: people) {
            if (user.getEmail().equals(mail)) {
                return user; // Found the user, return immediately
            }
        }

        // User with the given email not found
        return null;

        //TODO FAUT QUE MAIL SOIT UNIQ
    }










    }

