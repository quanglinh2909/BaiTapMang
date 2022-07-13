package IRM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    public static ConnectionDatabase instance;
    public static ConnectionDatabase getInstance(){
        if(instance == null){
           instance = new ConnectionDatabase();
        }
        return instance;
    }
    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://src//access//data.accdb");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
