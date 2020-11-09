import java.io.IOException;
import static java.lang.System.exit;

public class TP5 {
    public static void main(String[] args) {
        int parametersNb = args.length;
        boolean writeInAFile = false;
        String cppContent;
        String cppFileName = "";

        if (parametersNb <= 0 || parametersNb >= 4) {
            displayBadParametersNumberError();
        } else {
            if (parametersNb == 3) {
                if(args[2].compareTo("--stdout") == 0) {
                    writeInAFile = true;
                } else {
                    displayParametersError();
                    exit(0);
                }
            } else if (parametersNb == 2) {
                if(args[1].compareTo("--stdout") == 0) {
                    writeInAFile = true;
                }
            }
            try {
                if(parametersNb == 1) {
                    cppFileName = args[0];
                } else if (parametersNb == 2) {
                    if(writeInAFile) {
                        cppFileName = args[0];
                    }
                    else {
                        cppFileName = args[1];
                    }
                } else {
                    cppFileName = args[1];
                }

                cppContent = JavaToCppConverter.convertJavaToCppClass(args[0], cppFileName);

                if (writeInAFile) {
                    FileWriter.writeInACppFile(cppFileName, ".hpp", cppContent);
                } else {
                    System.out.print(cppContent);
                }

            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void displayBadParametersNumberError() {
        System.err.println("Error : bad parameters number, the program should be called with 1 or 2 parameters");
        System.err.println("How to call the program properly: java TP5 javaClassToConvertInCppName [newCppClassName --stdout]");
    }

    private static void displayParametersError() {
        System.err.println("Error : bad parameters number, the program should be called with 1 or 2 parameters");
    }
}
