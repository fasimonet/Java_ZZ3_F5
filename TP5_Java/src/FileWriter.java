import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWritter {
    public static void writeInACppFile(String cppFileName, String fileExtension, String cppClassContent) throws IOException {
        PrintWriter writer = new PrintWriter(cppFileName + fileExtension, "UTF-8");
        writer.print(cppClassContent);
        writer.close();

        System.out.println(new StringBuilder("File ").append(cppFileName).append(fileExtension).append(" successfully created"));
    }
}
