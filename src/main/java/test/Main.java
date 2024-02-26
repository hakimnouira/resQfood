package test;

import models.event;
import services.eventService;
import utils.MyDataBase;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        eventService ps = new eventService();
        try {
//            ps.create(new Person(23,"insert into ","Ben Foulen"));
//            ps.update(new Person(1,25, "Skander","Ben Foulen"));
            System.out.println(ps.read());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
