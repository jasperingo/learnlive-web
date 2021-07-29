
package learnlive.entities;



public class Lecturer extends User {
    
    
    public static final String TABLE = "lecturers";
    
    private String personnelNumber;
    
    
    public String getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(String personnelNumber) {
        this.personnelNumber = personnelNumber;
    }
    
    
    
}


