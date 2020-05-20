package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.billboard.CreateBillboardGUI;
import Server.Request.CreateBBRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import javax.swing.*;


public class ScheduleBillboardGUI extends JFrame {
    private JPanel pnlScheduleNewBillboard = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private JButton btnSubmit = new JButton("Submit");

    private JLabel lblBillboardName = new JLabel("Billboard Name:");
    private JLabel lblScheduledTime = new JLabel("<html>Schedule Time:<br/>yyyy-MM-dd HH:mm:ss</html>");
    private JLabel lblDuration = new JLabel("Duration:");
    private JLabel lblReoccurType = new JLabel("Reoccur Type:");
    private JLabel lblReoccurAmount = new JLabel("Reoccur Amount:");

    private JTextField txtBillboardName = new JTextField(20);
    private JTextField txtScheduledTime = new JTextField(20);
    private JTextField txtDuration = new JTextField(20);
    private JTextField txtReoccurType = new JTextField(20);
    private JTextField txtReoccurAmount = new JTextField(20);


    public ScheduleBillboardGUI(){
        super("Schedule Billboard");
        createGUI();
    }
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {



                if (Check_Valid_Inputs())
                {
                    //System.out.println("Correct Inputs");
                    //Create Schedule Requests

                    txtBillboardName.setText("");
                    txtScheduledTime.setText("");
                    txtDuration.setText("");
                    txtReoccurType.setText("");
                    txtReoccurAmount.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect Inputs");
                }
            }
        });





        //add labels to panel
        constraints.gridx=0;
        constraints.gridy=0;

        pnlScheduleNewBillboard.add(lblBillboardName,constraints);
        constraints.gridy=1;
        pnlScheduleNewBillboard.add(lblScheduledTime,constraints);
        constraints.gridy=3;
        pnlScheduleNewBillboard.add(lblDuration,constraints);
        constraints.gridy=4;
        pnlScheduleNewBillboard.add(lblReoccurType,constraints);
        constraints.gridy=5;
        pnlScheduleNewBillboard.add(lblReoccurAmount,constraints);

        constraints.gridx=1;
        constraints.gridy=0;
        pnlScheduleNewBillboard.add(txtBillboardName,constraints);
        constraints.gridy=1;
        pnlScheduleNewBillboard.add(txtScheduledTime,constraints);
        constraints.gridy=3;
        pnlScheduleNewBillboard.add(txtDuration,constraints);
        constraints.gridy=4;
        pnlScheduleNewBillboard.add(txtReoccurType,constraints);
        constraints.gridy=5;
        pnlScheduleNewBillboard.add(txtReoccurAmount,constraints);

        constraints.gridx=0;
        constraints.gridy=6;
        pnlScheduleNewBillboard.add(btnSubmit,constraints);

        getContentPane().add(pnlScheduleNewBillboard);
        //set the location of the GUI
        setLocation(900, 350);

        //make changes and then send to GUI
        pack();
        setVisible(true);

    }

    private boolean Check_Valid_Inputs()
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BillboardTitle = txtBillboardName.getText();
        try
        {
            Date date = format.parse(txtScheduledTime.getText());
            int Duration = Integer.parseInt(txtDuration.getText());
            int ReoccurType = Integer.parseInt(txtReoccurType.getText());
            int ReoccurAmount = Integer.parseInt(txtReoccurAmount.getText());
            if (Duration <= 0 || ReoccurType < 0 || ReoccurType > 3)
            {
                return false;
            }
            else if (ReoccurType == 3 && ReoccurAmount < Duration)
            {
                return false;
            }
        }
        catch (Exception e)//NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}

