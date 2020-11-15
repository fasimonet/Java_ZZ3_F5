import junit.framework.TestCase;
import java.sql.ResultSet;

public class DbManagerTest extends TestCase {
    public void testSelectAll() {
        try {
            String tableName = "TestCityWeather";
            WeatherStation weatherStation = new WeatherStation();
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 86400);
            dbManager.createTable(tableName, "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Volvic"));
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Clermont-Ferrand"));
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Aubière"));

            ResultSet resultSet = dbManager.selectAllFromTable(tableName);

            //Test Volvic
            resultSet.next();
            assertEquals(2967710, resultSet.getInt("id"));
            assertEquals("Volvic", resultSet.getString("name"));
            assertEquals(3.04, resultSet.getDouble("longitude"));
            assertEquals(45.87, resultSet.getDouble("latitude"));

            //Test Clermont-Ferrand
            resultSet.next();
            assertEquals(3024634, resultSet.getInt("id"));
            assertEquals("Arrondissement de Clermont-Ferrand", resultSet.getString("name"));
            assertEquals(3.0, resultSet.getDouble("longitude"));
            assertEquals(45.67, resultSet.getDouble("latitude"));

            //Test Aubière
            resultSet.next();
            assertEquals(3036364, resultSet.getInt("id"));
            assertEquals("Aubière", resultSet.getString("name"));
            assertEquals(3.11, resultSet.getDouble("longitude"));
            assertEquals(45.75, resultSet.getDouble("latitude"));

            dbManager.deleteRows(tableName);
            dbManager.deleteTable(tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSelectAllSortedByName() {
        try {
            String tableName = "TestCityWeather";
            WeatherStation weatherStation = new WeatherStation();
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 86400);
            dbManager.createTable(tableName, "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Volvic"));
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Clermont-Ferrand"));
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Aubière"));

            ResultSet resultSet = dbManager.selectAllFromTableOrderBy(tableName, "name");

            //Test Arrondissement de Clermont-Ferrand is the first of the resultSet
            resultSet.next();
            assertEquals(3024634, resultSet.getInt("id"));
            assertEquals("Arrondissement de Clermont-Ferrand", resultSet.getString("name"));
            assertEquals(3.0, resultSet.getDouble("longitude"));
            assertEquals(45.67, resultSet.getDouble("latitude"));

            //Test Aubière is the second of the resultSet
            resultSet.next();
            assertEquals(3036364, resultSet.getInt("id"));
            assertEquals("Aubière", resultSet.getString("name"));
            assertEquals(3.11, resultSet.getDouble("longitude"));
            assertEquals(45.75, resultSet.getDouble("latitude"));

            //Test Volvic is the third of the resultSet
            resultSet.next();
            assertEquals(2967710, resultSet.getInt("id"));
            assertEquals("Volvic", resultSet.getString("name"));
            assertEquals(3.04, resultSet.getDouble("longitude"));
            assertEquals(45.87, resultSet.getDouble("latitude"));

            dbManager.deleteRows(tableName);
            dbManager.deleteTable(tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testUpdateCityWeather() {
        try {
            String tableName = "TestCityWeather";
            WeatherStation weatherStation = new WeatherStation();
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 86400);
            dbManager.createTable(tableName, "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");

            CityWeather cityWeather = weatherStation.getCityWeather("Volvic");
            dbManager.addCityWeather(tableName, cityWeather);

            ResultSet resultSet = dbManager.selectAllFromTable(tableName);

            //Test Volvic
            resultSet.next();
            assertEquals(2967710, resultSet.getInt("id"));
            assertEquals("Volvic", resultSet.getString("name"));
            assertEquals(3.04, resultSet.getDouble("longitude"));
            assertEquals(45.87, resultSet.getDouble("latitude"));

            cityWeather.name = "Aubière";
            dbManager.updateCityWeather(tableName, cityWeather);

            resultSet = dbManager.selectAllFromTable(tableName);

            //Test Aubière
            resultSet.next();
            assertEquals(2967710, resultSet.getInt("id"));
            assertEquals("Aubière", resultSet.getString("name"));
            assertEquals(3.04, resultSet.getDouble("longitude"));
            assertEquals(45.87, resultSet.getDouble("latitude"));

            dbManager.deleteRows(tableName);
            dbManager.deleteTable(tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCheckValidityOK()
    {
        try {
            String tableName = "TestCityWeather";
            WeatherStation weatherStation = new WeatherStation();
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 86400);
            dbManager.createTable(tableName, "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Volvic"));

            ResultSet resultSet = dbManager.selectAllFromTable(tableName);
            resultSet.next();

            assertTrue(dbManager.checkValidity(resultSet));

            dbManager.deleteRows(tableName);
            dbManager.deleteTable(tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testCheckValidityKO()
    {
        try {
            String tableName = "TestCityWeather";
            WeatherStation weatherStation = new WeatherStation();
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 0);
            dbManager.createTable(tableName, "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");
            dbManager.addCityWeather(tableName, weatherStation.getCityWeather("Volvic"));

            ResultSet resultSet = dbManager.selectAllFromTable(tableName);
            resultSet.next();

            assertFalse(dbManager.checkValidity(resultSet));

            dbManager.deleteRows(tableName);
            dbManager.deleteTable(tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
