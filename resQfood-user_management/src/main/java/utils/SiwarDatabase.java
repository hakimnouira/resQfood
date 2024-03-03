/*
package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SiwarDatabase {
    private final String URL = "jdbc:mysql://localhost:3306/donationmanagement";
    private final String USER = "root";
    private final String PASS = "0000";
    private Connection connection;

    private static SiwarDatabase instance;

    private SiwarDatabase(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static SiwarDatabase getInstance() {
        if(instance == null)
            instance = new SiwarDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
*/