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

        while ((line = br.readLine()) != null) {
            stops.add(parseLine(line, delimiter));
        }

        System.out.println("Stations de metro chargees avec succes");

        return stops;
    }

    public MetroStop parseLine(String line, String delimiter) {
        String[] parts = line.split(delimiter);

        MetroStop ms = new MetroStop();
        ms.id = Integer.parseInt(parts[0]);
        ms.longitude = Double.parseDouble(parts[1]);
        ms.latitude = Double.parseDouble(parts[2]);
        ms.stopName = parts[3];
        ms.districtName = parts[4];
        ms.stopType = parts[5];

        return ms;
    }
}
