import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherStation {
    public CityWeather GetCityWeather(String cityName) throws IOException {
        StringBuilder urlString = new StringBuilder();
        URL url = new URL(urlString.append("https://api.openweathermap.org/data/2.5/weather?q=").append(cityName).append("&appid=fdec5664d19caf22895d3aaf207d3d43&units=metric").toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Gson gson = new Gson();
        CityWeather cityWeather = gson.fromJson(bufferedReader, CityWeather.class);

        urlConnection.disconnect();
        inputStream.close();
        bufferedReader.close();

        return cityWeather;
    }
}
