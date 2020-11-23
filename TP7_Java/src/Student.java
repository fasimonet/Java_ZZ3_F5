import java.util.Date;

public class Student {
    public String nationalId;
    public String lastName;
    public String firstName;
    public Date dateOfBirth;
    public String birthPlace;
    public String description;

    public Student(String nationalId, String firstName, String lastName, Date dateOfBirth, String birthPlace, String description) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.birthPlace = birthPlace;
        this.description = description;
    }

    public String toString() {
        return "Student id: " + nationalId + ", first name: " + firstName + ", last name: " + lastName  + ", date of birth: " + dateOfBirth + ", birth place: " + birthPlace + ", description: " + description;
    }
}
