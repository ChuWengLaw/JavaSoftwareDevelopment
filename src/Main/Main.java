package Main;

import Main.connection.DBConnection;
import Main.user.LoginWin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static Connection connection;
    public static Statement statement;

    /**
     *  SQL String to create a table named billboard in the database
     * @author Law
     */
    private static final String DELETE_ALL =
            "DROP table billboard;" +
            "DROP table user;" +
            "DROP table Schedule;";
    private static final String CREATE_BILLBOARD_TABLE =
            "CREATE TABLE IF NOT EXISTS Billboard ("
                    + "BillboardName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserName VARCHAR(30),"
                    + "TextColour VARCHAR(30),"
                    + "BackGroundColour VARCHAR(30),"
                    + "Message VARCHAR(30),"
                    + "Image VARCHAR(30),"
                    + "Information VARCHAR(30)" + ");";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS User ("
                    + "UserName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserPassword VARCHAR(30) NOT NULL,"
                    + "CreateBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditAllBillboardPermission BOOLEAN NOT NULL,"
                    + "ScheduleBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditUsersPermission BOOLEAN NOT NULL" + ");";

    private static final String CREATE_SCHEDULE_TABLE =
            "CREATE TABLE IF NOT EXISTS Schedule ("
                    + "BillboardName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "ScheduleTime DATETIME NOT NULL,"
                    + "Duration INT NOT NULL,"
                    + "RecurType VARCHAR(10),"
                    + "RecurDuration INT" + ");"; //only required for minutes

    //TODO: CREATE_USER_TABLE and CREATE_SCHEDULE_TABLE

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(new LoginWin());

        connection = DBConnection.newConnection();
        try {
            statement = connection.createStatement();

            statement.execute(CREATE_BILLBOARD_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_SCHEDULE_TABLE);

//################code below is just to create a test user with no name or password for testing
            try {
                ResultSet resultSet = Main.statement.executeQuery("SELECT * FROM User");
                String testAdmin = "";
                Boolean AdminExists = false;
                while (resultSet.next()) {
                    if ( testAdmin.equals(resultSet.getString("UserName")) && testAdmin.equals(resultSet.getString("UserPassword"))) {
                        AdminExists = true;
                    }
                }
                if (!AdminExists)
                {
                    statement.execute("INSERT INTO User Values ('', '',true,true,true,true);");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
//#########################above code is just to create a test user with no name or password for testing




        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}