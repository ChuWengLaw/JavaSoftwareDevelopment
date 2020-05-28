package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.billboard.CreateBillboardGUI;
import Server.Request.CreateBBRequest;
import Server.Request.ScheduleBillboardRequest;

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
    //private JTextField txtReoccurType = new JTextField(20);
    private JTextField txtReoccurAmount = new JTextField(20);

    JComboBox ReoccureType = new JComboBox();



    public ScheduleBillboardGUI(){
        super("Schedule Billboard");
        createGUI();
    }
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ReoccureType.addItem("None");
        ReoccureType.addItem("1 Day");
        ReoccureType.addItem("1 Hour");
        ReoccureType.addItem("(Below) Minutes");

        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check_Valid_Inputs())
                {
                    String value = ReoccureType.getSelectedItem().toString();
                    if (value.equals("None")) value = "0";
                    else if (value.equals("1 Day")) value = "1";
                    else if (value.equals("1 Hour")) value = "2";
                    else if (value.equals("(Below) Minutes")) value = "3";
                    //System.out.println("Correct Inputs");
                    //Create Schedule Requests
                    ScheduleBillboardRequest temp = new ScheduleBillboardRequest(txtBillboardName.getText(), txtScheduledTime.getText(),
                            txtDuration.getText(), value, txtReoccurAmount.getText());
                    try {
                        Client.connectServer(temp);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    txtBillboardName.setText("");
                    txtScheduledTime.setText("");
                    txtDuration.setText("");
                    //ReoccureType.;
                    txtReoccurAmount.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect Inputs");
                }
            }
        });



        constraints.insets = new Insets(10,10,10,10);

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
        pnlScheduleNewBillboard.add(ReoccureType,constraints);
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
            if (txtReoccurAmount.getText().equals(""))
            {
                txtReoccurAmount.setText("0");
            }
            Date date = format.parse(txtScheduledTime.getText());
            int Duration = Integer.parseInt(txtDuration.getText());
            int ReoccurAmount = Integer.parseInt(txtReoccurAmount.getText());
            if (Duration <= 0)// || ReoccurType < 0 || ReoccurType > 3)
            {
                return false;
            }
            else if (ReoccureType.getSelectedItem().toString().equals("(Below) Minutes") && ReoccurAmount < Duration)
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

