package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.SQLException;

public class InformationGUI extends JFrame implements ActionListener,Runnable {

    //set the size of the GUI
    public static final int WIDTH = 300;
    public static final int HEIGHT =300;

    //define buttons
    private JButton btnGetInfo;

    //define text boxes
    private JTextField txtBillboardName;
    private JTextField txtInfo;

    private String strBillboardName;

    //define Labels
    private JLabel lblBillboardName;
    private JLabel lblInfo;

    public InformationGUI(String title) throws HeadlessException {
        super(title);
    }

    /**
     * creates the base GUI to be used to display the billboard information
     * @author Lachlan
     */
    private void createGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and what text it will contain
        btnGetInfo = createButton("Get Billboard Info");

        //create an action listener for that button
        btnGetInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Billboard bb = new Billboard();
                strBillboardName = txtBillboardName.getText();
                String out = null;
                try {
                    bb.GetBillboardInfo(strBillboardName);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                txtInfo.setText(out);
            }
        });

        //create labels
        lblBillboardName = createLabel("Billboard Name:");
        lblInfo =createLabel("Information:");

        //create textBox
        txtBillboardName = createText();
        txtInfo = createText();

        //create layout for the text boxes
        JPanel inputBoxes = new JPanel(new GridLayout(2,2));
        inputBoxes.add(lblBillboardName);
        inputBoxes.add(txtBillboardName);
        inputBoxes.add(lblInfo);
        inputBoxes.add(txtInfo);

        getContentPane().add(inputBoxes);
        getContentPane().add(btnGetInfo,BorderLayout.SOUTH);

        repaint();
        setVisible(true);
    }

    /**
     * creates a text box
     * @author Lachlan
     * @return the text box
     */
    private JTextField createText() {
        JTextField textField = new JTextField();
        return textField;
    }

    /**
     * creates a label with the text which has been input
     * @author Lachlan
     * @param text the text which will be displayed in the label
     * @return the label
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }

    /**
     * creates a button with the text which has been input
     * @author Lachlan
     * @param text the text which will be displayed inside the button
     * @return the button
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
