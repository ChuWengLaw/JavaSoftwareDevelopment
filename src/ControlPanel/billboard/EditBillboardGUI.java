package ControlPanel.billboard;

import ControlPanel.Client;
import Server.Request.CreateBBRequest;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditBillboardGUI extends JFrame {

    //define the buttons
    private JButton btnSumbit;
    private JButton btnSearch;

    //define the Labels
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

    //define the panel and gridbag to be used for the GUI layout
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public EditBillboardGUI() throws HeadlessException {
        super("Create Billboard");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //create the buttons
        btnSumbit = createButton("Submit");
        btnSearch = createButton("Search");

        //if the submit button is click perform the following
        btnSumbit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the billboard field is blank display appropriate message
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
                }
                //if the name contains a space display an appropriate message
                else if (txtBillboardName.getText().contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Please Enter the Name as One Word");
                    //otherwise edit the billboard
                } else {
                    CreateBBRequest temp = new CreateBBRequest(txtBillboardName.getText(), txtTextColour.getText(),
                            txtBackgroundColour.getText(), txtMessage.getText(), txtImage.getText(), txtInformation.getText());

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
            }
        });

        //if the search button is clicked do the following
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (txtBillboardName.getText().isBlank()){
                JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
            }
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

        //create the textboxes
        txtBillboardName = createText();
        txtTextColour = createText();
        txtBackgroundColour = createText();
        txtMessage = createText();
        txtImage = createText();
        txtInformation = createText();
        txtInformationColour = createText();

        //disable all feilds other than billboard name
        txtTextColour.setEditable(false);
        txtBackgroundColour.setEditable(false);
        txtMessage.setEditable(false);
        txtImage.setEditable(false);
        txtInformation.setEditable(false);
        txtInformationColour.setEditable(false);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        //add labels to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblBillboardName, constraints);
        constraints.gridy = 1;
        panel.add(lblTextColour, constraints);
        constraints.gridy = 2;
        panel.add(lblBackgroundColour, constraints);
        constraints.gridy = 3;
        panel.add(lblMessage, constraints);
        constraints.gridy = 4;
        panel.add(lblImage, constraints);
        constraints.gridy = 5;
        panel.add(lblInformation, constraints);
        constraints.gridy = 6;
        panel.add(lblInformationColour, constraints);

        //add txtfeilds to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(txtBillboardName, constraints);
        constraints.gridy = 1;
        panel.add(txtTextColour, constraints);
        constraints.gridy = 2;
        panel.add(txtBackgroundColour, constraints);
        constraints.gridy = 3;
        panel.add(txtMessage, constraints);
        constraints.gridy = 4;
        panel.add(txtImage, constraints);
        constraints.gridy = 5;
        panel.add(txtInformation, constraints);
        constraints.gridy = 6;
        panel.add(txtInformationColour, constraints);

        //add buttons to panel
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 0;
        constraints.gridy = 8;
        panel.add(btnSumbit, constraints);
        constraints.gridx = 1;
        panel.add(btnSearch, constraints);


        getContentPane().add(panel);
        setLocation(900, 350);

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

}