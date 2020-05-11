package Main.billboard;

import Main.user.CreateUserWin;
import Main.user.DeleteUserWin;
import Main.user.EditUserWin;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BillBoardManagementGUI extends JFrame{
    private JButton btnCreateEditBB;
    private JButton btnInfoBB;
    private JButton btnListBB;
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public BillBoardManagementGUI(){
        super("Billboard Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set up buttons
        btnCreateEditBB = createButton("Create or Edit Billboard");
        btnInfoBB = createButton("Billboard Info");
        btnListBB = createButton("List Existing Billboards");

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(btnCreateEditBB, constraints);

        constraints.gridx = 1;
        panel.add(btnInfoBB, constraints);

        constraints.gridx = 2;
        panel.add(btnListBB, constraints);


        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }

    private JButton createButton (String text){
        JButton button = new JButton();
        button.setText(text);
        return button;
    }
}