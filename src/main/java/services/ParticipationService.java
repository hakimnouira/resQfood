package services;

import models.Participations;
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
        public void addParticipation(int id_event) throws SQLException {
            // Assuming you have a Connection object named connection' initialized somewhere
            String sql = "INSERT INTO participations ( id_event) VALUES (?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, id_event);
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


            people.add(p);
        }
        return people;
    }


}
