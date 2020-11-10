import java.sql.*;

public class DbManager {
    public static Connection CreateDb(String dbUrl) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:database/mydb.db");
    }

    public static void CreateTable(String tableName, Statement statement, String ...rowsToCreate) throws SQLException {
        StringBuilder query = new StringBuilder("create table if not exists ").append(tableName).append(" (");
        for (String row: rowsToCreate) {
            query.append(row).append(",");
        }

        query = new StringBuilder(query.substring(0, query.length()-1));
        query.append(");");

        statement.execute(query.toString());
    }

    public static void InsertIntoTable(String tableName, Statement statement, String ...valuesToInsert) throws SQLException {
        StringBuilder request = new StringBuilder("insert into ").append(tableName).append(" values(");

        for(String val: valuesToInsert) {
            request.append(val).append(", ");
        }

        request = new StringBuilder(request.substring(0, request.length()-2));
        request.append(");");

        statement.execute(request.toString());
    }

    public static ResultSet SelectAllFromTable(String tableName, Statement statement) throws SQLException {
        String findAllCityWeathersRequest = "select * from " + tableName;
        return statement.executeQuery(findAllCityWeathersRequest);
    }

}
