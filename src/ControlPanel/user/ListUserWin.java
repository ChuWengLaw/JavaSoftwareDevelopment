package ControlPanel.user;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.ListUserRequest;
import Server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import java.sql.*;
import java.util.Vector;

public class ListUserWin extends JFrame {
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    private JScrollPane scrollpane;

    public ListUserWin() {
        super("List of users");
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Main.userManagementWin.setEnabled(true);
                Main.userManagementWin.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        };
        super.addWindowListener(windowListener);

        setSize(500, 120);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/2,height/2);
    }

    public void createTableSQL() {
        ListUserRequest listUser = new ListUserRequest(Main.loginUser.getSessionToken());

        try {
            Client.connectServer(listUser);

            if(Client.isRequestState()){
                super.setSize(500, 120);
                super.setLocationRelativeTo(null);
                JPanel panel = new JPanel();
                JScrollPane scrollpane = new JScrollPane(table);
                table.setEnabled(false);
                table.getTableHeader().setReorderingAllowed(false);
                panel.setLayout(new BorderLayout());
                panel.add(scrollpane, BorderLayout.CENTER);
                super.setContentPane(panel);
                super.setVisible(true);
            }
            else{
                throw new Exception();
            }
        } catch(ConnectException ex){
            JOptionPane.showMessageDialog(null, "Connection failed.");
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to generate list");
        }
    }

    public void getTable(JTable table) {
        this.table = table;
    }
}

//            Statement statement = Server.connection.createStatement();
//            ResultSet rs = statement.executeQuery(
//                    "select userName,CreateBillboardsPermission,EditAllBillboardPermission," +
//                            "ScheduleBillboardsPermission, EditUsersPermission from user");
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnCount = rsmd.getColumnCount();
//            Vector columnheader  = new Vector(columnCount);
//
//            for (int i = 1; i<= columnCount; i++){
//                columnheader.add(rsmd.getColumnName(i));
//            }
//
//            Vector data = new Vector();
//            Vector row = new Vector();
//
//            while(rs.next()) {
//                row = new Vector(columnCount);
//                for (int i = 1; i <= columnCount; i++) {
//                    row.add(rs.getString(i));
//                    data.add(row);
//                    table = new JTable(data,columnheader);
//                    scrollpane = new JScrollPane(table);
//                    panel.add(scrollpane, BorderLayout.CENTER);
//                    getContentPane().add(panel);
//                }
//            }