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

/**
 * This class creates the GUI to be used to display
 * all the existing users from the database.
 *
 * @author "Kenji" Foo Shiang Xun
 */
public class ListUserWin extends JFrame {
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    private JScrollPane scrollpane;

    /**
     * Constructor initialises the GUI creation as well as set the
     * window listener.
     */
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
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);
    }

    /**
     * Creates GUI and creates the JTable from the
     * table retrieved from function getTable()
     *
     * @author "Kenji" Foo Shiang Xun
     */
    public void createTableSQL() {
        //Sending the request to the server to retrieve list of users in the database
        ListUserRequest listUser = new ListUserRequest(Main.loginUser.getSessionToken());

        try {
            Client.connectServer(listUser);

            if (Client.isRequestState()) {
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
            } else {
                throw new Exception();
            }
        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection failed.");
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to generate list");
        }
    }

    /**
     * Retrieves the table from the server
     * and stores the table.
     *
     * @author "Kenji" Foo Shiang Xun
     */
    public void getTable(JTable table) {
        this.table = table;
    }
}