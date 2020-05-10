package Main.user;

import Main.Main;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.*;

public class CreateUserWin extends JFrame{
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

        public CreateUserWin(){
                // Setting default value of the frame
                super("Create New Account");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Window Listener
                WindowListener windowListener = new WindowListener() {
                        @Override
                        public void windowOpened(WindowEvent e) {}

                        @Override
                        public void windowClosing(WindowEvent e) {}

                        @Override
                        public void windowClosed(WindowEvent e) {
                                Main.userManagementWin.setEnabled(true);
                                Main.userManagementWin.setVisible(true);
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
                ActionListener createListener = e -> {
                        try {
                                if(CheckUserSQL(userNameTextField.getText())){
                                        JOptionPane.showMessageDialog(null,"User name already exists");
                                }
                                else if(userNameTextField.getText().isEmpty() || passwordTextField.getPassword().length == 0){
                                        JOptionPane.showMessageDialog(null,"User name and password field cannot be empty");
                                }
                                else{
                                        CreateUserSQL(userNameTextField.getText(), String.valueOf(passwordTextField.getPassword()), checkBox1.isSelected(),
                                                checkBox2.isSelected(), checkBox3.isSelected(), checkBox4.isSelected());
                                }

                        } catch (SQLException ex) {
                                ex.printStackTrace();
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
                setLocation(900,350);
                pack();
        }

        private void CreateUserSQL(String userName, String userPassword,
                                   boolean createBillboardsPermission, boolean editAllBillboardPermission,
                                   boolean scheduleBillboardsPermission, boolean editUsersPermission) throws SQLException {
                PreparedStatement Pstatement = Main.connection.prepareStatement("INSERT INTO user  " +
                        "(userName, userPassword,  createBillboardsPermission, editAllBillboardPermission, scheduleBillboardsPermission, editUsersPermission) " +
                        "VALUES (?, ?, ?, ?, ?, ?)");
                Pstatement.setString(1, userName);
                Pstatement.setString(2, userPassword);
                Pstatement.setBoolean(3, createBillboardsPermission);
                Pstatement.setBoolean(4, editAllBillboardPermission);
                Pstatement.setBoolean(5, scheduleBillboardsPermission);
                Pstatement.setBoolean(6, editUsersPermission);
                Pstatement.executeUpdate();
                Pstatement.close();
        }

        private boolean CheckUserSQL(String userName) throws SQLException {
                boolean existing = false;

                Statement statement = Main.connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT userName FROM  user");

                while(resultSet.next()){
                        if (userName.equals(resultSet.getString(1))){
                                existing = true;
                                break;
                        }
                }

                statement.close();
                return existing;
        }
}
