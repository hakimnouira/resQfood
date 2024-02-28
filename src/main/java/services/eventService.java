package services;

import models.event;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class eventService implements IService<event>{

    private Connection connection;
     event Event = new event();

    public eventService(){
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void create(event events) throws SQLException {
        String sql = "insert into events set name = ?,  date = ?,time = ?,location = ?,capacity = ?,status = ?,description = ?,image = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, events.getName());
        ps.setDate(2, events.getDate());
        ps.setString(3,events.getTime());
        ps.setString(4,events.getLocation());
        ps.setInt(5,events.getCapacity());
        ps.setString(6,events.getStatus());
        ps.setString(7,events.getDescription());
        ps.setString(8,events.getImage());
        ps.executeUpdate();
    }

    @Override
    public  void update(event events) throws SQLException {
        String sql = "update events set name = ?,  date = ?,time = ?,location = ?,capacity = ?,status = ? , users_joined = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, events.getName());
        ps.setDate(2, events.getDate());
        ps.setString(3,events.getTime());
        ps.setString(4,events.getLocation());
        ps.setInt(5,events.getCapacity());
        ps.setString(6,events.getStatus());
        ps.setInt(7,events.getUsers_joined());
        ps.setInt(8,events.getId());
        ps.executeUpdate();
        System.out.println(" event updated");
    }

   @Override
    public void delete(int id) throws SQLException {
       String sql = "DELETE FROM events WHERE id = ?";
       try (PreparedStatement statement = connection.prepareStatement(sql)) {
           statement.setInt(1, id);
           statement.executeUpdate();
       }
   }

/*
    @Override
    public void delete(event events) throws SQLException {
        int eventId = Event.getId();

        // SQL DELETE statement to delete the event with the specified ID
        String sql = "DELETE FROM events WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        }
    }*/




    @Override
    public List<event> read() throws SQLException {
        String sql = "select * from events";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<event> people = new ArrayList<>();
        while (rs.next()){
            event e = new event();//(Integer.parseInt(capacitytf.getText()), datetf.getValue(), descriptiontf.getText(), locationtf.getText(), nametf.getText(), statustf.getText(), timetf.getText());
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setDate(rs.getDate("date"));
            e.setTime(rs.getString("time"));
            e.setLocation(rs.getString("location"));
            e.setCapacity(rs.getInt("capacity"));
            e.setStatus(rs.getString("status"));
            e.setDescription(rs.getString("description"));
            e.setImage(rs.getString("image"));
            e.setUsers_joined(rs.getInt("users_joined"));
            people.add(e);
        }
        return people;
    }

}