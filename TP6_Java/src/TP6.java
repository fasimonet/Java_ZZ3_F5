import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.sql .*;

public class TP6 {
    public static void main(String[] args)
    {
        WeatherStation weatherStation = new WeatherStation();
        CityWeather cityWeather;

        if(args.length != 1) {
            System.err.println("Error : the program must take exactly one argument : the city name");
            System.err.println("Error detail :");
            throw new IllegalArgumentException();
        }

        try {
            cityWeather = weatherStation.getCityWeather(args[0]);
            System.out.println(cityWeather);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            Connection connection = DbManager.CreateDb("jdbc:sqlite:database/mydb.db");

            Statement statement = connection.createStatement();

            DbManager.CreateTable("CityWeather", statement, "id integer PRIMARY KEY", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float");
            DbManager.InsertIntoTable("CityWeather", statement, "11", "14.3", "16.8", "11.4", "18.3", "7.2", "40.7");

            ResultSet allCityWeathers = DbManager.SelectAllFromTable("CityWeather", statement);

            while (allCityWeathers.next()) {
                System.out.println("Id: " + allCityWeathers.getInt("id"));
                System.out.println("Temperature: " + allCityWeathers.getFloat("temp"));
                System.out.println("Feels like: " + allCityWeathers.getFloat("feels_like"));
                System.out.println("Temperature min: " + allCityWeathers.getFloat("temp_min"));
                System.out.println("Temperature max: " + allCityWeathers.getFloat("temp_max"));
                System.out.println("Pressure: " + allCityWeathers.getFloat("pressure"));
                System.out.println("Humidity: " + allCityWeathers.getFloat("humidity"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
