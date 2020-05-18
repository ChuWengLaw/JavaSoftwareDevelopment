package Main.billboard;

import Main.Main;
import Server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class BillBoardManagementGUI extends JFrame {

    //define the buttons
    private JButton btnCreateBB;
    private JButton btnEditBB;
    private JButton btnInfoBB;
    private JButton btnListBB;

    //define the JPanel and the GridBagConstraints
    private JPanel bBMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public BillBoardManagementGUI(){
        super("Billboard Management");
        createGUI();
    }
    private void createGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Window Listener
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                Server.menuWin.setEnabled(false);}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                Server.menuWin.setEnabled(true);
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
        //set up buttons
        btnCreateBB = createButton("Create Billboard");
        btnEditBB = createButton("Edit Billboard");
        btnInfoBB = createButton("Billboard Info");
        btnListBB = createButton("List Existing Billboards");

        btnCreateBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateBillboardGUI();
            }
        });

        btnEditBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditBillboardGUI();
            }
        });


        btnInfoBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformationGUI();
            }
        });

        btnListBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListBillboardsGUI();
            }
        });

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        bBMenu.add(btnCreateBB, constraints);

        constraints.gridx = 1;
        bBMenu.add(btnEditBB, constraints);

        constraints.gridx = 2;
        bBMenu.add(btnInfoBB, constraints);

        constraints.gridx = 3;
        bBMenu.add(btnListBB, constraints);

        getContentPane().add(bBMenu);

        // Display the window
        setLocation(900,350);
        pack();
        repaint();
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