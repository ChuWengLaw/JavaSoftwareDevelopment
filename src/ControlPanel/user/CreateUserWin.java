package ControlPanel.user;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ConnectException;
import javax.swing.*;

/**
 * @author Nicholas Tseng
 * This is the create user window class extends JFrame. In this window,
 * users can create a new user.
 */
public class CreateUserWin extends JFrame {
    // Initialize the components in the window.
    private JLabel labelUserName = new JLabel("User Name");
    private JLabel labelPassword = new JLabel("Selected password");
    private JLabel labelpermission1 = new JLabel("Create billboards");
    private JLabel labelpermission2 = new JLabel("Edit billboards");
    private JLabel labelpermission3 = new JLabel("Schedule billboards");
    private JLabel labelpermission4 = new JLabel("Edit users");
    private JTextField userNameTextField = new JTextField(20);
    private JPasswordField passwordTextField = new JPasswordField(20);
    private JCheckBox checkBox1 = new JCheckBox("Enable");
    private JCheckBox checkBox2 = new JCheckBox("Enable");
    private JCheckBox checkBox3 = new JCheckBox("Enable");
    private JCheckBox checkBox4 = new JCheckBox("Enable");
    private JButton createButton = new JButton("Create");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * This is the constructor which will create the create user window.
     */
    public CreateUserWin() {
        // Setting default value of the frame
        super("Create New Account");
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
                Main.userManagementWin.setEnabled(true);
                Main.userManagementWin.setVisible(true);
                userNameTextField.setText("");
                passwordTextField.setText("");
                checkBox1.setSelected(false);
                checkBox2.setSelected(false);
                checkBox3.setSelected(false);
                checkBox4.setSelected(false);
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

        // Button setting
        ActionListener createListener = e -> {
            if (userNameTextField.getText().isEmpty() || passwordTextField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "User name and password field cannot be empty");
            } else {
                CreateUserRequest createUserRequest = new CreateUserRequest(Main.loginUser.getSessionToken(),
                        userNameTextField.getText(), String.valueOf(passwordTextField.getPassword()), checkBox1.isSelected(),
                        checkBox2.isSelected(), checkBox3.isSelected(), checkBox4.isSelected());

                try {
                    Client.connectServer(createUserRequest);

                    if (Client.isRequestState()) {
                        JOptionPane.showMessageDialog(null, "User creation successful!");
                        checkBox1.setSelected(false);
                        checkBox2.setSelected(false);
                        checkBox3.setSelected(false);
                        checkBox4.setSelected(false);
                        userNameTextField.setText("");
                        passwordTextField.setText("");
                    } else {
                        throw new Exception();
                    }
                } catch (ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "User name existed!");
                }
            }
        };
        createButton.addActionListener(createListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserName, constraints);

        constraints.gridx = 1;
        panel.add(userNameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        constraints.gridx = 1;
        panel.add(passwordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(labelpermission1, constraints);

        constraints.gridx = 1;
        panel.add(checkBox1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelpermission2, constraints);

        constraints.gridx = 1;
        panel.add(checkBox2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelpermission3, constraints);

        constraints.gridx = 1;
        panel.add(checkBox3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(labelpermission4, constraints);

        constraints.gridx = 1;
        panel.add(checkBox4, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(createButton, constraints);

        getContentPane().add(panel);

        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation(width / 4, height / 4);
        pack();
    }
}
