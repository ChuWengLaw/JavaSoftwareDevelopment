package ControlPanel.user;

import ControlPanel.Main;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.*;

/**
 * @author Nicholas Tseng
 * This is the user mangement window class which extends Jframe. In this window,
 * users can access to four different functions:
 *
 * 1. Create new user.
 * 2. List all user.
 * 3. Edit user.
 * 4. Delete user.
 */
public class UserManagementWin extends JFrame{
    // Initialize the components in the window.
    private JButton createUserButton = new JButton("Create a new user");
    private JButton listUserButton = new JButton("List all users");
    private JButton editUserButton = new JButton("Edit user");
    private JButton deleteUserButton = new JButton("Delete user");
    private JButton searchUserButton = new JButton("Search user");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * This is the construct which will create the user management window.
     */
    public UserManagementWin(){
        super("User Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Window Listener
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                Main.menuWin.setEnabled(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };
        super.addWindowListener(windowListener);

        // Button setting
        ActionListener createUserListener = e -> {
            Main.createUserWin.setVisible(true);
            super.setEnabled(false);
        };
        createUserButton.addActionListener(createUserListener);

        ActionListener editUserListener = e -> {
            Main.editUserWin.setVisible(true);
            super.setEnabled(false);
        };
        editUserButton.addActionListener(editUserListener);

        ActionListener deleteActionListener = e -> {
            Main.deleteUserWin.setVisible(true);
            super.setEnabled(false);
        };
        deleteUserButton.addActionListener(deleteActionListener);

        ActionListener listActionListener = e-> {
            Main.listUserWin.setVisible(true);

            Main.listUserWin.createTableSQL();

            super.setEnabled(false);
        };
        listUserButton.addActionListener(listActionListener);

        ActionListener searchActionListener = e-> {
            Main.searchUserWin.setVisible(true);
            super.setEnabled(false);
        };
        searchUserButton.addActionListener(searchActionListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(createUserButton, constraints);

        constraints.gridx = 1;
        panel.add(listUserButton, constraints);

        constraints.gridx = 2;
        panel.add(editUserButton, constraints);

        constraints.gridx =3;
        panel.add(deleteUserButton, constraints);

        constraints.gridx = 4;
        panel.add(searchUserButton, constraints);

        getContentPane().add(panel);

        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
        pack();
    }
}
