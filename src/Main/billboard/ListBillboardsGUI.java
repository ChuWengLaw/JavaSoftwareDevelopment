package Main.billboard;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ListBillboardsGUI extends JFrame {

    //set the width and height of the GUI
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;

    //define the table
    private JTable tblBillboards;


    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    private void createGUI() {
        //set the size of the frame and exit
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            //define a statement
            Statement statement = Main.connection.createStatement();

            //define a result set
            ResultSet rs = statement.executeQuery("SELECT BillboardName, Information FROM Billboard");

            //define the results meta data
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector column = new Vector(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                column.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();
            while (rs.next()) {
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            tblBillboards = new JTable(data, column);
            tblBillboards.setBounds(30, 40, 200, 300);

            JScrollPane sp = new JScrollPane(tblBillboards);
            getContentPane().add(sp);

            setLocation(900, 350);

            repaint();
            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
