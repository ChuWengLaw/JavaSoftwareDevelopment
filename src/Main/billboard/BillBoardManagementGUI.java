package Main.billboard;

import java.awt.*;
import javax.swing.*;

public class BillBoardManagementGUI extends JFrame{
    private JButton btnCreateEditBB;
    private JButton btnInfoBB;
    private JButton btnListBB;
    private JPanel bBMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * create the BillboardManagement GUI
     * @Author Lachlan
     */
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
        bBMenu.add(btnCreateEditBB, constraints);

        constraints.gridx = 1;
        bBMenu.add(btnInfoBB, constraints);

        constraints.gridx = 2;
        bBMenu.add(btnListBB, constraints);

        getContentPane().add(bBMenu);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }

    /**
     * Creates the button
     * @author Lachlan
     * @param text the text on the button
     * @return the formatted button
     */
    private JButton createButton (String text){
        JButton button = new JButton();
        button.setText(text);
        return button;
    }
}