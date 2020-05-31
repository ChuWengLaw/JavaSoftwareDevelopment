package ControlPanel.user;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.User;
import Server.Request.SearchRequest;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ConnectException;
import javax.swing.*;

/**
 * This class creates the GUI which allows the user
 * to retrieve the list of permissions of the entered user
 *
 * @author "Kenji" Foo Shiang Xun
 */

public class SearchUserInfoWin extends JFrame {
    private JLabel labelUserName = new JLabel("Username");
    private JLabel labelCreateBBPermission = new JLabel("Create billboards");
    private JLabel labelEditBBPermission = new JLabel("Edit billboards");
    private JLabel labelSchedulePermission = new JLabel("Schedule billboards");
    private JLabel labelEditUserPermission = new JLabel("Edit users");
    private JTextField userNameTextField = new JTextField(20);
    private JCheckBox checkBoxCreate = new JCheckBox("Enable");
    private JCheckBox checkBoxEditBB = new JCheckBox("Enable");
    private JCheckBox checkBoxScheduleBB = new JCheckBox("Enable");
    private JCheckBox checkBoxEditUser = new JCheckBox("Enable");
    private JButton searchButton = new JButton("Search");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();
    public User searchUser = new User();

    /**
     * Constructor initialises the GUI creation as well as set the
     * window listener
     */
    public SearchUserInfoWin() {
        super("Search User Info");
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
                userNameTextField.setText("");
                checkBoxCreate.setSelected(false);
                checkBoxEditBB.setSelected(false);
                checkBoxScheduleBB.setSelected(false);
                checkBoxEditUser.setSelected(false);
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

        //Disabling checkboxes
        checkBoxCreate.setEnabled(false);
        checkBoxEditBB.setEnabled(false);
        checkBoxScheduleBB.setEnabled(false);
        checkBoxEditUser.setEnabled(false);

        //Adding actionlistener to the search button
        ActionListener searchListener = e -> {
            if (userNameTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "User name field can't be empty.");
            } else {
                //Sending the search request to server to retreive the list of permissions of entered user from the database
                SearchRequest searchRequest = new SearchRequest(Main.loginUser.getSessionToken(), userNameTextField.getText(), false);

                try {
                    Client.connectServer(searchRequest);

                    if (Client.isRequestState()) {
                        //Displaying the list of permission of the entered user with the checkboxes
                        checkBoxCreate.setSelected(searchUser.getCreateBillboardsPermission());
                        checkBoxEditBB.setSelected(searchUser.getEditAllBillboardPermission());
                        checkBoxScheduleBB.setSelected(searchUser.getScheduleBillboardsPermission());
                        checkBoxEditUser.setSelected(searchUser.getEditUsersPermission());
                    } else {
                        throw new Exception();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "User name does not exist.");
                }
            }
        };
        searchButton.addActionListener(searchListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserName, constraints);
        constraints.gridy = 1;
        panel.add(labelCreateBBPermission, constraints);
        constraints.gridy = 2;
        panel.add(labelEditBBPermission, constraints);
        constraints.gridy = 3;
        panel.add(labelSchedulePermission, constraints);
        constraints.gridy = 4;
        panel.add(labelEditUserPermission, constraints);
        constraints.gridy = 5;
        panel.add(searchButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(userNameTextField, constraints);
        constraints.gridy = 1;
        panel.add(checkBoxCreate, constraints);
        constraints.gridy = 2;
        panel.add(checkBoxEditBB, constraints);
        constraints.gridy = 3;
        panel.add(checkBoxScheduleBB, constraints);
        constraints.gridy = 4;
        panel.add(checkBoxEditUser, constraints);

        getContentPane().add(panel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);
        pack();

    }
}
