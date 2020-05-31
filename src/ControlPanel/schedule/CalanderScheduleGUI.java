package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;

import Server.Request.ListBBRequest;
import Server.Request.ListScheduleRequest;
import Server.Request.WeeklyScheduleRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author Callum Longman
 * This is the Schedule Management window
 * users from here can schedule, delete schedules, preview billboards
 * list all schedules and view a weekly calendar for the following 7 days
 */

public class CalanderScheduleGUI extends JFrame {

    //Create Swing items (Buttons, labels, text fields)
    private JButton btnBillboardScheduler = new JButton();
    private JButton btnDeleteSchedule = new JButton();
    private JButton btnViewAllScheduled = new JButton();
    private JButton btnViewBillboard = new JButton();
    private JTextField txtViewBillboard = new JTextField(20);
    private JPanel pnlScheduleMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();
    private JLabel lblPreview = new JLabel();
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;

    /**
     * Constructor initialises the GUI creation
     * Sets up Action listeners
     */
    public CalanderScheduleGUI() {
        super("Scheduled Billboards");
        createGUI();
    }

    private void createGUI() {
        //Assign constants that are used to determine the Date/Time of specific days
        int Min_in_Millis = 60000;
        int Hour_in_Millis = 60 * Min_in_Millis;
        int Day_in_Millis = 24 * Hour_in_Millis;
        //get the current time in Milliseconds
        long CurrentTimeMillis = System.currentTimeMillis();
        //Create Labels where the text is the Date/Time of the following 7 days from the current day at 12am
        lbl1 = createLabel(CurrentTimeMillis);
        lbl2 = createLabel(CurrentTimeMillis + 1 * Day_in_Millis);
        lbl3 = createLabel(CurrentTimeMillis + 2 * Day_in_Millis);
        lbl4 = createLabel(CurrentTimeMillis + 3 * Day_in_Millis);
        lbl5 = createLabel(CurrentTimeMillis + 4 * Day_in_Millis);
        lbl6 = createLabel(CurrentTimeMillis + 5 * Day_in_Millis);
        lbl7 = createLabel(CurrentTimeMillis + 6 * Day_in_Millis);
        //set up close options
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Set up all button action listeners
        btnBillboardScheduler.setText("Schedule a Billboard");
        btnBillboardScheduler.addActionListener(new ActionListener() {
            //create new Schedule GUI
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduleBillboardGUI();
            }
        });

