
package learnlive.entities;



abstract public class User extends Entity {
    
    public static final String TYPE_LECTURER = "lecturer";
    
    public static final String TYPE_STUDENT = "student";
    
    public static final String USERS_IMG_PATH = "/path/to/users/imgs/";
    
    public static final String USERS_DEFAULT_IMG = "user.jpg";
    
    
    protected String photo; 
    
    protected String firstName;
    
    protected String lastName;
    
    protected String phoneNumber;
    
    protected String password;
    
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = USERS_IMG_PATH+(photo==null?USERS_DEFAULT_IMG:photo);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}


