package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.ListBBRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;


/**
 * This class creates the GUI to be used to display all the existing
 * billboards from the database
 */
public class ListBillboardsGUI extends JFrame {
    private JPanel panel = new JPanel();

    /**
     * Constructor initialises the GUI creation.
     *
     * @throws HeadlessException
     */
    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    /**
     * Creates GUI and receive the JTable from server
     *
     * @author Law
     */
    private void createGUI() {
        // sends request to server
        ListBBRequest listBBRequest = new ListBBRequest(Main.loginUser.getSessionToken());
        try {
            Client.connectServer(listBBRequest);
            if (Client.isRequestState()) {
                JOptionPane.showMessageDialog(null, "Retrieving Billboard list...");
            } else {
                throw new Exception();
            }
        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection fail.");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setSize(500, 120);
        setLocation(900, 350);

        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        super.setSize(500, 120);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JScrollPane scrollpane = new JScrollPane(Client.getBBTable());
        panel.setLayout(new BorderLayout());
        panel.add(scrollpane, BorderLayout.CENTER);
        super.setContentPane(panel);
        super.setVisible(true);
    }
}
