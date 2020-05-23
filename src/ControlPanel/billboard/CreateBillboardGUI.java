package ControlPanel.billboard;

import ControlPanel.Client;
import Server.Request.CreateBBRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;

/**
 * This class creates the GUI to be used to create a billboard
 */
public class CreateBillboardGUI extends JFrame {
    //define element to be used
    private JButton btnSubmit;

    //define the labels
    private JLabel lblBillboardName;
    private JLabel lblTextColour;
    private JLabel lblBackgroundColour;
    private JLabel lblMessage;
    private JLabel lblImage;
    private JLabel lblInformation;
    private JLabel lblInformationColour;

    //define the text boxes
    private JTextField txtBillboardName;
    private JTextField txtTextColour;
    private JTextField txtBackgroundColour;
    private JTextField txtMessage;
    private JTextField txtImage;
    private JTextField txtInformation;
    private JTextField txtInformationColour;

    //define the strings to be used in the SQL
    private String strBillboardName;
    private JPanel createBBPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints createBBConstraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     */
    public CreateBillboardGUI() throws HeadlessException {
        super("Create Billboard");
        createGUI();
    }

    /**
     * Create the base GUI to be used to create and edit the data
     *
     * @author Lachlan
     */
    private void createGUI() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");
        //create and actionListener for the submit button
        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
                } else if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please Enter the Name as One Word");
                } else {
                    CreateBBRequest temp = new CreateBBRequest(txtBillboardName.getText(), txtTextColour.getText(), txtBackgroundColour.getText(),
                            txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());
                    try {
                        Client.connectServer(temp);

                        new MakeXMLFile(txtBillboardName.getText(), txtTextColour.getText(),
                                txtBackgroundColour.getText(), txtMessage.getText(), txtImage.getText(), txtInformation.getText(), txtInformationColour.getText());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //clear the textFeilds once the SQL code has been executed
                txtBillboardName.setText("");
                txtTextColour.setText("");
                txtBackgroundColour.setText("");
                txtMessage.setText("");
                txtImage.setText("");
                txtInformation.setText("");
                txtInformationColour.setText("");
            }
        });


        //create the labels
        lblBillboardName = createLabel("Billboard Name:");
        lblTextColour = createLabel("Text Colour:");
        lblBackgroundColour = createLabel("Background Colour:");
        lblMessage = createLabel("Message:");
        lblImage = createLabel("Image");
        lblInformation = createLabel("Information:");
        lblInformationColour = createLabel("Information Colour:");

        //create the text boxes to receive the data
        txtBillboardName = createText();
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();
        txtInformationColour = createText();

        createBBConstraints.anchor = GridBagConstraints.WEST;
        createBBConstraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 0;
        createBBPanel.add(lblBillboardName, createBBConstraints);
        createBBConstraints.gridy = 1;
        createBBPanel.add(lblTextColour, createBBConstraints);
        createBBConstraints.gridy = 2;
        createBBPanel.add(lblBackgroundColour, createBBConstraints);
        createBBConstraints.gridy = 3;
        createBBPanel.add(lblMessage, createBBConstraints);
        createBBConstraints.gridy = 4;
        createBBPanel.add(lblImage, createBBConstraints);
        createBBConstraints.gridy = 5;
        createBBPanel.add(lblInformation, createBBConstraints);
        createBBConstraints.gridy = 6;
        createBBPanel.add(lblInformationColour, createBBConstraints);

        //add txtfeilds to panel
        createBBConstraints.gridx = 1;
        createBBConstraints.gridy = 0;
        createBBPanel.add(txtBillboardName, createBBConstraints);
        createBBConstraints.gridy = 1;
        createBBPanel.add(txtTextColour, createBBConstraints);
        createBBConstraints.gridy = 2;
        createBBPanel.add(txtBackgroundColour, createBBConstraints);
        createBBConstraints.gridy = 3;
        createBBPanel.add(txtMessage, createBBConstraints);
        createBBConstraints.gridy = 4;
        createBBPanel.add(txtImage, createBBConstraints);
        createBBConstraints.gridy = 5;
        createBBPanel.add(txtInformation, createBBConstraints);
        createBBConstraints.gridy = 6;
        createBBPanel.add(txtInformationColour, createBBConstraints);

        //add button to panel
        createBBConstraints.gridwidth = 2;
        createBBConstraints.insets = new Insets(5, 10, 5, 10);
        createBBConstraints.anchor = GridBagConstraints.EAST;
        createBBConstraints.gridx = 0;
        createBBConstraints.gridy = 8;
        createBBPanel.add(btnSubmit, createBBConstraints);

        getContentPane().add(createBBPanel);
        //set the location of the GUI
        setLocation(900, 350);

        //make changes and then send to GUI
        pack();
        setVisible(true);
    }

    /**
     * This function creates a JButton
     *
     * @param text the text with will be on the button
     * @return a JButton with text on it
     * @author Lachlan
     */
    private JButton createButton(String text) {
        JButton button = new JButton();
        button.setText(text);
        return button;
    }

    /**
     * This function create a blank JTextField
     *
     * @return an empty JTextField
     * @author Lachlan
     */
    private JTextField createText() {
        JTextField textBox = new JTextField(20);
        return textBox;
    }

    /**
     * This function create a JTextField with the input of the text
     *
     * @param text the text to be displayed
     * @return a JTextField with the input of text
     * @author Lachlan
     */
    private JTextField createText(String text) {
        JTextField textBox = new JTextField();
        textBox.setText(text);
        strBillboardName = text;
        return textBox;
    }

    /**
     * This function create a JLabel with the title of the text
     *
     * @param text the title of the label
     * @return a JLabel with the title of text
     * @author Lachlan
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }

//    /**
//     * This function is used to determine if a billboard already exists
//     *
//     * @param billboardName the name of the billboard being created
//     * @return a boolean value to whether a billboard already exists
//     * @throws SQLException
//     * @author Lachlan
//     */
//    private Boolean checkDublicate(String billboardName) throws SQLException {
//        boolean existing = false;
//
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery("SELECT BillboardName FROM Billboard");
//
//        while (rs.next()) {
//            if (billboardName.equals(rs.getString(1))) {
//                existing = true;
//                break;
//            }
//        }
//        statement.close();
//        return existing;
//    }
}