package ControlPanel.user;

import ControlPanel.*;
import Server.Request.LoginRequest;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import javax.swing.*;

/**
 * @author Nicholas Tseng
 * This is the login window class extends JFrame. In this window,
 * users can login by typing their username and password. If the login fail,
 * a message window will pop up and users can try login again.
 */
public class LoginWin extends JFrame implements Runnable{
    /**
     * Initialize the components in the window.
     */
    private JLabel labelUserID = new JLabel("User ID");
    private JLabel labelPassword= new JLabel("Password");
    private JTextField idTextField = new JTextField(20);
    private JPasswordField passwordTextField = new JPasswordField(20);
    private JButton loginButton = new JButton("Login");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * This is the constructor which will create the login window.
     */
    public LoginWin(){
        // Setting default value of the frame.
        super("Login");
    }

    /**
     * This is the actual method that create the GUI of the login window.
     * It is implemented in such way that it can be invoked later
     * and the program will not crash easily.
     */
    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //Button setting
        ActionListener loginListener = e ->{
            LoginRequest loginRequest = new LoginRequest(idTextField.getText(), String.valueOf(passwordTextField.getPassword()));
            try {
                Client.connectServer(loginRequest);

                // Check if the login request is successfully executed, if so, send an acknowledgement.
                // The user management button will be set either enable or disable according to
                // login user's edit permission.
                if (Client.isRequestState()){
                    super.dispose();
                    Main.menuWin.setVisible(true);
                    Main.menuWin.userManagementEnable(Main.loginUser.getEditUsersPermission());
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
                JOptionPane.showMessageDialog(null,"Invalid user name or password");
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
        pack();
        setVisible(true);
    }

    /**
     * Override the method run(), it will call the method createGUI().
     */
    @Override
    public void run() {
        createGUI();
    }
}
