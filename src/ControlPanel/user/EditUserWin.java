package ControlPanel.user;

import ControlPanel.Client;
import ControlPanel.Main;
import ControlPanel.User;
import Server.Request.EditUserRequest;
import Server.Request.SearchRequest;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ConnectException;
import javax.swing.*;

/**
 * @author Nicholas Tseng
 * This is the edit user window class extends JFrame. In this window,
 * users can edit an existed user information beside the user name.
 */
public class EditUserWin extends JFrame{
    // Initialize the components in the window.
    private JLabel labelUserName = new JLabel("User Name");
    private JLabel labelPassword = new JLabel("Selected password");
    private JLabel labelPermission1 = new JLabel("Create billboards");
    private JLabel labelPermission2 = new JLabel("Edit billboards");
    private JLabel labelPermission3 = new JLabel("Schedule billboards");
    private JLabel labelPermission4 = new JLabel("Edit users");
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
    public User editedUser = new User();

    /**
     * This is the constructor which will create the edit user window.
     */
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
            else if (userNameTextField.getText().equals(Main.loginUser.getUserName())){
                JOptionPane.showMessageDialog(null,
                        "Administrators are not allow to change their own permission");
            }
            else{
                SearchRequest searchRequest = new SearchRequest(Main.loginUser.getSessionToken(), userNameTextField.getText());

                try {
                    Client.connectServer(searchRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                if(Client.isRequestState()){
                    userNameTextField.setEditable(false);
                    passwordTextField.setEditable(true);
                    checkBox1.setEnabled(true);
                    checkBox2.setEnabled(true);
                    checkBox3.setEnabled(true);
                    checkBox4.setEnabled(true);
                    editButton.setEnabled(true);
                    checkBox1.setSelected(editedUser.getCreateBillboardsPermission());
                    checkBox2.setSelected(editedUser.getEditAllBillboardPermission());
                    checkBox3.setSelected(editedUser.getScheduleBillboardsPermission());
                    checkBox4.setSelected(editedUser.getEditUsersPermission());
                }
                else{
                    JOptionPane.showMessageDialog(null, "User name does not exist.");
                }
            }
        };
        searchButton.addActionListener(searchListener);

        ActionListener editListener = e ->{
            if (passwordTextField.getText().isEmpty()){
                EditUserRequest editUserRequest = new EditUserRequest(userNameTextField.getText(), checkBox1.isSelected(), checkBox2.isSelected(),
                checkBox3.isSelected(), checkBox4.isSelected(), !passwordTextField.getText().isEmpty());

                try {
                    Client.connectServer(editUserRequest);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                if(Client.isRequestState()){
                    userNameTextField.setEditable(true);
                    passwordTextField.setEditable(false);
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox1.setSelected(false);
                    checkBox2.setSelected(false);
                    checkBox3.setSelected(false);
                    checkBox4.setSelected(false);
                    editButton.setEnabled(false);
                    userNameTextField.setText("");
                    passwordTextField.setText("");
                    JOptionPane.showMessageDialog(null, "Edit successful!");
                }
            }
            else{
                EditUserRequest editUserRequest = new EditUserRequest(Main.loginUser.getSessionToken(), userNameTextField.getText(),
                        passwordTextField.getText(), checkBox1.isSelected(), checkBox2.isSelected(),
                        checkBox3.isSelected(), checkBox4.isSelected(), !passwordTextField.getText().isEmpty());

                try {
                    Client.connectServer(editUserRequest);

                    if(Client.isRequestState()){
                        userNameTextField.setEditable(true);
                        passwordTextField.setEditable(false);
                        checkBox1.setEnabled(false);
                        checkBox2.setEnabled(false);
                        checkBox3.setEnabled(false);
                        checkBox4.setEnabled(false);
                        editButton.setEnabled(false);
                        checkBox1.setSelected(false);
                        checkBox2.setSelected(false);
                        checkBox3.setSelected(false);
                        checkBox4.setSelected(false);
                        editButton.setEnabled(false);
                        userNameTextField.setText("");
                        passwordTextField.setText("");
                        JOptionPane.showMessageDialog(null, "Edit successful!");
                    }
                    else{
                        throw new Exception();
                    }
                } catch(ConnectException ex){
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Edit fail!");
                }
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
        panel.add(labelPermission1, constraints);

        constraints.gridx = 1;
        panel.add(checkBox1, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(labelPermission2, constraints);

        constraints.gridx = 1;
        panel.add(checkBox2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(labelPermission3, constraints);

        constraints.gridx = 1;
        panel.add(checkBox3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(labelPermission4, constraints);

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
        pack();
    }
}
