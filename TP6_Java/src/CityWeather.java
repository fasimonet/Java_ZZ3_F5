public class CityWeather {
    public int id;
    public String name;
    public double visibility;

    public static class Coord {
        public double lon;
        public double lat;
    }

    public static class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public double pressure;
        public double humidity;
    }

    public static class Wind {
        public double speed;
    }

    public Coord coord;
    public Main main;
    public Wind wind;

    @Override
    public String toString() {
        StringBuilder weatherSummary = new StringBuilder();
        return weatherSummary
                .append("===== WEATHER SUMMARY =====\n")
                .append("Identifier : ").append(id).append("\n")
                .append("City name : ").append(name).append("\n")
                .append("Longitude : ").append(coord.lon).append("\n")
                .append("Latitude : ").append(coord.lat).append("\n")
                .append("Current temperature : ").append(main.temp).append("°C\n")
                .append("Temperature felt : ").append(main.feels_like).append("°C\n")
                .append("Minimum temperature : ").append(main.temp_min).append("°C\n")
                .append("Maximum temperature : ").append(main.temp_max).append("°C\n")
                .append("Pressure : ").append(main.pressure).append("Pa\n")
                .append("Visibility : ").append(visibility).append("km\n")
                .append("Wind speed : ").append(wind.speed).append("m/s\n")
                .append("Humidity : ").append(main.humidity).append("%\n")
                .toString();
    }
}
