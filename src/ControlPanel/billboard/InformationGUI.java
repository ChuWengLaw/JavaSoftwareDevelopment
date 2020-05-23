package ControlPanel.billboard;

import ControlPanel.Client;
import Server.Request.BBInfoRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * This class creates the GUI to be used to display the information
 * of a selected billboard
 */
public class InformationGUI extends JFrame {

    //define buttons
    private JButton btnGetInfo;
    private JButton btnClear;

    //define text boxes
    private JTextField txtBillboardName;
    private JTextField txtInfo;

    //define Labels
    private JLabel lblBillboardName;
    private JLabel lblInfo;

    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     */
    public InformationGUI() throws HeadlessException {
        super("Billboard Information");
        createGUI();
    }

    /**
     * creates the base GUI to be used to display the billboard information
     *
     * @author Lachlan
     */
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and what text it will contain
        btnGetInfo = createButton("Get Billboard Info");

        //create an action listener for that button
        btnGetInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BBInfoRequest temp = new BBInfoRequest(txtBillboardName.getText());
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null,"Please Enter a Billboard Name.");
                } else {
                    try {
                        Client.connectServer(temp);
                        txtInfo.setText(Client.getInfo());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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
        lblInfo = createLabel("Information:");

        //create textBox
        txtBillboardName = createText();
        txtInfo = createText();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblBillboardName, constraints);

        constraints.gridx = 1;
        panel.add(txtBillboardName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(lblInfo, constraints);

        constraints.gridx = 1;
        panel.add(txtInfo, constraints);

        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(btnGetInfo, constraints);
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(btnClear, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900, 350);
        pack();
        setVisible(true);
    }

    /**
     * creates a text box
     *
     * @return the text box
     * @author Lachlan
     */
    private JTextField createText() {
        JTextField textField = new JTextField(20);
        return textField;
    }

    /**
     * creates a label with the text which has been input
     *
     * @param text the text which will be displayed in the label
     * @return the label
     * @author Lachlan
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }

    /**
     * creates a button with the text which has been input
     *
     * @param text the text which will be displayed inside the button
     * @return the button
     * @author Lachlan
     */
    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        return button;
    }

//    /**
//     * function gets the information for a particular billboard
//     * @author Lachlan
//     * @param name the name of the Billboard
//     * @return the information for that billboard
//     * @throws SQLException
//     */
//    private String setInfo(String name) throws SQLException {
//        Statement statement = Server.connection.createStatement();
//        ResultSet rs = statement.executeQuery("SELECT BillboardName, Information FROM Billboard");
//
//        String result = null;
//
//        while (rs.next()){
//            if(name.equals(rs.getString(1))){
//                result = rs.getString(2);
//                break;
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * the function check if a billboard exists
//     * @author Lachlan
//     * @param Billboard billboard we want to search for
//     * @return true or false to whether the billboard exist
//     * @throws SQLException
//     */
//    private boolean checkBillboard(String Billboard) throws SQLException {
//        boolean existing = false;
//
//        Statement statement = Server.connection.createStatement();
//        ResultSet rs = statement.executeQuery("SELECT BillboardName FROM Billboard");
//
//        while (rs.next()){
//            if (Billboard.equals(rs.getString(1))){
//                existing = true;
//                break;
//            }
//        }
//        statement.close();
//        return existing;
//    }
}
