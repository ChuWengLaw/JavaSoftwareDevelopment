package ControlPanel.billboard;

import Server.*;


import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;


/**
  * This class creates the GUI to be used to display all the existing
  * billboards from the database
  */
public class ListBillboardsGUI extends JFrame {
    /**
     * Constructor initialises the GUI creation.
     */
    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }
    /**
     * Creates JTable from database
     * @author Law
     */
    private void createGUI() {
        try{
            Statement statement = Server.connection.createStatement();
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
