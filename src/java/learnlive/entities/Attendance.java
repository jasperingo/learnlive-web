
package learnlive.entities;


public class Attendance extends Entity {
    
    public static final String TABLE = "attendance";
    
    
    private SchoolClass schoolClass;
    
    private Student student;
    
    private int number;

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
}


