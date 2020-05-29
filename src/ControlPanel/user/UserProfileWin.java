package ControlPanel.user;

import ControlPanel.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class UserProfileWin extends JFrame {
    private JLabel labelpermission1 = new JLabel("Create billboards");
    private JLabel labelpermission2 = new JLabel("Edit billboards");
    private JLabel labelpermission3 = new JLabel("Schedule billboards");
    private JLabel labelpermission4 = new JLabel("Edit users");
    private JCheckBox checkBox1 = new JCheckBox("Enable");
    private JCheckBox checkBox2 = new JCheckBox("Enable");
    private JCheckBox checkBox3 = new JCheckBox("Enable");
    private JCheckBox checkBox4 = new JCheckBox("Enable");
    private JButton changePasswordButton = new JButton("Change password");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public UserProfileWin() {
        // Setting default value of the frame
        super("Edit User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Window Listener
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Main.menuWin.setEnabled(true);
                Main.menuWin.setVisible(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        };
        super.addWindowListener(windowListener);

        // Checkbox setting
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        checkBox4.setEnabled(false);

        // Button setting
        ActionListener changePasswordListener = e -> {
            Main.changePasswordWin.setVisible(true);
            super.setEnabled(false);
        };
        changePasswordButton.addActionListener(changePasswordListener);


        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelpermission1, constraints);

        constraints.gridx = 1;
        panel.add(checkBox1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelpermission2, constraints);

        constraints.gridx = 1;
        panel.add(checkBox2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelpermission3, constraints);

        constraints.gridx = 1;
        panel.add(checkBox3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelpermission4, constraints);

        constraints.gridx = 1;
        panel.add(checkBox4, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(changePasswordButton, constraints);

        getContentPane().add(panel);

        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
        pack();
    }

    public void setCheckBox(boolean isCheck1, boolean isCheck2, boolean isCheck3, boolean isCheck4) {
        checkBox1.setSelected(isCheck1);
        checkBox2.setSelected(isCheck2);
        checkBox3.setSelected(isCheck3);
        checkBox4.setSelected(isCheck4);
    }
}