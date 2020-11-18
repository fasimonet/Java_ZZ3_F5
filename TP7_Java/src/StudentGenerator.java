import com.github.javafaker.Faker;
import com.github.javafaker.Yoda;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StudentGenerator {
    public static Student generateStudent() {
        Faker faker = new Faker();
        String[] fullName = faker.gameOfThrones().character().split(" ");
        return new Student("1", fullName[0], fullName.length < 2 ? "Snow" : fullName[1], faker.date().birthday(), faker.address().fullAddress(), faker.gameOfThrones().quote());
    }

    public static List<Student> generateStudentList(int listSize) {
        List<Student> studentList = new LinkedList<>();
        for(int i=0; i<listSize; ++i) {
            studentList.add(generateStudent());
        }
        return studentList;
    }
}
