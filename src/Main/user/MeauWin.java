package Main.user;

import Main.Main;
import Main.billboard.CreateEditGUI;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MeauWin extends JFrame{
        private JButton billboardManageButton = new JButton("Billboard management");
        private JButton editUserButton = new JButton ("User management");
        private JPanel panel = new JPanel(new GridBagLayout());
        private GridBagConstraints constraints = new GridBagConstraints();

        public MeauWin(){
            // Setting default value of the frame
            super("Menu");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Button setting
            ActionListener editUserListener = e -> new UserManagementWin();
            editUserButton.addActionListener(editUserListener);

            ActionListener editBillboardListener = e -> new CreateEditGUI("Create/Edit Billboard");
            billboardManageButton.addActionListener(editBillboardListener);

            // Panel setting
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(editUserButton, constraints);

            constraints.gridx = 1;
            panel.add(billboardManageButton, constraints);

            getContentPane().add(panel);

            // Display the window
            setLocation(900,350);
            pack();
            setVisible(true);
        }
}
