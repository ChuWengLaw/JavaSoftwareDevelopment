package ControlPanel.user;

import ControlPanel.*;
import Server.*;
import Server.Request.DeleteUserRequest;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;

public class DeleteUserWin extends JFrame {
    private JLabel usernamelabel = new JLabel("Username:");
    private JTextField usernamefield = new JTextField(20);
    private JButton deletebutton = new JButton("Delete");
    private JPanel deletepanel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public DeleteUserWin() {
        super("Delete a User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Main.userManagementWin.setEnabled(true);
                Main.userManagementWin.setVisible(true);
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

        ActionListener listener = e -> {
            if (usernamefield.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty");
            } else {
                DeleteUserRequest deleteUser = new DeleteUserRequest(usernamefield.getText());
                try {
                    Client.connectServer(deleteUser);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (Client.isRequestState()) {
                    JOptionPane.showMessageDialog(null, "User has been deleted");
                    System.out.println(Client.isRequestState());
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist");
                }
                usernamefield.setText("");
            }

        };

        deletebutton.addActionListener(listener);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        deletepanel.add(usernamelabel, constraints);

        constraints.gridx = 1;
        deletepanel.add(usernamefield, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        deletepanel.add(deletebutton, constraints);

        getContentPane().add(deletepanel);

        setLocation(900, 350);
        pack();
    }
}