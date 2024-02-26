package services;

import models.Participations;

import java.sql.SQLException;
import java.util.List;

public interface IServiceP {


    void addParticipation(int id_event) throws SQLException;

    List<Participations> read() throws SQLException;
}

