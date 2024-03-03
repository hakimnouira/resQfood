package services.fatma;

import models.fatma.Participations;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public  class ParticipationService implements IServiceP{

    private Connection connection;
    Participations partic = new Participations();

    public ParticipationService(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void addParticipation(int id_event , String particip_name) throws SQLException {
        // Assuming you have a Connection object named connection' initialized somewhere
        String sql = "INSERT INTO participations (id_event, particip_name) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id_event);
            ps.setString(2, particip_name);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Participations> read() throws SQLException {
        String sql = "select * from participations";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Participations> people = new ArrayList<>();
        while (rs.next()){
            Participations p = new Participations();//(Integer.parseInt(capacitytf.getText()), datetf.getValue(), descriptiontf.getText(), locationtf.getText(), nametf.getText(), statustf.getText(), timetf.getText());
            p.setId_event(rs.getInt("id_event"));
            p.setParticip_name(rs.getString("particip_name"));


            people.add(p);
        }
        return people;
    }


}
