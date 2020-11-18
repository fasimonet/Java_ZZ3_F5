import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TP7 {
    public static void main(String[] args) {
        // INITIALIZATION
        List<Student> students = new ArrayList<>();
        double totExecutionTime = 0;
        int tryNb = 5;
        int studentToGenerateNb = 150;
        final int nbThread = Runtime.getRuntime().availableProcessors();

        // GENERATION WITHOUT THREADS
        System.out.println("Generation of " + studentToGenerateNb + " students " + tryNb + " times without multiple threads.");

        for(int i=0; i<tryNb; ++i) {
            long startTime = System.nanoTime();
            students = StudentGenerator.generateStudentList(studentToGenerateNb);
            long endTime = System.nanoTime();

            double executionTime = (endTime - startTime) / 1000000000.0;
            System.out.println("Execution time of the generation " + (i+1) + ": " + executionTime + "s");

            totExecutionTime += executionTime;
        }

        System.out.println("Execution time average : " + totExecutionTime/(double)tryNb + "s");
        totExecutionTime = 0;

        // GENERATION WITH THREADS
        try {
            System.out.println("Generation of " + studentToGenerateNb + " students " + tryNb + " times with " + nbThread + " threads.");
            for(int i=0; i<tryNb; ++i) {
                long startTime = System.nanoTime();
                ThreadedStudentGenerator.generateStudentList(nbThread, studentToGenerateNb);
                long endTime = System.nanoTime();

                double executionTime = (endTime - startTime) / 1000000000.0;
                System.out.println("Execution time of the threaded generation " + (i + 1) + ": " + executionTime + "s");

                totExecutionTime += executionTime;
            }

            System.out.println("Threaded execution time average : " + totExecutionTime/(double)tryNb + "s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
