package Main.billboard;

import javax.swing.*;
import java.awt.*;

public class ListBillboardsGUI extends JFrame {

    //set the width and height of the GUI
    public static final int HEIGHT = 800;
    public static final int WIDTH = 600;

    //define the table
    private JTable tblBillboards;


    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    private void createGUI() {
        //set the size of the frame and exit
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        repaint();
        setVisible(true);
    }
}
