package Main;

import Main.billboard.BillBoardManagementGUI;
import Main.user.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class Main {
<<<<<<< HEAD

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
    public static ListUserWin listUserWin = new ListUserWin();

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
                    + "RecurType VARCHAR(10),"
                    + "RecurDuration INT" + ");"; //only required for minutes

    //TODO: CREATE_USER_TABLE and CREATE_SCHEDULE_TABLE

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
=======
    public static void main(String[] args) {
        // User the commented code to start a new connection to server
//        Properties props = new Properties();
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream("./network.props");
//            props.load(in);
//            in.close();
//
//            // specify the socket port
//            int port = Integer.parseInt(props.getProperty("port"));
//            String address = props.getProperty("address");
//            Socket socket = new Socket(address, port);
//
//            System.out.println("Connected to Server");
//
//
//            socket.close();
//        } catch (Exception e) {
//            System.err.println(e);
//        }
>>>>>>> 7d81f66fa5f95d76ca72d621a652122be54c986f
        SwingUtilities.invokeLater(new LoginWin());
    }
}

