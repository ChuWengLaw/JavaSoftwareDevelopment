package Main.user;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.Vector;

public class ListUserWin extends JFrame{
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    private JScrollPane scrollpane = new JScrollPane();
    public ListUserWin(){
        super("List of users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                Main.userManagementWin.setEnabled(true);
                Main.userManagementWin.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };
        super.addWindowListener(windowListener);

        try{
            Statement statement = Main.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "select userName,CreateBillboardsPermission,EditAllBillboardPermission," +
                            "ScheduleBillboardsPermission, EditUsersPermission from user");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector column  = new Vector(columnCount);
            for (int i = 1; i<= columnCount; i++){
                column.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();
            while(rs.next()) {
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                table = new JTable(data,column);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

        setSize(500,120);
        setLocation(900,350);
        scrollpane = new JScrollPane(table);
        panel.setLayout(new BorderLayout());
        panel.add(scrollpane, BorderLayout.CENTER);
        getContentPane().add(panel);
        setVisible(true);
        }
    }
