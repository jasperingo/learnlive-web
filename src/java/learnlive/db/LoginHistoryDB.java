
package learnlive.db;

import learnlive.entities.LoginHistory;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginHistoryDB extends Database {
    
    
    public static boolean insertForStudent(LoginHistory history) throws SQLException {
        
        String sql = String.format("INSERT INTO %s (student_id) VALUES (?)", LoginHistory.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, history.getStudent().getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Login history not inserted");
            }
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginHistoryDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
    public static boolean insertForLecturer(LoginHistory history) throws SQLException {
        
        String sql = String.format("INSERT INTO %s (lecturer_id) VALUES (?)", LoginHistory.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, history.getLecturer().getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Login history not inserted");
            }
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginHistoryDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
}



