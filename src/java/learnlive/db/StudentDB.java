
package learnlive.db;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import static learnlive.db.Database.getConnection;
import learnlive.entities.LoginHistory;
import learnlive.entities.Student;


public class StudentDB extends Database {
    
    
    public static Student form(ResultSet result) throws SQLException {
        Student student = new Student();
        student.setId(result.getLong("student_id"));
        student.setFirstName(result.getString("first_name"));
        student.setLastName(result.getString("last_name"));
        student.setMatriculationNumber(result.getString("matriculation_number"));
        student.setPhoneNumber(result.getString("phone_number"));
        student.setPassword(result.getString("password"));
        student.setCreatedAt(((Timestamp)result.getObject("created_at")).toLocalDateTime());
        return student;
    }
    
    public static Student findByMatriculationNumber(String number) throws SQLException {
        
        return find(String.format("SELECT *, id AS student_id FROM %s WHERE matriculation_number = ?", Student.TABLE), number);
         
    }
    
    public static Student findByPhoneNumber(String number) throws SQLException {
        
        return find(String.format("SELECT *, id AS student_id FROM %s WHERE phone_number = ?", Student.TABLE), number);
         
    }
    
    public static Student find(String sql, String number) throws SQLException {
        
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
    
    public static long findIdByPhoneNumber(String number) throws SQLException {
        
        String sql = String.format("SELECT id FROM %s WHERE phone_number = ?", Student.TABLE);
           
        return findId(sql, number);
    }
    
    public static long findIdByMatriculationNumber(String number) throws SQLException {
        
        String sql = String.format("SELECT id FROM %s WHERE matriculation_number = ?", Student.TABLE);
           
        return findId(sql, number);
    }
    
    public static long findId(String sql, String number) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, number);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    
    public static void insert(Student student) throws SQLException {
        
        String sql = String.format("INSERT INTO %s ("
                + "matriculation_number, first_name, last_name, phone_number, password) "
                + "VALUES (?, ?, ?, ?, ?)", Student.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            getConnection().setAutoCommit(false);
            
            pstmt.setString(1, student.getMatriculationNumber());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setString(5, student.getPassword());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Student not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) student.setId(keys.getLong(1));
            
            LoginHistory history = new LoginHistory();
            history.setStudent(student);
            
            LoginHistoryDB.insertForStudent(history);
            
            getConnection().commit();
            
        } catch (SQLException ex) {
            
            try {
                getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex1);
                ex.setNextException(ex1);
            }
            
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
}


