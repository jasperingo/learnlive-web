
package learnlive.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnlive.entities.Lecturer;
import learnlive.entities.SchoolClass;
import learnlive.utils.MyUtils;


public class SchoolClassDB extends Database {
    
    
    public static void insert(SchoolClass sch) throws SQLException {
        
        String sql = String.format("INSERT INTO %s ("
                + "lecturer_id, code, topic, start_at) "
                + "VALUES (?, ?, ?, ?)", 
                SchoolClass.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, sch.getLecturer().getId());
            pstmt.setString(2, sch.getCode());
            pstmt.setString(3, sch.getTopic());
            pstmt.setObject(4, sch.getStartAt());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("School class not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) sch.setId(keys.getLong(1));
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    public static SchoolClass form(ResultSet result) throws SQLException {
        SchoolClass sch = new  SchoolClass();
        sch.setId(result.getLong("class_id"));
        sch.setCode(result.getString("code"));
        sch.setTopic(result.getString("topic"));
        sch.setCapacity(result.getInt("capacity"));
        sch.setTakeAttendance(result.getBoolean("take_attendance"));
        sch.setEndAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("end_at")));
        sch.setStartAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("start_at")));
        sch.setCreatedAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("created_at")));
        return sch;
    }
    
    
    public static int countAll() throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT COUNT(id) AS count FROM %s", SchoolClass.TABLE)
            )) {
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getInt("count");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static List<SchoolClass> findList(int page, int limit) throws SQLException {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id, topic, code, start_at, end_at FROM %s ORDER BY start_at DESC LIMIT ?, ?", 
                        SchoolClass.TABLE)
            )) {
            
            pstmt.setInt(1, page);
            pstmt.setInt(2, limit);
            
            ResultSet result = pstmt.executeQuery();
            
            List<SchoolClass> list = new ArrayList<>();
            
            while (result.next()) {
                SchoolClass sch = new  SchoolClass();
                sch.setId(result.getLong("id"));
                sch.setCode(result.getString("code"));
                sch.setTopic(result.getString("topic"));
                sch.setEndAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("end_at")));
                sch.setStartAt(MyUtils.convertTimestampToLocalDateTime(result.getObject("start_at")));
                list.add(sch);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static SchoolClass findBycode(String code) throws SQLException {
        
        String sql = String.format(
                "SELECT *, school_classes.id AS class_id, lecturers.id AS lecturer_id "
                + "FROM %s INNER JOIN %s "
                + "ON school_classes.lecturer_id = lecturers.id "
                + "WHERE school_classes.code = ?", 
                SchoolClass.TABLE, Lecturer.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                SchoolClass sch = form(result);
                sch.setLecturer(LecturerDB.form(result));
                return sch;
            }
            
            return null;
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public static void updateCapacity(SchoolClass sch) throws SQLException {
        
        String sql = String.format("UPDATE %s SET capacity = ? WHERE id = ?", SchoolClass.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setInt(1, sch.getCapacity());
            pstmt.setLong(2, sch.getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("School class capacity not updated");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
    public static void updateTakeAttendance(SchoolClass sch) throws SQLException {
        
        String sql = String.format("UPDATE %s SET take_attendance = ? WHERE id = ?", SchoolClass.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setBoolean(1, sch.isTakeAttendance());
            pstmt.setLong(2, sch.getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("School class take attendance not updated");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SchoolClassDB.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
    }
    
    
}



