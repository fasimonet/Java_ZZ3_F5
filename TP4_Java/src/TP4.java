import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

public class TP4 {
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
    }
}
