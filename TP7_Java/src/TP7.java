import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TP7 {
    public static void main(String[] args) {
        // INITIALIZATION
        List<Student> students;
        double totExecutionTime = 0;
        int tryNb = 5;
        int studentToGenerateNb = 150000;
        final int nbThread = Runtime.getRuntime().availableProcessors();
        //String studentTableName = "student";

        try {
            //DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db");

            // GENERATION WITHOUT THREADS
            System.out.println("Generation of " + studentToGenerateNb + " students " + tryNb + " times without multiple threads.");

            for(int i=0; i<tryNb; ++i) {
                long startTime = System.nanoTime();
                students = new StudentGenerator().generateStudentList(studentToGenerateNb);
                //dbManager.addStudentList(students);
                long endTime = System.nanoTime();

                double executionTime = (endTime - startTime) / 1000000000.0;
                System.out.println("Execution time of the generation " + (i+1) + ": " + executionTime + "s");

                totExecutionTime += executionTime;
            }

            System.out.println("Execution time average : " + totExecutionTime/(double)tryNb + "s");
            totExecutionTime = 0;

            // GENERATION WITH THREADS
            System.out.println("Generation of " + studentToGenerateNb + " students " + tryNb + " times with " + nbThread + " threads.");

            for(int i=0; i<tryNb; ++i) {
                long startTime = System.nanoTime();
                students = new ThreadedStudentGenerator().generateStudentList(nbThread, studentToGenerateNb);
                dbManager.addStudentList(students);
                long endTime = System.nanoTime();

                double executionTime = (endTime - startTime) / 1000000000.0;
                System.out.println("Execution time of the threaded generation " + (i + 1) + ": " + executionTime + "s");

                totExecutionTime += executionTime;
            }

            //dbManager.deleteRows(studentTableName);
            //dbManager.deleteTable(studentTableName);
            //dbManager.closeDatabase();

            System.out.println("Threaded execution time average : " + totExecutionTime/(double)tryNb + "s");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }



    }
}
