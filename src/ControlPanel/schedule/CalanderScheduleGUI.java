package ControlPanel.schedule;

import ControlPanel.Main;
import ControlPanel.billboard.CreateBillboardGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class CalanderScheduleGUI extends JFrame {

    private JButton btnBillboardScheduler = new JButton();
    private JPanel pnlScheduleMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;


    public CalanderScheduleGUI() {
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

        //GET SQL DATA ABOUT SCHEDULES Request from database
        int Min_in_Millis = 60000;
        int Hour_in_Millis = 60 * Min_in_Millis;
        int Day_in_Millis = 24 * Hour_in_Millis;

        long CurrentTimeMillis = System.currentTimeMillis();

        lbl1 = createLabel(CurrentTimeMillis);
        lbl2 = createLabel(CurrentTimeMillis + 1 * Day_in_Millis);
        lbl3 = createLabel(CurrentTimeMillis + 2 * Day_in_Millis);
        lbl4 = createLabel(CurrentTimeMillis + 3 * Day_in_Millis);
        lbl5 = createLabel(CurrentTimeMillis + 4 * Day_in_Millis);
        lbl6 = createLabel(CurrentTimeMillis + 5 * Day_in_Millis);
        lbl7 = createLabel(CurrentTimeMillis + 6 * Day_in_Millis);

        //Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        int Buffer = 10;

        EmptyBorder border = new EmptyBorder(Buffer, Buffer, Buffer, Buffer);

        // set the border of this component
        lbl1.setBorder(border);
        lbl2.setBorder(border);
        lbl3.setBorder(border);
        lbl4.setBorder(border);
        lbl5.setBorder(border);
        lbl6.setBorder(border);
        lbl7.setBorder(border);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        pnlScheduleMenu.add(btnBillboardScheduler, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;

        pnlScheduleMenu.add(lbl1);
        constraints.gridy = 2;
        pnlScheduleMenu.add(lbl2);
        constraints.gridy = 3;
        pnlScheduleMenu.add(lbl3);
        constraints.gridy = 4;
        pnlScheduleMenu.add(lbl4);
        constraints.gridy = 5;
        pnlScheduleMenu.add(lbl5);
        constraints.gridy = 6;
        pnlScheduleMenu.add(lbl6);
        constraints.gridy = 7;
        pnlScheduleMenu.add(lbl7);

        getContentPane().add(pnlScheduleMenu);
        // Display the window
        setLocation(900, 350);
        pack();
        repaint();
        setVisible(true);
    }

    private JLabel createLabel(long millis) {
        Date datePrint = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM E");
        JLabel label = new JLabel();
        label.setText(formatter.format(datePrint));
        return label;
    }
}

