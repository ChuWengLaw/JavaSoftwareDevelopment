package Main.user;

import Main.Main;
import Main.user.CreateUserWin;
import Main.user.DeleteUserWin;
import Main.user.EditUserWin;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserManagementWin extends JFrame{
    private JButton createUserButton = new JButton("Create a new user");
    private JButton listUserButton = new JButton("List all users");
    private JButton editUserButton = new JButton("Edit user");
    private JButton deleteUserButton = new JButton("Delete user");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public UserManagementWin(User user){
        super("User Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Button setting
        ActionListener createUserListener = e -> new CreateUserWin();
        createUserButton.addActionListener(createUserListener);

        ActionListener editUserListener = e -> new EditUserWin();
        editUserButton.addActionListener(editUserListener);

        ActionListener deleteActionListener = e ->{
            new DeleteUserWin(user);
        };
        deleteUserButton.addActionListener(deleteActionListener);

        ActionListener listActionListener = e-> new ListUserWin();
        listUserButton.addActionListener(listActionListener);


        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(createUserButton, constraints);

        constraints.gridx = 1;
        panel.add(listUserButton, constraints);

        constraints.gridx = 2;
        panel.add(editUserButton, constraints);

        constraints.gridx =3;
        panel.add(deleteUserButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }
}
