package ControlPanel.schedule;

import ControlPanel.Main;
import ControlPanel.billboard.CreateBillboardGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;


public class CalanderScheduleGUI extends JFrame {

    private JButton btnBillboardScheduler = new JButton();
    private JPanel pnlScheduleMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();


    public CalanderScheduleGUI(){
        super("Scheduled Billboards");
        createGUI();
    }
    private void createGUI() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnBillboardScheduler.setText("Schedule a Billboard");

        btnBillboardScheduler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduleBillboardGUI();
            }
        });


        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        pnlScheduleMenu.add(btnBillboardScheduler, constraints);


        getContentPane().add(pnlScheduleMenu);
        // Display the window
        setLocation(900,350);
        pack();
        repaint();
        setVisible(true);

    }

}

