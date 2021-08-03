
package learnlive.entities;

import java.time.LocalDateTime;


public class Assignment extends Entity {
    
    public static final String TABLE = "assignments";
    
    public static final String CODE_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private SchoolClass schoolClass;
    
    private String content;
    
    private String document;
    
    private LocalDateTime submissionStartAt;
    
    private LocalDateTime submissionEndAt;

    
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
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

    public LocalDateTime getSubmissionStartAt() {
        return submissionStartAt;
    }

    public void setSubmissionStartAt(LocalDateTime submissionStartAt) {
        this.submissionStartAt = submissionStartAt;
    }

    public LocalDateTime getSubmissionEndAt() {
        return submissionEndAt;
    }

    public void setSubmissionEndAt(LocalDateTime submissionEndAt) {
        this.submissionEndAt = submissionEndAt;
    }
      
    
    
    
}
