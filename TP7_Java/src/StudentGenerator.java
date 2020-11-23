import com.github.javafaker.Faker;
import com.github.javafaker.Yoda;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class StudentGenerator {
    private static Faker faker = new Faker();

    public Student generateStudent() {
        return new Student(faker.idNumber().toString(), faker.name().firstName(), faker.name().lastName(), faker.date().birthday(), faker.address().fullAddress(), faker.chuckNorris().fact());
    }

    public List<Student> generateStudentList(int listSize) {
        List<Student> studentList = new LinkedList<>();
        for(int i=0; i<listSize; ++i) {
            studentList.add(new StudentGenerator().generateStudent());
        }
        return studentList;
    }
}
