
package learnlive.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database {
    
    
    private static Connection connection = null;

    protected static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = createConnection();
        }
        
        return connection;
    }
    
    private static Connection createConnection() throws SQLException {
        
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String user = "root";
        String password = "6509";
        String url = "jdbc:mysql://localhost:3306/learnlive?useSSL=false&serverTimezone=Africa/Lagos";
       
        return DriverManager.getConnection(url, user, password);
    }
    
    
}


