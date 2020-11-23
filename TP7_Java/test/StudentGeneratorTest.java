import junit.framework.TestCase;

import java.util.List;

public class StudentGeneratorTest extends TestCase {
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

    public void testGenerateStudent() {
        Student student = new StudentGenerator().generateStudent();
        checkStudentValidity(student);
    }

    public void testGenerateStudentList() {
        int expectedListSize = 10;

        List<Student> studentList = new StudentGenerator().generateStudentList(expectedListSize);
        assertEquals(expectedListSize, studentList.size());

        for(Student student: studentList) checkStudentValidity(student);
    }
}
