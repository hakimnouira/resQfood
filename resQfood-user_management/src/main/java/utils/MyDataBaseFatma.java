/*
package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBaseFatma {
    private final String URL = "jdbc:mysql://localhost:3306/AIntegration";
    private final String USER = "root";
    private final String PASS = "0000";
    private Connection connection;

    private static MyDataBaseFatma instance;

    private MyDataBaseFatma(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MyDataBaseFatma getInstance() {
        if(instance == null)
            instance = new MyDataBaseFatma();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
 */