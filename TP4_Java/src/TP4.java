import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

public class TP4 {
    public static void main(String[] args)
    {
        //String url = "api.openweathermap.org/data/2.5/weather?q=Clermont-Ferrand&appid=fdec5664d19caf22895d3aaf207d3d43&units=metric";
        WeatherStation weatherStation = new WeatherStation();
        CityWeather cityWeather = null;

        if(args.length != 1) {
            System.err.println("Error : the program must take exactly one argument : the city name");
            System.err.println("Error detail :");
            throw new IllegalArgumentException();
        }

        try {
            cityWeather = weatherStation.GetCityWeather(args[0]);
            System.out.println(cityWeather);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
