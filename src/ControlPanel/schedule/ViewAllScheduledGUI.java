package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.ListScheduleRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;
/**
 * @author Callum Longman
 * WIndow that recieves a JTable with all the scheduled billbaords information
 */
public class ViewAllScheduledGUI extends JFrame {
    //Set up the swing elements
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    /**
     * Constructor initialises the GUI creation
     * Sets up Action listeners
     */
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);

        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        super.setSize(500, 120);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        //Server returns a JTable
        //make table non editable
        table = Client.getListScheduleBillboardTable();
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        //Display the Table
        JScrollPane scrollpane = new JScrollPane(table);
        panel.setLayout(new BorderLayout());
        panel.add(scrollpane, BorderLayout.CENTER);
        super.setContentPane(panel);
        super.setVisible(true);
    }
}
