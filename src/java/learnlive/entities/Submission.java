
package learnlive.entities;


public class Submission extends Entity {
    
    public static final String TABLE = "submissions";
    
    public static final String CODE_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private Attendance attendance;
    
    private Assignment assignment;
    
    private String content;
    
    private String document;

    
    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
    
    
    
    
}


