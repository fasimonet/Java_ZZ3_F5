import java.sql.*;
import java.util.Date;
import java.util.List;

public class DbManager {
    private Connection connection;
    private Statement statement;

    public DbManager(String name) throws SQLException {
        connection = DriverManager.getConnection(name);
        statement = connection.createStatement();
        //statement.setQueryTimeout(30);

        createTableIfNotExist();
    }

    public void createTableIfNotExist() throws SQLException {
        statement.executeUpdate("create table if not exists student (nationalId string, firstName string, lastName string, dateOfBirth Timestamp, birthPlace string, description string)");
    }

    public void addStudent(Student student) throws Exception {
        PreparedStatement ps = connection.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?)");

        ps.setString(1, student.nationalId);
        ps.setString(2, student.firstName);
        ps.setString(3, student.lastName);
        ps.setTimestamp(4, new Timestamp(student.dateOfBirth.getTime()));
        ps.setString(5, student.birthPlace);
        ps.setString(6, student.description);

        ps.executeUpdate();
    }

    public void addStudentList(List<Student> studentList) throws Exception {
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement("insert into student values(?, ?, ?, ?, ?, ?)");

        for (Student student: studentList) {
            addStudent(student);
        }

        connection.commit();
        connection.setAutoCommit(true);
    }

    /*public List<Student> selectAll() throws SQLException {
        ResultSet rs = statement.executeQuery("select * from etudiants order by ID");
        List<Etudiant> cities = new LinkedList<>();

        while (rs.next()) {
            cities.add(convertToEtudiant(rs));
        }

        return cities;
    }*/

    public void deleteRows(String tableName) throws SQLException {
        statement.execute("delete from " + tableName);
    }

    public void deleteTable(String tableName) throws SQLException {
        statement.execute("drop table " + tableName);
    }

    public void closeDatabase() throws SQLException{
        statement.close();
        connection.close();
    }
}
