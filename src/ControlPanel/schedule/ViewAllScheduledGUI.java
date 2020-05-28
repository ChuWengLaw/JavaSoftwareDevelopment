package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.ListScheduleRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;


/**
 * This class creates the GUI to be used to display all the existing
 * billboards from the database
 */
public class ViewAllScheduledGUI extends JFrame {
    private JPanel panel = new JPanel();

    public ViewAllScheduledGUI() throws HeadlessException {
        super("All Scheduled");
        createGUI();
    }

    private void createGUI() {
        // sends request to server
        ListScheduleRequest listScheduleRequest = new ListScheduleRequest(Main.loginUser.getSessionToken());
        try {
            Client.connectServer(listScheduleRequest);
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
        JScrollPane scrollpane = new JScrollPane(Client.getListScheduleBillboardTable());
        panel.setLayout(new BorderLayout());
        panel.add(scrollpane, BorderLayout.CENTER);
        super.setContentPane(panel);
        super.setVisible(true);
    }
}
