package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.billboard.CreateBillboardGUI;
import Server.Request.CreateBBRequest;
import Server.Request.DeleteScheduleRequest;
import Server.Request.ScheduleBillboardRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import javax.swing.*;

/**
 * @author Callum Longman
 * This is the Delete Schedule window
 * users can delete schedules by inputting the billbaord name and a date/time
 */
public class DeleteScheduleGUI extends JFrame {
    //Create Swing items (Buttons, labels, text fields)
    private JPanel pnlDeleteSchedule = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();
    private JButton btnDelete = new JButton("Delete");
    private JLabel lblBillboardName = new JLabel("Billboard Name:");
    private JLabel lblScheduledTime = new JLabel("<html>Schedule Time (24h Time):<br/>yyyy-MM-dd HH:mm:ss</html>");
    private JTextField txtBillboardName = new JTextField(20);
    private JTextField txtScheduledTime = new JTextField(20);

    /**
     * Constructor initialises the GUI creation
     * Sets up Action listeners
     */
    public DeleteScheduleGUI() {
        super("Delete Schedule");
        createGUI();
    }

    private void createGUI() {
        //set up close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Set up all button action listeners
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check_Valid_Inputs()) {
                    //Create Delete Schedule Requests
                    DeleteScheduleRequest temp = new DeleteScheduleRequest(txtBillboardName.getText(), txtScheduledTime.getText(), Main.loginUser.getSessionToken());


                    try {
                        //Send the request and return confirmation if required
                        Client.connectServer(temp);
                        if (Client.isRequestState()) {
                            JOptionPane.showMessageDialog(null, "Deleted If combination exists.");
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
                        JOptionPane.showMessageDialog(null, "Did not Delete");
                    }
                    //reset the text fields so you can remove future schedules
                    txtBillboardName.setText("");
                    txtScheduledTime.setText("");
                } else {
                    //Return error if the input was incorrect
                    JOptionPane.showMessageDialog(null, "Invalid Date Format");
                }
            }
        });
        //Add all elements to the panel and frame
        constraints.insets = new Insets(10, 10, 10, 10);
        //add labels to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        pnlDeleteSchedule.add(lblBillboardName, constraints);
        constraints.gridy = 1;
        pnlDeleteSchedule.add(lblScheduledTime, constraints);
        //Add text to frame
        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlDeleteSchedule.add(txtBillboardName, constraints);
        constraints.gridy = 1;
        pnlDeleteSchedule.add(txtScheduledTime, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        pnlDeleteSchedule.add(btnDelete, constraints);
        getContentPane().add(pnlDeleteSchedule);
        //set the location of the GUI
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);
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
        //Check the inputs fit the date time format provided
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BillboardTitle = txtBillboardName.getText();
        try {
            Date date = format.parse(txtScheduledTime.getText());
        } catch (Exception e)//NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}