        btnDeleteSchedule.setText("Delete a Scheduled Billboard");
        btnDeleteSchedule.addActionListener(new ActionListener() {
            //create new delete schedule GUI
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteScheduleGUI();
            }
        });

        btnViewAllScheduled.setText("View All Scheduled Billboards");
        btnViewAllScheduled.addActionListener(new ActionListener() {
            //create new View all Schedule GUI
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAllScheduledGUI();
            }
        });

        btnViewBillboard.setText("Preview a Billboard");
        btnViewBillboard.addActionListener(new ActionListener() {
            //Open a mini Viewer to see what specific billboards will look like on the big screen
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a new request to see if the previewed billboard exists.
                ListBBRequest listBBRequest = new ListBBRequest(Main.loginUser.getSessionToken());
                try {
                    //send the request to the server
                    Client.connectServer(listBBRequest);
                } catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //receive the server reply
                JTable table = Client.getBBTable();
                int rows = table.getRowCount();
                boolean BillboardExists = false;
                //Loop through the list billboard reply for the name of the previewed billboard
                if (!txtViewBillboard.getText().isEmpty()) {
                    for (int i = 0; i < rows; i++) {
                        String tableString = table.getValueAt(i, 0).toString().toLowerCase();
                        String input = txtViewBillboard.getText().toLowerCase();
                        //If the billboard exists start a new mini viewer with the billboard name
                        if (tableString.equals(input)) {
                            new PreviewBillboardGUI(txtViewBillboard.getText());
                            BillboardExists = true;
                            break;
                        }
                    }
                }
                if (!BillboardExists) {
                    JOptionPane.showMessageDialog(null, "Billboard does not exist.");
                }
            }
        });
        //Deny/allow access to all scheduling buttons if the user does not have the correct permissions
        if (!Main.loginUser.getScheduleBillboardsPermission()) {
            btnBillboardScheduler.setEnabled(false);
            btnViewAllScheduled.setEnabled(false);
            btnDeleteSchedule.setEnabled(false);
            btnViewBillboard.setEnabled(false);
        } else {
            btnBillboardScheduler.setEnabled(true);
            btnViewAllScheduled.setEnabled(true);
            btnDeleteSchedule.setEnabled(true);
            btnViewBillboard.setEnabled(true);
        }
        //Request all the scheduled billboards for the week
        WeeklyScheduleRequest WeeklyScheduleRequest = new WeeklyScheduleRequest(Main.loginUser.getSessionToken());
        try {
            //Send the request to the server
            Client.connectServer(WeeklyScheduleRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //Receive the server reply
        ArrayList<String[]> ScheduleArray = Client.getScheduleArray();
        //Use the reply to fill 7 labels 1 for each day of the week
        lbl1 = AddInfoToLabel(ScheduleArray, lbl1, CurrentTimeMillis + 0 * Day_in_Millis);
        lbl2 = AddInfoToLabel(ScheduleArray, lbl2, CurrentTimeMillis + 1 * Day_in_Millis);
        lbl3 = AddInfoToLabel(ScheduleArray, lbl3, CurrentTimeMillis + 2 * Day_in_Millis);
        lbl4 = AddInfoToLabel(ScheduleArray, lbl4, CurrentTimeMillis + 3 * Day_in_Millis);
        lbl5 = AddInfoToLabel(ScheduleArray, lbl5, CurrentTimeMillis + 4 * Day_in_Millis);
        lbl6 = AddInfoToLabel(ScheduleArray, lbl6, CurrentTimeMillis + 5 * Day_in_Millis);
        lbl7 = AddInfoToLabel(ScheduleArray, lbl7, CurrentTimeMillis + 6 * Day_in_Millis);

        lblPreview.setText("Enter A Billboard to Preview:");
        //Set up the panels to add at specific locations
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        //Add the buttons to the frame
        constraints.gridx = 1;
        constraints.gridy = 1;
        pnlScheduleMenu.add(btnBillboardScheduler, constraints);
        constraints.gridx = 2;
        pnlScheduleMenu.add(btnDeleteSchedule, constraints);
        constraints.gridx = 3;
        pnlScheduleMenu.add(btnViewAllScheduled, constraints);
        constraints.gridx = 4;
        pnlScheduleMenu.add(btnViewBillboard, constraints);
        constraints.gridx = 5;
        pnlScheduleMenu.add(txtViewBillboard, constraints);
        constraints.gridy = 0;
        pnlScheduleMenu.add(lblPreview, constraints);
        //Add the panels to the frame
        constraints.gridy = 2;
        constraints.gridx = 1;
        pnlScheduleMenu.add(lbl1, constraints);
        constraints.gridx = 2;
        pnlScheduleMenu.add(lbl2, constraints);
        constraints.gridx = 3;
        pnlScheduleMenu.add(lbl3, constraints);
        constraints.gridx = 4;
        pnlScheduleMenu.add(lbl4, constraints);
        constraints.gridx = 5;
        pnlScheduleMenu.add(lbl5, constraints);
        constraints.gridx = 6;
        pnlScheduleMenu.add(lbl6, constraints);
        constraints.gridx = 7;
        pnlScheduleMenu.add(lbl7, constraints);

        getContentPane().add(pnlScheduleMenu);
        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);

        pack();
        repaint();
        setVisible(true);
    }

    /**
     * This function adds info to a JLabel
     *
     * @param scheduleArray    title of label
     * @param lblIn            label that needs to be edited
     * @param Day_Start_Millis the time that needs to be displayed
     * @return a JLabel with the title of text
     * @author Callum
     */
    private JLabel AddInfoToLabel(ArrayList<String[]> scheduleArray, JLabel lblIn, long Day_Start_Millis) {
        //Get the date format for the begining and end of the day
        SimpleDateFormat start_formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat end_formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date DateStart = new Date(Day_Start_Millis);
        String StringStart = start_formatter.format(DateStart);
        String StringEnd = end_formatter.format(DateStart);
        //loop through all the scheduled billboards and add the information to the weekly schedule
        for (int i = 0; i < scheduleArray.size(); i++) {
            int IntStart = scheduleArray.get(i)[1].compareTo(StringStart);
            int IntEnd = scheduleArray.get(i)[1].compareTo(StringEnd);
            //Only adds the info if the date time is correct for the days
            if (IntStart > 0 && IntEnd < 0) {
                lblIn.setText(lblIn.getText() + "<br/>" + scheduleArray.get(i)[0] + "<br/>" +
                        scheduleArray.get(i)[1].substring(0, scheduleArray.get(i)[1].length() - 2));
            }
        }
        lblIn.setText(lblIn.getText() + "</html>");
        return lblIn;
    }

    /**
     * This function adds info to a JLabel
     *
     * @param millis time to display on label
     * @return a JLabel with the title of text
     * @author Callum
     */
    private JLabel createLabel(long millis) {
        //Code to create labels that will display the day month and date for the following 7 days
        Date datePrint = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM E");
        JLabel label = new JLabel();
        label.setText("<html><strong>" + formatter.format(datePrint) + "</strong>");
        return label;
    }
    //Input a billboard name and it will return true false depending
    //on if the billboards is currently scheduled

    /**
     * This function adds info to a JLabel
     *
     * @param BillboardName billboard name to check
     * @return a Boolean if the billboards is scheduled or not
     * @author Callum
     */
    public static boolean IsCurrentlyScheduled(String BillboardName) {
        JTable Table;
        boolean IsScheduled = false;
        //Send a request to return a llst of all scheduled billboards
        ListScheduleRequest listScheduleRequest = new ListScheduleRequest(Main.loginUser.getSessionToken());
        try {
            Client.connectServer(listScheduleRequest);
        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection fail.");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Table = Client.getListScheduleBillboardTable();

        int rows = Table.getRowCount();
        //loop through the billboards and see if the inputed billboard name is currently scheduled returning true if it is
        for (int i = 0; i < rows; i++) {
            if (Table.getValueAt(i, 0).equals(BillboardName)) {
                IsScheduled = true;
            }
        }
        return IsScheduled;
    }
}