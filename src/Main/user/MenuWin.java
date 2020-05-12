package Main.user;

import Main.Main;
import Main.billboard.CreateEditGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuWin extends JFrame{
    private JButton billboardManageButton = new JButton("Billboard management");
    private JButton editUserButton = new JButton ("User management");
    private JButton changePasswordButton = new JButton("Change password");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public MenuWin(){
        // Setting default value of the frame
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button setting
        ActionListener editUserListener = e -> {
            Main.userManagementWin.setVisible(true);
            super.setEnabled(false);
        };
        editUserButton.addActionListener(editUserListener);

        ActionListener editBillboardListener = e -> new CreateEditGUI();
        billboardManageButton.addActionListener(editBillboardListener);

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
        panel.add(editUserButton, constraints);

        constraints.gridx = 1;
        panel.add(billboardManageButton, constraints);

        constraints.gridx = 2;
        panel.add(changePasswordButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
    }

    public void enableUserButton(User user){
        editUserButton.setEnabled(user.getEditUsersPermission());
    }
}
