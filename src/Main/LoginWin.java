package Main;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginWin extends JFrame{
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
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Button setting
            User user = new User();
            ActionListener loginListener = e ->{
                dispose();
                user.setUserName(idTextField.getText());
                user.setPassword(passwordTextField.getPassword());
                new MeauWin();
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
}
