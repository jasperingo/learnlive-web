
package learnlive.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnlive.entities.Assignment;
import learnlive.entities.Attendance;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;
import learnlive.entities.Submission;
import learnlive.utils.MyUtils;


public class SubmissionDB extends Database {
    
    
    public static void insert(Submission sub) throws SQLException {
        
        String sql = String.format(
                "INSERT INTO %s (assignment_id, attendance_id, content, document) "
                + "VALUES (?, ?, ?, ?)", 
                
                Submission.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, sub.getAssignment().getId());
            pstmt.setLong(2, sub.getAttendance().getId());
            pstmt.setString(3, sub.getContent());
            pstmt.setString(4, sub.getDocument());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Submission not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) sub.setId(keys.getLong(1));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    public static long findIdByAttendaceAndAssignment(long aID, long atID) throws SQLException {
        
        String sql = String.format(
                "SELECT id FROM %s "
                + "WHERE assignment_id = ? AND attendance_id = ?", 
                Submission.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, aID);
            pstmt.setLong(2, atID);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    public static Submission form(ResultSet result) throws SQLException {
        Submission sub = new Submission();
        sub.setId(result.getLong("id"));
        sub.setContent(result.getString("submission_content"));
        sub.setDocument(result.getString("submission_document"));
        sub.setCreatedAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("created_at")));
        return sub;
    }
    
    
    public static int countAll(long aId) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT COUNT(id) AS count FROM %s WHERE assignment_id = ?", Submission.TABLE)
            )) {
            
            pstmt.setLong(1, aId);
             
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getInt("count");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static List<Submission> find(long aId, int page, int limit) throws SQLException {
        
        String sql = String.format(
            "SELECT *, submissions.content AS submission_content, submissions.document AS submission_document, "
            + "attendance.created_at AS marked_at "
            + "FROM %s INNER JOIN %s "
            + "ON attendance.id = submissions.attendance_id "
            + "INNER JOIN %s "
            + "ON attendance.student_id = students.id "
            + "INNER JOIN %s "
            + "ON assignments.id = submissions.assignment_id "
            + "WHERE submissions.assignment_id = ? "
            + "ORDER BY submissions.created_at DESC LIMIT ?, ?", 
            Submission.TABLE, Attendance.TABLE, Student.TABLE, Assignment.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, aId);
            pstmt.setInt(2, page);
            pstmt.setInt(3, limit);
            
            ResultSet result = pstmt.executeQuery();
            
            List<Submission> list = new ArrayList<>();
            
            while (result.next()) {
                Submission sub = form(result);
                Attendance at = AttendanceDB.form(result);
                Student st = StudentDB.form(result);
                at.setStudent(st);
                sub.setAttendance(at);
                list.add(sub);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    
}

