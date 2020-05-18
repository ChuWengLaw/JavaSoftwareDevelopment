package Main.user;

import Main.Main;
import Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.Vector;

public class ListUserWin extends JFrame{
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    private JScrollPane scrollpane;

    public ListUserWin(){
<<<<<<< HEAD
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
        /**
         * @author Foo
         * This section retrieves the listed user information and puts them in a vector
         * It then displays the user information stored in the vector through JTable
         */

        setSize(500,120);
        setLocation(900,350);

        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        }

        public void createTableSQL() throws SQLException {
            Statement statement = Main.connection.createStatement();
=======
        try{
            User user = new User();
            Statement statement = Server.connection.createStatement();
>>>>>>> 7d81f66fa5f95d76ca72d621a652122be54c986f
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
                scrollpane = new JScrollPane(table);
                panel.add(scrollpane, BorderLayout.CENTER);
                getContentPane().add(panel);
            }
<<<<<<< HEAD
=======
            JFrame frame = new JFrame();
            frame.setSize(500,120);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            JTable table = new JTable(data,column);
            JScrollPane scrollpane = new JScrollPane(table);
            panel.setLayout(new BorderLayout());
            panel.add(scrollpane, BorderLayout.CENTER);
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
>>>>>>> 7d81f66fa5f95d76ca72d621a652122be54c986f
        }
    }
