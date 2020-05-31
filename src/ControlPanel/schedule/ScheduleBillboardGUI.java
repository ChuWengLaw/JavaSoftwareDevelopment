package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.ScheduleBillboardRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 * @author Callum Longman
 * Users from this window can schedule billbaords
 * They must enter a valid billboard name, date time in correct format.  A duration
 * and have the option to set a re occurance type and duration
 */
public class ScheduleBillboardGUI extends JFrame {
    //Set up swing elements
    private JPanel pnlScheduleNewBillboard = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private JButton btnSubmit = new JButton("Submit");

    private JLabel lblBillboardName = new JLabel("Billboard Name:");
    private JLabel lblScheduledTime = new JLabel("<html>Schedule Time (24h Time):<br/>yyyy-MM-dd HH:mm:ss</html>");
    private JLabel lblDuration = new JLabel("Duration (minutes):");
    private JLabel lblReoccurType = new JLabel("Reoccur Type:");
    private JLabel lblReoccurAmount = new JLabel("Reoccur Amount:");

    private JTextField txtBillboardName = new JTextField(20);
    private JTextField txtScheduledTime = new JTextField(20);
    private JTextField txtDuration = new JTextField(20);
    private JTextField txtReoccurAmount = new JTextField(20);

    JComboBox ReoccurType = new JComboBox();


    /**
     * Constructor initialises the GUI creation
     * Sets up Action listeners
     */
    public ScheduleBillboardGUI() {
        super("Schedule Billboard");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Set the combo box options
        ReoccurType.addItem("None");
        ReoccurType.addItem("1 Day");
        ReoccurType.addItem("1 Hour");
        ReoccurType.addItem("(Below) Minutes");

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check_Valid_Inputs()) {
                    //Set the value of reoccur depending on what option is chosen in the combo box
                    String value = ReoccurType.getSelectedItem().toString();
                    if (value.equals("None")) value = "0";
                    else if (value.equals("1 Day")) value = "1";
                    else if (value.equals("1 Hour")) value = "2";
                    else if (value.equals("(Below) Minutes")) value = "3";
                    //Create Schedule Requests, sendind the correct inputs
                    ScheduleBillboardRequest temp = new ScheduleBillboardRequest(txtBillboardName.getText().toLowerCase(), txtScheduledTime.getText(),
                            txtDuration.getText(), value, txtReoccurAmount.getText(), Main.loginUser.getSessionToken(), Main.loginUser.getUserName());
                    try {
                        Client.connectServer(temp);
                        if (Client.isRequestState()) {
                            JOptionPane.showMessageDialog(null, "Scheduled");
                        } else {
                            throw new Exception();
                        }

                    } catch (ConnectException ex) {
                        JOptionPane.showMessageDialog(null, "Connection fail.");
                        System.exit(0);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Did not Schedule");
                    }
                    //reset the text boxes
                    txtBillboardName.setText("");
                    txtScheduledTime.setText("");
                    txtDuration.setText("");
                    txtReoccurAmount.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Inputs");
                }
            }
        });

        constraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        pnlScheduleNewBillboard.add(lblBillboardName, constraints);
        constraints.gridy = 1;
        pnlScheduleNewBillboard.add(lblScheduledTime, constraints);
        constraints.gridy = 3;
        pnlScheduleNewBillboard.add(lblDuration, constraints);
        constraints.gridy = 4;
        pnlScheduleNewBillboard.add(lblReoccurType, constraints);
        constraints.gridy = 5;
        pnlScheduleNewBillboard.add(lblReoccurAmount, constraints);
        //add Text box to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlScheduleNewBillboard.add(txtBillboardName, constraints);
        constraints.gridy = 1;
        pnlScheduleNewBillboard.add(txtScheduledTime, constraints);
        constraints.gridy = 3;
        pnlScheduleNewBillboard.add(txtDuration, constraints);
        constraints.gridy = 4;
        pnlScheduleNewBillboard.add(ReoccurType, constraints);
        constraints.gridy = 5;
        pnlScheduleNewBillboard.add(txtReoccurAmount, constraints);
        //add button to panel
        constraints.gridx = 0;
        constraints.gridy = 6;
        pnlScheduleNewBillboard.add(btnSubmit, constraints);

        getContentPane().add(pnlScheduleNewBillboard);
        //set the location of the GUI
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);
        //make changes and then send to GUI
        pack();
        setVisible(true);

    }

    /**
     * This function adds info to a JLabel
     *
     * @return a Boolean if the inputs to send are valid
     * @author Callum
     */
    private boolean Check_Valid_Inputs() {
        //Check all the inputs are valid
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BillboardTitle = txtBillboardName.getText();
        try {
            if (txtReoccurAmount.getText().equals("")) {
                txtReoccurAmount.setText("0");
            }
            //check that the inputs are ints and or date times
            Date date = format.parse(txtScheduledTime.getText());
            int Duration = Integer.parseInt(txtDuration.getText());
            int ReoccurAmount = Integer.parseInt(txtReoccurAmount.getText());
            if (Duration <= 0)// || ReoccurType < 0 || ReoccurType > 3)
            {
                return false;
            } else if (ReoccurType.getSelectedItem().toString().equals("(Below) Minutes") && ReoccurAmount < Duration) {
                return false;
            }
        } catch (Exception e)//NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}

