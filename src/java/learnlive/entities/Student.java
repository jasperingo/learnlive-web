
package learnlive.entities;


public class Student extends User {
    
    public static final String TABLE = "students";
    
    private String matriculationNumber;

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }
    
    
}
