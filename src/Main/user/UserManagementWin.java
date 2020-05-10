package Main.user;

import Main.Main;
import Main.user.CreateUserWin;
import Main.user.DeleteUserWin;
import Main.user.EditUserWin;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class UserManagementWin extends JFrame{
    private JButton createUserButton = new JButton("Create a new user");
    private JButton listUserButton = new JButton("List all users");
    private JButton editUserButton = new JButton("Edit user");
    private JButton deleteUserButton = new JButton("Delete user");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

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
                Main.meauWin.setEnabled(true);
                Main.meauWin.setVisible(true);
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

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
    }
}
