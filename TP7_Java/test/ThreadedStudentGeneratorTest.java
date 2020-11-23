import junit.framework.TestCase;

import java.util.List;

public class ThreadedStudentGeneratorTest extends TestCase {
    private void checkStudentValidity(Student student) {
        assertFalse(student.nationalId == "");
        assertNotNull(student.nationalId);
        assertFalse(student.firstName == "");
        assertNotNull(student.firstName);
        assertFalse(student.lastName == "");
        assertNotNull(student.lastName);
        assertNotNull(student.dateOfBirth);
        assertFalse(student.birthPlace == "");
        assertNotNull(student.birthPlace);
        assertFalse(student.description == "");
        assertNotNull(student.description);
    }

    public void testGenerateStudentList() {
        int nbThreads = 5;
        int listSize = 10000;
        ThreadedStudentGenerator threadedStudentGenerator = new ThreadedStudentGenerator();
        List<Student> studentList;

        try {
            studentList = threadedStudentGenerator.generateStudentList(nbThreads, listSize);

            assertEquals(listSize, studentList.size());

            for(Student student: studentList) {
                checkStudentValidity(student);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
