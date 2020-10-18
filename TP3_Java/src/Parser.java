import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Parser {

    public Parser() {

    }

    public ArrayList<MetroStop> parse(String fileName, String delimiter) throws  Exception {
        Reader reader = new FileReader(fileName);
        BufferedReader br = new BufferedReader(reader);
        ArrayList<MetroStop> stops = new ArrayList<>();
        String line;

        int cpt = 1;

        while ((line = br.readLine()) != null) {
            try {
                MetroStop ms = parseLine(line, delimiter);
                if (ms != null) {
                    stops.add(ms);
                } else {
                    System.out.printf("Impossible de lire le fichier %s a la ligne %d (mauvaise syntaxe)\n", fileName, cpt);
                    //System.exit(1);
                }
            } catch (Exception e) {
                if(e instanceof IllegalArgumentException) {
                    System.out.println("Le fichier a parser est incorrect au niveau de sa syntaxe");
                    System.exit(1);
                }

                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            cpt++;
        }

        System.out.println("Stations de metro chargees avec succes");

        return stops;
    }

    public MetroStop parseLine(String line, String delimiter) throws IllegalArgumentException {
        String[] parts = line.split(delimiter);
        MetroStop ms = new MetroStop();

        if (parts.length == 6) {
            ms.id = Integer.parseInt(parts[0]);
            ms.longitude = Double.parseDouble(parts[1]);
            ms.latitude = Double.parseDouble(parts[2]);
            ms.stopName = parts[3];
            ms.districtName = parts[4];
            ms.stopType = parts[5];
        } else {
            throw new IllegalArgumentException();
        }

        return ms;
    }
}
