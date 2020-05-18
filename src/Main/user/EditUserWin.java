package Main.user;

import Main.Main;
import Server.Server;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class EditUserWin extends JFrame{
    private JLabel labelUserName = new JLabel("User Name");
    private JLabel labelPassword = new JLabel("Selected password");
    private JLabel labelpermission1 = new JLabel("Create billboards");
    private JLabel labelpermission2 = new JLabel("Edit billboards");
    private JLabel labelpermission3 = new JLabel("Schedule billboards");
    private JLabel labelpermission4 = new JLabel("Edit users");
    private JTextField userNameTextField = new JTextField(20);
    private JTextField passwordTextField = new JTextField(20);
    private JCheckBox checkBox1 = new JCheckBox("Enable");
    private JCheckBox checkBox2 = new JCheckBox("Enable");
    private JCheckBox checkBox3 = new JCheckBox("Enable");
    private JCheckBox checkBox4 = new JCheckBox("Enable");
    private JButton searchButton= new JButton("Search");
    private JButton editButton = new JButton("Edit");
    private JButton cancelButton = new JButton("Cancel");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public EditUserWin(){
        // Setting default value of the frame
        super("Edit User");
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

        // Text field and checkbox setting
        passwordTextField.setEditable(false);
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        checkBox4.setEnabled(false);
        editButton.setEnabled(false);

        // Button setting
        ActionListener searchListener = e -> {
            if(userNameTextField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"User name field can't be empty.");
            }
            else if (userNameTextField.getText().equals(Main.user.getUserName())){
                JOptionPane.showMessageDialog(null,
                        "Administrators are not allow to change their own permission");
            }
            else{
                try {
                    if (!checkUserSQL(userNameTextField.getText())){
                        JOptionPane.showMessageDialog(null,"User name does not exists.");
                    }
                    else{
                        setUserSQL();
                        userNameTextField.setEditable(false);
                        passwordTextField.setEditable(true);
                        checkBox1.setEnabled(true);
                        checkBox2.setEnabled(true);
                        checkBox3.setEnabled(true);
                        checkBox4.setEnabled(true);
                        editButton.setEnabled(true);
                        searchButton.setEnabled(false);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        searchButton.addActionListener(searchListener);

        ActionListener editListener = e ->{
            try{
                if (passwordTextField.getText().isEmpty()){
                    editUserSQL(userNameTextField.getText(), checkBox1.isSelected(), checkBox2.isSelected(),
                            checkBox3.isSelected(), checkBox4.isSelected());
                    userNameTextField.setEditable(true);
                    passwordTextField.setEditable(false);
                    passwordTextField.setText(null);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    editButton.setEnabled(false);
                    searchButton.setEnabled(true);
                }
                else{
                    String saltString = Server.saltString();
                    String hashPassword = Server.hashAString(passwordTextField.getText() + saltString);
                    editUserSQL(userNameTextField.getText(), hashPassword, checkBox1.isSelected(), checkBox2.isSelected(),
                            checkBox3.isSelected(), checkBox4.isSelected(), saltString);
                    userNameTextField.setEditable(true);
                    passwordTextField.setEditable(false);
                    passwordTextField.setText(null);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    editButton.setEnabled(false);
                    searchButton.setEnabled(true);
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        };
        editButton.addActionListener(editListener);

        ActionListener cancelListener = e -> {
            passwordTextField.setText(null);
            checkBox1.setSelected(false);
            checkBox2.setSelected(false);
            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
            userNameTextField.setEditable(true);
            passwordTextField.setEditable(false);
            checkBox1.setEnabled(false);
            checkBox2.setEnabled(false);
            checkBox3.setEnabled(false);
            checkBox4.setEnabled(false);
            editButton.setEnabled(false);
            searchButton.setEnabled(true);
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
        panel.add(editButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(searchButton, constraints);

        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.EAST;
        panel.add(cancelButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
    }

    private void setUserSQL() throws SQLException {
        Statement statement = Server.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT userName, createBillboardsPermission, editAllBillboardPermission, scheduleBillboardsPermission," +
                "editUsersPermission FROM  user");

        while(resultSet.next()) {
            if (userNameTextField.getText().equals(resultSet.getString(1))){
                checkBox1.setSelected(resultSet.getBoolean(2));
                checkBox2.setSelected(resultSet.getBoolean(3));
                checkBox3.setSelected(resultSet.getBoolean(4));
                checkBox4.setSelected(resultSet.getBoolean(5));
                break;
            }
        }

        statement.close();
    }

    private void editUserSQL(String userName, String userPassword,
                             boolean createBillboardsPermission, boolean editAllBillboardPermission,
                             boolean scheduleBillboardsPermission, boolean editUsersPermission, String saltValue) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("UPDATE user " +
                "SET userPassword = ?,  createBillboardsPermission = ?, editAllBillboardPermission = ?, " +
                "scheduleBillboardsPermission = ?, editUsersPermission = ?, saltValue = ? WHERE userName = ? " );
        pstatement.setString(1, userPassword);
        pstatement.setBoolean(2, createBillboardsPermission);
        pstatement.setBoolean(3, editAllBillboardPermission);
        pstatement.setBoolean(4, scheduleBillboardsPermission);
        pstatement.setBoolean(5, editUsersPermission);
        pstatement.setString(6, saltValue);
        pstatement.setString(7, userName);
        pstatement.executeUpdate();
        pstatement.close();
    }

    private void editUserSQL(String userName, boolean createBillboardsPermission, boolean editAllBillboardPermission,
                             boolean scheduleBillboardsPermission, boolean editUserPermission) throws SQLException {
        PreparedStatement pstatement = Server.connection.prepareStatement("UPDATE user " +
                "SET  createBillboardsPermission = ?, editAllBillboardPermission = ?, " +
                "scheduleBillboardsPermission = ?, editUsersPermission = ? WHERE userName = ? " );
        pstatement.setBoolean(1, createBillboardsPermission);
        pstatement.setBoolean(2, editAllBillboardPermission);
        pstatement.setBoolean(3, scheduleBillboardsPermission);
        pstatement.setBoolean(4, editUserPermission);
        pstatement.setString(5, userName);
        pstatement.executeUpdate();
        pstatement.close();
    }

    private boolean checkUserSQL(String userName) throws SQLException {
        boolean existing = false;

        Statement statement = Server.connection.createStatement();
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
