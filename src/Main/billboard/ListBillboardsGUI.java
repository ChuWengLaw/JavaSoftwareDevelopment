package Main.billboard;

import Main.Main;
import Main.user.User;


import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import java.sql.Statement;
import java.util.Vector;

/**
  * List all the existing billboards in the database
  * @author Law
  */
public class ListBillboardsGUI extends JFrame {
    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    private void createGUI() {
        try{
            User user = new User();
            Statement statement = Main.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT BillboardName, UserName, TextColour, " +
                            "BackGroundColour, Message, Image, Information FROM billboard");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector column  = new Vector(columnCount);
            for (int i = 1; i<= columnCount; i++){

                column.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();

            while(rs.next()){
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++){

                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            JFrame frame = new JFrame();
            frame.setSize(500,400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel = new JPanel();
            JTable table = new JTable(data,column);
            JScrollPane scrollpane = new JScrollPane(table);
            panel.setLayout(new BorderLayout());
            panel.add(scrollpane, BorderLayout.CENTER);
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}

