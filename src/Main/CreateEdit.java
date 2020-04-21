package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class CreateEdit extends JFrame implements ActionListener,Runnable{
    //set the width of the GUI
    public static final int WIDTH = 450;
    public static final int HEIGHT = 600;

    public CreateEdit(String title) throws HeadlessException {
        super(title);
    }

    //define element to be used
    private JPanel pnlOne;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new CreateEdit("Create and Edit"));
    }

    /**
     * Create the base GUI to be used to create and edit the data
     * @author Lachlan
     */
    private void createGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //define the colour of elements
        pnlOne = createPanel(Color.red);

        //define location of elements
        getContentPane().add(pnlOne,BorderLayout.CENTER);

        //make changes and then send to GUI
        repaint();
        setVisible(true);
    }

    private JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        panel.setBackground(c);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
    createGUI();
    }
}
