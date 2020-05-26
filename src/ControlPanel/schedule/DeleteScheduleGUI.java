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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import javax.swing.*;


public class DeleteScheduleGUI extends JFrame {
    private JPanel pnlDeleteSchedule = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private JButton btnDelete = new JButton("Delete");

    private JLabel lblBillboardName = new JLabel("Billboard Name:");
    private JLabel lblScheduledTime = new JLabel("<html>Schedule Time:<br/>yyyy-MM-dd HH:mm:ss</html>");


    private JTextField txtBillboardName = new JTextField(20);
    private JTextField txtScheduledTime = new JTextField(20);



    public DeleteScheduleGUI(){
        super("Delete Schedule");
        createGUI();
    }
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        btnDelete.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check_Valid_Inputs())
                {
                    //System.out.println("Correct Inputs");
                    //Create Schedule Requests
                    DeleteScheduleRequest temp = new DeleteScheduleRequest(txtBillboardName.getText(), txtScheduledTime.getText());
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
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Invalid Date Format");
                }
            }
        });





        //add labels to panel
        constraints.gridx=0;
        constraints.gridy=0;

        pnlDeleteSchedule.add(lblBillboardName,constraints);
        constraints.gridy=1;
        pnlDeleteSchedule.add(lblScheduledTime,constraints);
        constraints.gridx=1;
        constraints.gridy=0;

        pnlDeleteSchedule.add(txtBillboardName,constraints);
        constraints.gridy=1;
        pnlDeleteSchedule.add(txtScheduledTime,constraints);

        constraints.gridx=0;
        constraints.gridy=2;
        pnlDeleteSchedule.add(btnDelete,constraints);

        getContentPane().add(pnlDeleteSchedule);
        //set the location of the GUI
        setLocation(900, 350);

        //make changes and then send to GUI
        pack();
        setVisible(true);
    }
    //need to work from here
    private boolean Check_Valid_Inputs()
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BillboardTitle = txtBillboardName.getText();
        try
        {
            Date date = format.parse(txtScheduledTime.getText());
        }
        catch (Exception e)//NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}

