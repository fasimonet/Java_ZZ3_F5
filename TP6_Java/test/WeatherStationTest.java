import junit.framework.TestCase;
import java.io.IOException;

public class WeatherStationTest extends TestCase {
    public void testBadCityName() {
        WeatherStation weatherStation = new WeatherStation();
        try {
            weatherStation.getCityWeather("city name which does not exist");
            fail("Call of GetCityWeather() method with bad city name should have fired an exception");
        } catch (IOException e) {
            assertNull(null);
        }
    }

    public void testOK() {
        WeatherStation weatherStation = new WeatherStation();
        CityWeather cityWeather = null;
        try {
            cityWeather = weatherStation.getCityWeather("Clermont-Ferrand");
        } catch (IOException e) {
            fail("Call of GetCityWeather() method with good city name should not have fired an exception");
        }

        assertNotNull(cityWeather);
        assertEquals(3024634, cityWeather.id);
        assertEquals("Arrondissement de Clermont-Ferrand", cityWeather.name);
        assertEquals(3.0, cityWeather.coord.lon);
        assertEquals(45.67, cityWeather.coord.lat);
        assertTrue(cityWeather.main.humidity >=  0 && cityWeather.main.humidity <= 100);
        assertTrue(cityWeather.main.pressure >=  0);
        assertTrue(cityWeather.wind.speed >= 0);
        assertTrue(cityWeather.visibility >= 0);
    }
}