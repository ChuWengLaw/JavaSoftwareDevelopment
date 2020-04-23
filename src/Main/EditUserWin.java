package Main;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EditUserWin extends JFrame{
    private JLabel label = new JLabel("Please insert the user name of the user you want to edit");
    private JLabel labelUserName = new JLabel("User Name");
    private JTextField userNameTextField = new JTextField(20);
    private JButton editButton = new JButton("Edit");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public EditUserWin(){
        // Setting default value of the frame
        super("Edit User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button setting

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label);

        constraints.gridy = 1;
        panel.add(labelUserName, constraints);

        constraints.gridx = 1;
        panel.add(userNameTextField, constraints);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridy = 2;
        panel.add(editButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }
}
