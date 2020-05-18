package Main;

import Main.connection.DBConnection;
import Main.schedule.TestSchedule;
import Main.user.*;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.util.Random;

public class Main {

    public static Connection connection;
    public static Statement statement;
    public static User user = new User();

    // Setting up windows
    public static MenuWin menuWin = new MenuWin();
    public static UserManagementWin userManagementWin = new UserManagementWin();
    public static ChangePasswordWin changePasswordWin = new ChangePasswordWin();
    public static CreateUserWin createUserWin = new CreateUserWin();
    public static EditUserWin editUserWin = new EditUserWin();
    public static DeleteUserWin deleteUserWin = new DeleteUserWin();

    /**
     * SQL String to create a table named billboard in the database
     *
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
                    + "UserPassword VARCHAR(64) NOT NULL,"
                    + "CreateBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditAllBillboardPermission BOOLEAN NOT NULL,"
                    + "ScheduleBillboardsPermission BOOLEAN NOT NULL,"
                    + "EditUsersPermission BOOLEAN NOT NULL,"
                    + "SaltValue VARCHAR(64) NOT NULL" + ");";

    private static final String CREATE_SCHEDULE_TABLE =
            "CREATE TABLE IF NOT EXISTS Schedule ("
                    + "BillboardName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "ScheduleTime DATETIME NOT NULL,"
                    + "Duration INT NOT NULL,"
                    + "RecurType ENUM ('None', 'Day', 'Hour', 'Minute'),"//ENUM ('Day', 'Hour', 'Minute')
                    + "RecurDuration INT" +
                    ");"; //Foreign Key

    //TODO: CREATE_USER_TABLE and CREATE_SCHEDULE_TABLE
    private static final String Delete_test ="DROP TABLE Schedule";
    private static final String INSERT_TEST_SCHEDULE =
            "INSERT INTO schedule VALUES ('Test Billboard', '2020-05-17 20:30:00', 30, 'Hour',20);";
    private static final String INSERT_a_SCHEDULE =
            "INSERT INTO schedule VALUES ('a', '2020-05-18 22:40:00', 20, 'Day',30);";


    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, /*Added for testing >*/ParseException {
        SwingUtilities.invokeLater(new LoginWin());
        connection = DBConnection.newConnection();
        try {

            statement = connection.createStatement();

            statement.execute(CREATE_BILLBOARD_TABLE);
            statement.execute(CREATE_USER_TABLE);
            statement.execute(CREATE_SCHEDULE_TABLE);

//#############################testing schedule
            statement.execute(Delete_test);
            statement.execute(CREATE_SCHEDULE_TABLE);
            statement.execute(INSERT_TEST_SCHEDULE);
            statement.execute(INSERT_a_SCHEDULE);
            System.out.println("Billboard Name: " + TestSchedule.What_Billboard_To_Display());

//################ testing above

            // Username and Password are added.
            try {
                ResultSet resultSet = Main.statement.executeQuery("SELECT * FROM user");
                String testAdmin = "admin";
                String testPassword = "test1";
                String testSaltString = saltString();
                Boolean AdminExists = false;
                String hashedPassword = hashAString(testPassword + testSaltString);

                while (resultSet.next()) {
                    if (testAdmin.equals(resultSet.getString("userName"))) {
                        AdminExists = true;
                    }
                }
                if (!AdminExists) {
                    PreparedStatement pstatement = connection.prepareStatement("INSERT INTO user  " +
                            "(userName, userPassword,  createBillboardsPermission, editAllBillboardPermission, scheduleBillboardsPermission, editUsersPermission, SaltValue) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
                    pstatement.setString(1, testAdmin);
                    pstatement.setString(2, hashedPassword);
                    pstatement.setBoolean(3, true);
                    pstatement.setBoolean(4, true);
                    pstatement.setBoolean(5, true);
                    pstatement.setBoolean(6, true);
                    pstatement.setString(7, testSaltString);
                    pstatement.executeUpdate();
                    pstatement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
//#########################above code is just to create a test user with no name or password for testing


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String hashAString(String hashString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(hashString.getBytes());
        StringBuffer sb = new StringBuffer();

        for (byte b : hash){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }

    public static String saltString(){
        Random rng = new Random();
        byte[] saltBytes = new byte[32];
        rng.nextBytes(saltBytes);
        StringBuffer sb = new StringBuffer();

        for (byte b : saltBytes){
            sb.append(String.format("%02x", b & 0xFF));
        }

        return sb.toString();
    }
}

