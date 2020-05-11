package Main.billboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class BillBoardManagementGUI extends JFrame implements ActionListener,Runnable {
    //define buttons
    private JButton btnCreateEditBillboard;
    private JButton btnBillboardInfo;
    private JButton btnListBillboards;
    private GridBagConstraints constraints = new GridBagConstraints();

    public BillBoardManagementGUI() throws HeadlessException {
        super("Billboard Management");
    }

    /**
     * @author Lachlan
     */
    private void createGUI(){
        //define how to Exit program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create the buttons
        btnCreateEditBillboard = createButton("Create or Edit Billboard");
        btnBillboardInfo = createButton("Billboard Info");
        btnListBillboards = createButton("List existing Billboards");

        //create an actionListener for btnCreateEditBillboard
        btnCreateEditBillboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //create an actionListener for btnBillboardInfo
        btnBillboardInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //create an actionListener for btnListBillboards
        btnListBillboards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JPanel menu = new JPanel(new GridBagLayout());

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10,10,10,10);

        constraints.gridx=0;
        constraints.gridy=0;
        menu.add(btnCreateEditBillboard, constraints);

        constraints.gridx =1;
        menu.add(btnBillboardInfo,constraints);

        constraints.gridx=2;
        menu.add(btnListBillboards, constraints);

        getContentPane().add(menu);


        repaint();
        setVisible(true);
    }

    /**
     * Create the buttons
     * @author Lachlan
     * @param text the text to be written on the button
     * @return the configured button
     */
    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
createGUI();
    }
}
