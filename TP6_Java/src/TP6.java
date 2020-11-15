import java.util.Scanner;

public class TP6 {
    public static void main(String[] args)
    {
        try {
            Scanner keyboard = new Scanner(System.in);
            DbManager dbManager = new DbManager("jdbc:sqlite:database/mydb.db", 86400);
            dbManager.createTable("CityWeather", "id int primary key", "name varchar2(100)", "temp float", "feels_like float", "temp_min float", "temp_max float", "pressure float", "humidity float", "latitude float", "longitude float", "wind_speed float", "visibility float", "last_retrieval_date Timestamp");

            displayMenu();
            manageMenu(keyboard, dbManager);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Display all listed weathers");
        System.out.println("2. Display all listed weathers sorted by city name");
        System.out.println("3. Display all listed weathers sorted by current temperature");
        System.out.println("4. Get and save a current city weather");
        System.out.println("5. Exit the program");
        System.out.println();
        System.out.println("What is your choice ?");
    }

    private static void manageMenu(Scanner keyboard, DbManager dbManager) {

        try {
            WeatherStation weatherStation = new WeatherStation();
            String tableName = "CityWeather";
            int choice = keyboard.nextInt();
            keyboard.nextLine(); // drain the buffer

            dbManager.manageLastRetrievalDateExpiration();

            switch (choice) {
                case 1:
                    dbManager.displayResultSet(dbManager.selectAllFromTable(tableName));
                    break;
                case 2:
                    dbManager.displayResultSet(dbManager.selectAllFromTableOrderBy(tableName, "name"));
                    break;
                case 3:
                    dbManager.displayResultSet(dbManager.selectAllFromTableOrderBy(tableName, "temp"));
                    break;
                case 4:
                    System.out.println("Enter the city name to retrieve its current weather");
                    String cityName = keyboard.nextLine();

                    CityWeather cityWeather = weatherStation.getCityWeather(cityName);

                    System.out.println("test");
                    if (dbManager.checkRecordAlreadyExists(tableName, cityWeather.id)) {
                        dbManager.updateCityWeather(tableName, cityWeather);
                    } else {
                        dbManager.addCityWeather(tableName, cityWeather);
                    }
                    System.out.println(new StringBuilder("Weather of ").append(cityName).append(" successfully retrieved"));
                    System.out.println("This weather is the following: " + System.lineSeparator() + cityWeather);
                    break;
                default:
                    keyboard.close();
                    dbManager.closeDatabase();
                    System.exit(0);
            }
            System.out.println();
            displayMenu();
            manageMenu(keyboard, dbManager);

        } catch (Exception e) {
            System.out.println("You must enter a number between 1 and 5");
            keyboard.nextLine(); // drain the buffer
        }
    }
}
