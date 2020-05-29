package Server;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Sample Code for database setup (Read from db.props)
 *
 * @author Law
 * @exception SQLException if a database access error occurs or the url is
 * {@code null}
 */
public class DBConnection {

    /**
     * The singleton instance of the database connection.
     */
    private static Connection connection = null;

    /**
     * Constructor initialises the connection.
     */
    private DBConnection() {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            connection = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLSyntaxErrorException ex) {
            JOptionPane.showMessageDialog(null, "Database Does not exist");
        } catch (SQLNonTransientConnectionException ex) {
            JOptionPane.showMessageDialog(null, "Database Connection Fail");
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection newConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }
}

