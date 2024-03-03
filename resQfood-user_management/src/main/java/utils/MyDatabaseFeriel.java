/*
package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabaseFeriel {

    private final String URL="jdbc:mysql://localhost:3306/3A2";
    private final String USER="root";
   // private final String PASS="";
   private final String PASS="0000";
    private Connection connection;
    private static MyDatabaseFeriel instance;

    public MyDatabaseFeriel(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static MyDatabaseFeriel getInstance() {
        if(instance == null)
            instance = new MyDatabaseFeriel();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}
*/