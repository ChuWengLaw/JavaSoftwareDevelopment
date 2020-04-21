package Main;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreateNewUserWin extends JFrame{
        private JLabel labelUserLName = new JLabel("User Last Name");
        private JLabel labelUserFName = new JLabel("User First Name");
        private JLabel labelPassword = new JLabel("Selected password");
        private JTextField userFNameTextFiled = new JTextField(20);
        private JTextField userLNameTextFiled = new JTextField(20);
        private JPasswordField passwordTextField = new JPasswordField(20);
        private JButton createButton = new JButton("Create");
        private JButton cancelButton = new JButton("Cancel");
        private JPanel panel = new JPanel(new GridBagLayout());
        private GridBagConstraints constraints = new GridBagConstraints();

        public CreateNewUserWin(){
                // Setting default value of the frame
                super("Create New Account");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Button setting
                User user = new User();
                ActionListener createListener = e -> {
                        user.setUserFName(userFNameTextFiled.getText());
                        user.setUserLName(userLNameTextFiled.getText());
                        user.setPassword(passwordTextField.getPassword());
                };
                createButton.addActionListener(createListener);

                ActionListener cancelListener = e -> {
                        super.dispose();
                        new LoginWin();
                };
                cancelButton.addActionListener(cancelListener);


                // Panel setting
                constraints.anchor = GridBagConstraints.WEST;
                constraints.insets = new Insets(10, 10, 10, 10);

                constraints.gridx = 0;
                constraints.gridy = 0;
                panel.add(labelUserFName, constraints);

                constraints.gridx = 1;
                panel.add(userFNameTextFiled, constraints);

                constraints.gridx = 0;
                constraints.gridy = 1;
                panel.add(labelUserLName, constraints);

                constraints.gridx = 1;
                panel.add(userLNameTextFiled, constraints);

                constraints.gridx = 0;
                constraints.gridy = 2;
                panel.add(labelPassword, constraints);

                constraints.gridx = 1;
                panel.add(passwordTextField, constraints);

                constraints.gridx = 1;
                constraints.gridy = 3;
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
}
