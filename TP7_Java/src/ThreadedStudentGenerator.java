import java.util.LinkedList;
import java.util.List;

public class ThreadedStudentGenerator {

    private class StudentGeneratorThread extends Thread {
        public List<Student> studentList;
        public int listSize;

        public StudentGeneratorThread(List<Student> studentList, int listSize) {
            this.studentList = studentList;
            this.listSize = listSize;
        }

        public void run() {
            studentList.addAll(new StudentGenerator().generateStudentList(listSize));
        }
    }

    public List<Student> generateStudentList(int nbThread, int listSize) throws InterruptedException {
        List<Student> studentList = new LinkedList<>();
        List<StudentGeneratorThread> studentGeneratorThreadList = new LinkedList<>();
        int localListSize = listSize / nbThread;


        // Create threads
        for(int i=0; i<nbThread; ++i) {
            if(i == nbThread-1) {
                localListSize = listSize / nbThread + (listSize % nbThread);
            }
            studentGeneratorThreadList.add(new StudentGeneratorThread(studentList, localListSize));
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
