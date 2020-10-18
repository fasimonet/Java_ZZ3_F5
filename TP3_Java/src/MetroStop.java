import java.util.Comparator;

public class MetroStop {
    public int id;
    public double longitude;
    public double latitude;
    public String stopName;
    public String districtName;
    public String stopType;

    public MetroStop() {

    }

    public MetroStop(int id, double longitude, double latitude, String stopName, String districtName, String stopType) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stopName = stopName;
        this.districtName = districtName;
        this.stopType = stopType;
    }

    public static Comparator<MetroStop> IdComparator = new Comparator<MetroStop>() {
        @Override
        public int compare(MetroStop ms1, MetroStop ms2) {
            return ms1.id - ms2.id;
        }
    };

    public static Comparator<MetroStop> DistrictComparator = new Comparator<MetroStop>() {
        @Override
        public int compare(MetroStop ms1, MetroStop ms2) {
            int districtNameComparison = ms1.districtName.compareTo(ms2.districtName);
            return districtNameComparison == 0 ? ms1.stopName.compareTo(ms2.stopName) : districtNameComparison;
        }
    };

    public void Display() {
        System.out.printf("Arrêt %s de %s avec l'id %d situé à %s de latitude %f et de longitude %f\n", stopName, stopType, id, districtName, latitude, longitude);
    }
}
