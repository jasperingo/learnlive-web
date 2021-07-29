
package learnlive.db;


import learnlive.entities.Lecturer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnlive.entities.LoginHistory;


public class LecturerDB extends Database {
    
    public static Lecturer form(ResultSet result) throws SQLException {
        Lecturer l = new Lecturer();
        l.setId(result.getLong("lecturer_id"));
        l.setFirstName(result.getString("first_name"));
        l.setLastName(result.getString("last_name"));
        l.setPersonnelNumber(result.getString("personnel_number"));
        l.setPhoneNumber(result.getString("phone_number"));
        l.setPassword(result.getString("password"));
        l.setCreatedAt(((Timestamp)result.getObject("created_at")).toLocalDateTime());
        return l;
    }
    
    public static Lecturer findByPersonnelNumber(String number) throws SQLException {
        
        return find(String.format("SELECT *, id AS lecturer_id FROM %s WHERE personnel_number = ?", 
                Lecturer.TABLE), number);
         
    }
    
    public static Lecturer findByPhoneNumber(String number) throws SQLException {
        
        return find(String.format("SELECT *, id AS lecturer_id FROM %s WHERE phone_number = ?", 
                Lecturer.TABLE), number);
         
    }
    
    public static Lecturer find(String sql, String number) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, number);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return form(result);
            }
            
            return null;
            
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    public static long findIdByPersonnelNumber(String number) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE personnel_number = ?", Lecturer.TABLE)
            )) {
            
            pstmt.setString(1, number);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    public static String findPhoneNumberByPersonnelNumber(String number) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT phone_number FROM %s WHERE personnel_number = ?", Lecturer.TABLE)
            )) {
            
            pstmt.setString(1, number);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getString("phone_number");
            }
            
            return null;
            
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    public static void insert(Lecturer lec) throws SQLException {
        
        String sql = String.format("UPDATE %s SET phone_number = ?, password = ? "
                + "WHERE id = ?", Lecturer.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            getConnection().setAutoCommit(false);
            
            pstmt.setString(1, lec.getPhoneNumber());
            pstmt.setString(2, lec.getPassword());
            pstmt.setLong(3, lec.getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Lecturer not inserted");
            }
            
            LoginHistory history = new LoginHistory();
            history.setLecturer(lec);
            
            LoginHistoryDB.insertForLecturer(history);
            
            getConnection().commit();
            
        } catch (SQLException ex) {
            
            try {
                getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LecturerDB.class.getName()).log(Level.SEVERE, null, ex1);
                ex.setNextException(ex1);
            }
            
            Logger.getLogger(LecturerDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
}


