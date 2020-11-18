import java.util.Date;

public class Student {
    public String nationalId;
    public String lastName;
    public String firstName;
    public Date birthDate;
    public String birthPlace;
    public String description;

    public Student(String nationalId, String firstName, String lastName, Date birthDate, String birthPlace, String description) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.description = description;
    }

    public String toString() {
        return "Student id: " + nationalId + ", first name: " + firstName + ", last name: " + lastName  + ", birth date: " + birthDate + ", birth place: " + birthPlace + ", description: " + description;
    }
}
