package Main;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(new CreateEditGUI("Create and Edit"));
        Billboard bb = new Billboard();
        bb.ConnectDatabase();
        //TODO: Add action perform for CreateEditBillboard method when submit button is pressed
    }
}
