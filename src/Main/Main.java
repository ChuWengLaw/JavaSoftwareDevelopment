package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static Connection connection;
    public static Statement statement;
    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS User ("
                    + "UserName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserPassword VARCHAR(30) NOT NULL,"
                    + "CreateBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditAllBillboardPermission BOOLEAN NOT NULL,"
                    + "ScheduleBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditUsersPermission BOOLEAN NOT NULL" + ");";
    public static void main(String[] arg) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mydb", "root", "");

        try{
            statement = connection.createStatement();
            statement.execute(CREATE_USER_TABLE);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        new DeleteUserWin();
    }
}
