package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection connection;

    public static void main(String[] arg) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mydb", "root", "");
        new EditUserWin();

    }
}
