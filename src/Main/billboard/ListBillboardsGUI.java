package Main.billboard;

import javax.swing.*;
import java.awt.*;

public class ListBillboardsGUI extends JFrame {

    //set the width and height of the GUI
    public static final int HEIGHT = 500;
    public static final int WIDTH = 800;

    //define the table
    private JTable tblBillboards;


    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    private void createGUI() {
        //set the size of the frame and exit
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //array of the billboards
        String[][] data ={
                {"Star Wars", "George Lucas","","","",""},
                {"Avengers", "Stan Lee","","","",""}
        };

        String[] columnNames = {"Name", "Author","Text Colour","Backgroud Colour","Message", "Image"};

        tblBillboards = new JTable(data, columnNames);
        tblBillboards.setBounds(30,40,200,300);

        JScrollPane sp = new JScrollPane(tblBillboards);
        getContentPane().add(sp);
        setLocation(900,350);

        repaint();
        setVisible(true);
    }
}
