import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ParserTests extends TestCase {
    @Test
    public void testParseWithGoodNumberOfStationsLoaded() {
        ArrayList<MetroStop> stops;
        try {
            stops = new Parser().parse("ratp_arret.csv", "#");
            Assert.assertEquals(stops.size(), 12012);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testParseWithEmptyFile() {
        ArrayList<MetroStop> stops;
        try {
            stops = new Parser().parse("ratp_arret_empty.csv", "#");
            Assert.assertEquals(0, stops.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testParseUnknownFileName() {
        try {
            new Parser().parse("ratp_unknown.csv", "#");
        } catch (Exception e) {
            assert e instanceof FileNotFoundException;
        }
    }

    @Test
    public void testParseLine() {
        MetroStop ms = new Parser().parseLine("1975#2.33871281165883#48.8844176451841#Abbesses#PARIS-18EME#metro", "#");

        Assert.assertEquals(ms.id, 1975);
        Assert.assertEquals(ms.longitude, 2.33871281165883, 0.00000000000001);
        Assert.assertEquals(ms.latitude, 48.8844176451841, 0.0000000000001);
        Assert.assertEquals(ms.stopName, "Abbesses");
        Assert.assertEquals(ms.districtName, "PARIS-18EME");
        Assert.assertEquals(ms.stopType, "metro");
    }

    @Test
    public void testParseLineWithIncorrectLine() {
        try {
            new Parser().parseLine("1975#2.33871281165883#Abbesses#PARIS-18EME#metro", "#");
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
    }

    @Test
    public void testParseLineWrongDelimiter() {
        try {
            new Parser().parseLine("ratp_arret.csv", ";");
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
        }
    }
}