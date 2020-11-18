import com.github.javafaker.Faker;
import com.github.javafaker.Yoda;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StudentGenerator {
    private static Faker faker = new Faker();

    public static Student generateStudent() {
        return new Student("1", faker.name().firstName(), faker.name().lastName(), faker.date().birthday(), faker.address().fullAddress(), faker.lorem().paragraph());
    }

    public static List<Student> generateStudentList(int listSize) {
        List<Student> studentList = new LinkedList<>();
        for(int i=0; i<listSize; ++i) {
            studentList.add(generateStudent());
        }
        return studentList;
    }
}
