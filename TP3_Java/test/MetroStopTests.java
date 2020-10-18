import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class MetroStopTests extends TestCase {
    @Test
    public void testListSortedById() {
        ArrayList<MetroStop> stops = new ArrayList<>();
        MetroStop ms1 = new MetroStop(3, 11.111111, 22.222222, "Jaude", "Clermont-Ferrand", "tramway");
        MetroStop ms2 = new MetroStop(0, 22.222222, 22.222222, "Jaude", "Clermont-Ferrand", "tramway");
        MetroStop ms3 = new MetroStop(1, 33.333333, 22.222222, "Jaude", "Clermont-Ferrand", "tramway");

        stops.add(ms1);
        stops.add(ms2);
        stops.add(ms3);

        Collections.sort(stops, MetroStop.IdComparator);

        Assert.assertEquals(0, stops.indexOf(ms2));
        Assert.assertEquals(1, stops.indexOf(ms3));
        Assert.assertEquals(2, stops.indexOf(ms1));
    }

    @Test
    public void testListSortedByDistrictAndThenByStationName() {
        ArrayList<MetroStop> stops = new ArrayList<>();
        MetroStop ms1 = new MetroStop(3, 11.111111, 22.222222, "Jaude", "Clermont-Ferrand", "tramway");
        MetroStop ms2 = new MetroStop(0, 11.111111, 22.222222, "Croix-de-Neyrat", "Clermont-Ferrand", "tramway");
        MetroStop ms3 = new MetroStop(1, 11.111111, 22.222222, "Cezeaux", "Aubiere", "tramway");

        stops.add(ms1);
        stops.add(ms2);
        stops.add(ms3);

        Collections.sort(stops, MetroStop.DistrictComparator);

        Assert.assertEquals(0, stops.indexOf(ms3));
        Assert.assertEquals(1, stops.indexOf(ms2));
        Assert.assertEquals(2, stops.indexOf(ms1));
    }
}
