import java.sql.*;

public class DbManager {
    private final Connection connection;
    private final Statement statement;
    private final int expirationDelay;

    public DbManager(String dbUrl, int delay) throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
        statement = connection.createStatement();
        expirationDelay = delay;
    }

    public void createTable(String tableName, String ...rowsToCreate) throws SQLException {
        StringBuilder query = new StringBuilder("create table if not exists ").append(tableName).append(" (");
        for (String row: rowsToCreate) {
            query.append(row).append(",");
        }

        query = new StringBuilder(query.substring(0, query.length()-1));
        query.append(");");

        statement.execute(query.toString());
    }

    public void addCityWeather(String tableName, CityWeather cityWeather) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO " + tableName + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        insertStatement.setInt(1, cityWeather.id);
        insertStatement.setString(2, cityWeather.name);
        insertStatement.setDouble(3, cityWeather.main.temp);
        insertStatement.setDouble(4, cityWeather.main.feels_like);
        insertStatement.setDouble(5, cityWeather.main.temp_min);
        insertStatement.setDouble(6, cityWeather.main.temp_max);
        insertStatement.setDouble(7, cityWeather.main.pressure);
        insertStatement.setDouble(8, cityWeather.main.humidity);
        insertStatement.setDouble(9, cityWeather.coord.lat);
        insertStatement.setDouble(10, cityWeather.coord.lon);
        insertStatement.setDouble(11, cityWeather.wind.speed);
        insertStatement.setDouble(12, cityWeather.visibility);
        insertStatement.setTimestamp(13, new Timestamp(new java.util.Date().getTime()));

        insertStatement.execute();
    }

    public void updateCityWeather(String tableName, CityWeather cityWeather) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("update " + tableName + " set name = ?, temp = ?, feels_like = ?, temp_min = ?, temp_max = ?, pressure = ?, humidity = ?, latitude = ?, longitude = ?, wind_speed = ?, visibility = ?, last_retrieval_date = ? where id = ?");

        insertStatement.setString(1, cityWeather.name);
        insertStatement.setDouble(2, cityWeather.main.temp);
        insertStatement.setDouble(3, cityWeather.main.feels_like);
        insertStatement.setDouble(4, cityWeather.main.temp_min);
        insertStatement.setDouble(5, cityWeather.main.temp_max);
        insertStatement.setDouble(6, cityWeather.main.pressure);
        insertStatement.setDouble(7, cityWeather.main.humidity);
        insertStatement.setDouble(8, cityWeather.coord.lat);
        insertStatement.setDouble(9, cityWeather.coord.lon);
        insertStatement.setDouble(10, cityWeather.wind.speed);
        insertStatement.setDouble(11, cityWeather.visibility);
        insertStatement.setTimestamp(12, new Timestamp(new java.util.Date().getTime()));

        insertStatement.setInt(13, cityWeather.id);

        insertStatement.execute();
    }

    public ResultSet selectAllFromTable(String tableName) throws SQLException {
        return statement.executeQuery("select * from " + tableName);
    }

    public ResultSet selectAllFromTableOrderBy(String tableName, String orderFactor) throws SQLException {
        String query = new StringBuilder("select * from ").append(tableName).append(" order by ").append(orderFactor).toString();
        return statement.executeQuery(query);
    }

    public boolean checkRecordAlreadyExists(String tableName, int id) throws SQLException {
        String query = new StringBuilder("select * from ").append(tableName).append(" where id=").append(id).toString();
        ResultSet result = statement.executeQuery(query);
        return result.next();
    }

    public void displayResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println("--------------------------------------------");
            System.out.println("City of " + resultSet.getString("name"));
            System.out.println("--------------------------------------------");
            System.out.println("Id: " + resultSet.getInt("id"));
            System.out.println("Temperature: " + resultSet.getFloat("temp"));
            System.out.println("Feels like: " + resultSet.getFloat("feels_like"));
            System.out.println("Temperature min: " + resultSet.getFloat("temp_min"));
            System.out.println("Temperature max: " + resultSet.getFloat("temp_max"));
            System.out.println("Pressure: " + resultSet.getFloat("pressure"));
            System.out.println("Humidity: " + resultSet.getFloat("humidity"));
            System.out.println("Latitude: " + resultSet.getFloat("latitude"));
            System.out.println("Longitude: " + resultSet.getFloat("longitude"));
            System.out.println("Wind speed: " + resultSet.getFloat("wind_speed") + "m/s");
            System.out.println("Visibility: " + resultSet.getFloat("visibility") + "km");
            System.out.println("Last retrieval date: " + resultSet.getDate("last_retrieval_date"));
        }
    }

    public void manageLastRetrievalDateExpiration() throws Exception {
        ResultSet resultSet = selectAllFromTable("CityWeather");

        while (resultSet.next()) {
            if(!checkValidity(resultSet)) updateCityWeather("CityWeather", new WeatherStation().getCityWeather(resultSet.getString("name")));
        }
    }

    public boolean checkValidity(ResultSet resultSet) throws SQLException {
        long delta = Math.abs(new java.util.Date().getTime() - resultSet.getDate("last_retrieval_date").getTime());
        return delta < expirationDelay;
    }

    public void closeDatabase() throws SQLException{
        statement.close();
        connection.close();
    }

    public void deleteRows(String tableName) throws SQLException {
        statement.execute("delete from " + tableName);
    }

    public void deleteTable(String tableName) throws SQLException {
        statement.execute("drop table " + tableName);
    }
}
