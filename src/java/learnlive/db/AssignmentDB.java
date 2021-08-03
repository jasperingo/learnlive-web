
package learnlive.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import static learnlive.db.Database.getConnection;
import learnlive.entities.Assignment;
import learnlive.entities.Attendance;
import learnlive.entities.SchoolClass;
import learnlive.utils.MyUtils;


public class AssignmentDB extends Database {
    
    
    public static void insert(Assignment a) throws SQLException {
        
        String sql = String.format(
                "INSERT INTO %s (school_class_id, content, document) "
                + "VALUES (?, ?, ?)", 
                
                Assignment.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, a.getSchoolClass().getId());
            pstmt.setString(2, a.getContent());
            pstmt.setString(3, a.getDocument());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Assignment not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) a.setId(keys.getLong(1));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
    public static Assignment form(ResultSet result) throws SQLException {
        Assignment a = new Assignment();
        a.setId(result.getLong("id"));
        a.setContent(result.getString("content"));
        a.setDocument(result.getString("document"));
        a.setCreatedAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("created_at")));
        return a;
    }
    
    public static Assignment findBySchoolClass(SchoolClass sch) throws SQLException {
        
        String sql = String.format(
                "SELECT * FROM %s "
                + "WHERE school_class_id = ?", 
                Assignment.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, sch.getId());
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return form(result);
            }
            
            return null;
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
}


