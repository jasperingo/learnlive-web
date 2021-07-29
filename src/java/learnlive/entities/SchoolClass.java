
package learnlive.entities;

import java.time.LocalDateTime;


public class SchoolClass extends Entity {
    
    public static final String TABLE = "school_classes";
    
    public static final String CODE_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private Lecturer lecturer;
    
    private String code;
    
    private String topic;
    
    private int capacity;
    
    private boolean takeAttendance;
    
    private LocalDateTime endAt;
    
    private LocalDateTime startAt;
    
    
    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isTakeAttendance() {
        return takeAttendance;
    }

    public void setTakeAttendance(boolean takeAttendance) {
        this.takeAttendance = takeAttendance;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
    
    

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }
    
    
}

