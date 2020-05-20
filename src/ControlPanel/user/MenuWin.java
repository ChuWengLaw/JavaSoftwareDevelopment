
package ControlPanel.user;

import ControlPanel.Main;
import ControlPanel.billboard.BillBoardManagementGUI;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author Nicholas Tseng
 * This is the menu window class which extends JFrame. In this window,
 * users can access three different functions:
 *
 * 1. Billboard management (required permission: at least one of the billboard permission).
 * 2. User management (requierd permission: edit user permission).
 * 3. User profile (required permission: none).
 *
 * If users are not allow to access any of the function due to permission issue,
 * the button will be disable from them.
 */
public class MenuWin extends JFrame {
    /**
     * Initialize the components in the window.
     */
    private JButton billboardManageButton = new JButton("Billboard management");
    private JButton editUserButton = new JButton("User management");
    private JButton userProfileButton = new JButton("User profile");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * This is the constructor which will create the menu window.
     */
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

        ActionListener userProfileListener = e -> {
            Main.userProfileWin.setVisible(true);
            Main.userProfileWin.setCheckBox(Main.loginUser.getCreateBillboardsPermission(), Main.loginUser.getEditAllBillboardPermission(),
                    Main.loginUser.getScheduleBillboardsPermission(), Main.loginUser.getEditUsersPermission());
            super.setEnabled(false);
        };
        userProfileButton.addActionListener(userProfileListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(editUserButton, constraints);

        constraints.gridx = 1;
        panel.add(billboardManageButton, constraints);

        constraints.gridx = 2;
        panel.add(userProfileButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900, 350);
        pack();
    }

    /**
     * This is a method that setup the enability of the edit user button
     * depends on the edit user permission
     *
     * @param editUserPermission The edit user permission of the login user.s
     */
    public void userManagementEnable(boolean editUserPermission) {
        editUserButton.setEnabled(editUserPermission);
    }
}