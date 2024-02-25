package services;

import models.Testimony;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestimonyService implements IService<Testimony>{
    private final Connection connection;

    public TestimonyService() {connection= MyDatabase.getInstance().getConnection();}

    @Override
    public void create(Testimony tt) throws SQLException {
        String sql = "INSERT INTO testimony (t_id,userId,title,txt,status) VALUES (?, ?,?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, tt.getT_id());
        statement.setInt(2, tt.getUserId());
        statement.setString(3, tt.getTitle());
        statement.setString(4, tt.getTxt());
        statement.setString(5, "processing");

        statement.executeUpdate();
    }

    @Override
    public void update(Testimony tt) throws SQLException {
        String sql = "update testimony set title=?, txt=?, userId=?,status=? where t_id = ? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, tt.getTitle());
        ps.setString(2, tt.getTxt());
        ps.setInt(3, tt.getUserId());
        ps.setString(4, tt.getStatus());
        ps.setInt(5, tt.getT_id());

        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        Statement ste =connection.createStatement();
        String rq = "DELETE FROM `3A2`.`testimony` WHERE `testimony`.`t_id`= '"+ id +"';";
        ste.executeUpdate(rq);
    }

    @Override
    public List<Testimony> read() throws SQLException {

        String sql = "select * from testimony";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Testimony> testimonies = new ArrayList<>();
        while (rs.next()){
            Testimony t = new Testimony();
            t.setT_id(rs.getInt("t_id"));
            t.setUserId(rs.getInt("userId"));
            t.setTitle(rs.getString("title"));
            t.setTxt(rs.getString("txt"));
            t.setStatus(rs.getString("status"));
            testimonies.add(t);
        }
        return testimonies;
    }
}
