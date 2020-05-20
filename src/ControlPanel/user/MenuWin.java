package ControlPanel.user;

import ControlPanel.Main;
import ControlPanel.billboard.BillBoardManagementGUI;
import ControlPanel.schedule.CalanderScheduleGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuWin extends JFrame {
    private JButton billboardManageButton = new JButton("Billboard management");
    private JButton editUserButton = new JButton("User management");
    private JButton scheduleBillboardButton = new JButton("Schedule Billboard");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public MenuWin() {
        // Setting default value of the frame
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button setting
        ActionListener editUserListener = e -> {
            Main.userManagementWin.setVisible(true);
            super.setEnabled(false);
        };
        editUserButton.addActionListener(editUserListener);

        ActionListener editBillboardListener = e -> new BillBoardManagementGUI();
        billboardManageButton.addActionListener(editBillboardListener);

        ActionListener scheduleBillboardListener = e -> new CalanderScheduleGUI();
        scheduleBillboardButton.addActionListener(scheduleBillboardListener);



        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(editUserButton, constraints);

        constraints.gridx = 1;
        panel.add(billboardManageButton, constraints);

        constraints.gridx = 2;
        panel.add(scheduleBillboardButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900, 350);
        pack();
    }
}