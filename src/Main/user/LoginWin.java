package Main.user;

import Main.Main;
import Server.*;
import Server.Request.LoginRequest;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class LoginWin extends JFrame implements Runnable{
    private JLabel labelUserID = new JLabel("User ID");
    private JLabel labelPassword= new JLabel("Password");
    private JTextField idTextField = new JTextField(20);
    private JPasswordField passwordTextField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public LoginWin(){
        // Setting default value of the frame
        super("Login");
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //Button setting
        ActionListener loginListener = e ->{
            LoginRequest loginRequest = new LoginRequest(idTextField.getText(), String.valueOf(passwordTextField.getPassword()));
            try {
                Client.main(loginRequest);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }


            if (Client.isRequestState()){
                super.dispose();
                Main.menuWin.setVisible(true);
                Main.userManagementWin.permission(true);
            }
        };
        loginButton.addActionListener(loginListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUserID, constraints);

        constraints.gridx = 1;
        panel.add(idTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        constraints.gridx = 1;
        panel.add(passwordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }

    @Override
    public void run() {
        createGUI();
    }
}
