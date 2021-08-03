
package learnlive.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnlive.entities.Attendance;
import learnlive.entities.SchoolClass;
import learnlive.entities.Student;
import learnlive.utils.MyUtils;


public class AttendanceDB extends Database {
    
    public static long findIdIfNumberExists(int number, long schoolClassId) throws SQLException {
        
        String sql = String.format(
                "SELECT id FROM %s "
                + "WHERE number = ? "
                + "AND school_class_id = ?", 
                Attendance.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setInt(1, number);
            pstmt.setLong(2, schoolClassId);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    public static long findIdIfStudentExists(long studentId, long schoolClassId) throws SQLException {
        
        String sql = String.format(
                "SELECT id FROM %s "
                + "WHERE student_id = ? "
                + "AND school_class_id = ?", 
                Attendance.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, studentId);
            pstmt.setLong(2, schoolClassId);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
         
    }
    
    
    public static void insert(Attendance a) throws SQLException {
        
        String sql = String.format(
                "INSERT INTO %s (school_class_id, student_id, number) "
                + "VALUES (?, ?, ?)", Attendance.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, a.getSchoolClass().getId());
            pstmt.setLong(2, a.getStudent().getId());
            pstmt.setInt(3, a.getNumber());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Attendance not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) a.setId(keys.getLong(1));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
    public static Attendance form(ResultSet result) throws SQLException {
        Attendance a = new  Attendance();
        a.setId(result.getLong("attendance_id"));
        a.setNumber(result.getInt("number"));
        a.setCreatedAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("marked_at")));
        return a;
    }
    
    
    public static int countAll(long schId) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT COUNT(id) AS count FROM %s WHERE school_class_id = ?", Attendance.TABLE)
            )) {
            
            pstmt.setLong(1, schId);
             
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getInt("count");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static List<Attendance> find(long schId, int page, int limit) throws SQLException {
        
        String sql = String.format(
            "SELECT *, attendance.id AS attendance_id, "
            + "students.id AS student_id, attendance.created_at AS marked_at "
            + "FROM %s INNER JOIN %s "
            + "ON attendance.student_id = students.id "
            + "WHERE attendance.school_class_id = ? "
            + "ORDER BY attendance.number ASC LIMIT ?, ?", 
            Attendance.TABLE, Student.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, schId);
            pstmt.setInt(2, page);
            pstmt.setInt(3, limit);
            
            ResultSet result = pstmt.executeQuery();
            
            List<Attendance> list = new ArrayList<>();
            
            while (result.next()) {
                Attendance a = form(result);
                a.setStudent(StudentDB.form(result));
                list.add(a);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    
    public static void delete(long id) throws SQLException {
        
        String sql = String.format(
                "DELETE FROM %s WHERE id = ?", Attendance.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Attendance not deleted");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }

    
    
    
}



