package Main.billboard;

import Main.Main;
import Main.billboard.Billboard;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InformationGUI extends JFrame {

    //set the size of the GUI
    public static final int WIDTH = 300;
    public static final int HEIGHT =300;

    //define buttons
    private JButton btnGetInfo;
    private JButton btnClear;

    //define text boxes
    private JTextField txtBillboardName;
    private JTextField txtInfo;

    private String strBillboardName;

    //define Labels
    private JLabel lblBillboardName;
    private JLabel lblInfo;

    public InformationGUI() throws HeadlessException {
        super("Billboard Information");
        createGUI();
    }

    /**
     * creates the base GUI to be used to display the billboard information
     * @author Lachlan
     */
    private void createGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and what text it will contain
        btnGetInfo = createButton("Get Billboard Info");

        //create an action listener for that button
        btnGetInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    if (!checkBillboard(txtBillboardName.getText())){
                        JOptionPane.showMessageDialog(null,"Billboard name entered not found.");
                    }
                    else{
                        txtInfo.setText(setInfo(txtBillboardName.getText()));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

                                         //create a button to clear the text
        btnClear = createButton("Clear");

        //create an actionListener for the clear button
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBillboardName.setText("");
                txtInfo.setText("");
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
        //getContentPane().add(btnClear, BorderLayout.SOUTH);

        //set the location of the GUI
        setLocation(900,350);

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

    private String setInfo(String name) throws SQLException {
        Statement statement = Main.connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT BillboardName, Information FROM Billboard");

        String result = null;

        while (rs.next()){
            if(name.equals(rs.getString(1))){
                result = rs.getString(2);
                break;
            }
        }

        return result;
    }

    private boolean checkBillboard(String Billboard) throws SQLException {
        boolean existing = false;

        Statement statement = Main.connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT BillboardName FROM Billboard");

        while (rs.next()){
            if (Billboard.equals(rs.getString(1))){
                existing = true;
                break;
            }
        }
        statement.close();
        return existing;
    }
}
