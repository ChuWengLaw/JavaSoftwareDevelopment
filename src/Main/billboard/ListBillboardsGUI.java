package Main.billboard;

import Main.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.SQLException;

public class ListBillboardsGUI extends JFrame {

    //set the width and height of the GUI
    public static final int HEIGHT = 300;
    public static final int WIDTH = 600;


    public ListBillboardsGUI() throws HeadlessException {
        super("List Billboards");
        createGUI();
    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);

        repaint();
        setVisible(true);
    }
}
