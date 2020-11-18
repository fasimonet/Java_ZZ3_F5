import java.util.LinkedList;
import java.util.List;

public class ThreadedStudentGenerator {

    private static class StudentGeneratorThread extends Thread {
        public List<Student> studentList;
        public int listSize;

        public StudentGeneratorThread(List<Student> studentList, int listSize) {
            this.studentList = studentList;
            this.listSize = listSize;
        }

        public void run() {
            studentList.addAll(StudentGenerator.generateStudentList(listSize));
        }
    }

    public static List<Student> generateStudentList(int nbThread, int listSize) throws InterruptedException {
        List<Student> studentList = new LinkedList<>();
        List<Thread> studentGeneratorThreadList = new LinkedList<>();

        // Create threads
        for(int i=0; i<nbThread; ++i) {
            studentGeneratorThreadList.add(new StudentGeneratorThread(studentList, listSize));
        }

        // Start threads
        for(int j=0; j<nbThread; ++j) {
            studentGeneratorThreadList.get(j).start();
        }

        // Wait threads
        for(int k=0; k<nbThread; ++k) {
            studentGeneratorThreadList.get(k).join();
        }

        return studentList;
    }
}
