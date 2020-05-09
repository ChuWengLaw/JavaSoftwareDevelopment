package Main;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Set;
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
        private JButton cancelButton = new JButton("Cancel");
        private JPanel panel = new JPanel(new GridBagLayout());
        private GridBagConstraints constraints = new GridBagConstraints();

        public CreateUserWin(){
                // Setting default value of the frame
                super("Create New Account");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                User user = new User();

                // Button setting
                ActionListener createListener = e -> {
                        try {
                                if(CheckUserSQL(userNameTextField.getText())){
                                        new ErrorWin("User Name exists");
                                }
                                else if(userNameTextField.getText().isEmpty() || passwordTextField.getPassword().length == 0){
                                        new ErrorWin("There is an empty filed");
                                }
                                else{
                                        SetUser(user);
                                        CreateUserSQL(user.getUserName(), user.getUserPassword(), user.getCreateBillboardsPermission(),
                                                user.getEditAllBillboardPermission(), user.getScheduleBillboardsPermission(), user.getEditUsersPermission());
                                }

                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                };
                createButton.addActionListener(createListener);

                ActionListener cancelListener = e -> {
                        super.dispose();
                };
                cancelButton.addActionListener(cancelListener);

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
                constraints.anchor = GridBagConstraints.CENTER;
                panel.add(createButton, constraints);

                constraints.gridx = 0;
                constraints.anchor = GridBagConstraints.EAST;
                panel.add(cancelButton, constraints);

                getContentPane().add(panel);

                // Display the window
                setLocation(900,350);
                pack();
                setVisible(true);
        }

        private void SetUser(User user){
                user.setUserName(userNameTextField.getText());
                user.setPassword(passwordTextField.getPassword());

                if (checkBox1.isSelected()){
                        user.setCreateBillboardsPermission(true);
                }
                else {
                        user.setCreateBillboardsPermission(false);
                }

                if (checkBox2.isSelected()){
                        user.setEditAllBillboardsPermission(true);
                }
                else {
                        user.setEditAllBillboardsPermission(false);
                }

                if (checkBox3.isSelected()){
                        user.setScheduleBillboardsPermission(true);
                }
                else {
                        user.setScheduleBillboardsPermission(false);
                }

                if (checkBox4.isSelected()){
                        user.setEditUsersPermission(true);
                }
                else {
                        user.setEditUsersPermission(false);
                }
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
