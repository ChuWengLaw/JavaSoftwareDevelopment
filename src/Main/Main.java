package Main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static Connection connection;
    public static Statement statement;

    /**
     *  SQL String to create a table named billboard in the database
     * @author Law
     */
    private static final String CREATE_BILLBOARD_TABLE =
            "CREATE TABLE IF NOT EXISTS Billboard ("
                    + "BillboardName VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,"
                    + "UserName VARCHAR(30),"
                    + "TextColour VARCHAR(30),"
                    + "BackGroundColour VARCHAR(30),"
                    + "Message VARCHAR(30),"
                    + "Image VARCHAR(30),"
                    + "Information VARCHAR(30)" + ");";

    //TODO: CREATE_USER_TABLE and CREATE_SCHEDULE_TABLE

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(new CreateEditGUI("Create and Edit"));
        connection = DBConnection.newConnection();
        try {
            statement = connection.createStatement();
            statement.execute(CREATE_BILLBOARD_TABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
