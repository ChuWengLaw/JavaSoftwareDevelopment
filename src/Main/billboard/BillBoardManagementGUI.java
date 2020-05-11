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

    public BillBoardManagementGUI(String title) throws HeadlessException {
        super(title);
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
    }

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
