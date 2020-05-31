package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.BBInfoRequest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.net.ConnectException;

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
    private JTextField txtTextColour;
    private JTextField txtBGColour;
    private JTextField txtMsg;
    private JTextField txtImg;
    private JTextField txtInfo;
    private JTextField txtInfoColour;

    //define Labels
    private JLabel lblBillboardName;
    private JLabel lblTextColour;
    private JLabel lblBGColour;
    private JLabel lblMsg;
    private JLabel lblImg;
    private JLabel lblInfo;
    private JLabel lblInfoColour;

    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     *
     * @throws HeadlessException
     */
    public InformationGUI() throws HeadlessException {
        super("Billboard Information");
        createGUI();
    }

    /**
     * creates the base GUI to be used to display the billboard information received from server
     *
     * @author Lachlan, Law
     */
    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the button and what text it will contain
        btnGetInfo = createButton("Get Billboard Info");

        // sends request to server
        btnGetInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtBillboardName.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Billboard Name.");
                } else {
                    try {
                        BBInfoRequest temp = new BBInfoRequest(Main.loginUser.getSessionToken(), txtBillboardName.getText().toLowerCase());
                        Client.connectServer(temp);
                        txtTextColour.setText(Client.getTextClr());
                        txtBGColour.setText(Client.getBgClr());
                        txtMsg.setText(Client.getMsg());
                        txtImg.setText(Client.getImg());
                        txtInfo.setText(Client.getInfo());
                        txtInfoColour.setText(Client.getInfoClr());
                        if(!Client.isRequestState()){
                            JOptionPane.showMessageDialog(null, "Billboard not found!");
                        }
                    } catch(ConnectException ex) {
                        JOptionPane.showMessageDialog(null, "Connection fail.");
                        System.exit(0);
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
                txtTextColour.setText("");
                txtBGColour.setText("");
                txtMsg.setText("");
                txtImg.setText("");
                txtImg.setForeground(Color.black);
                txtInfo.setText("");
                txtInfoColour.setText("");
            }
        });

        //create labels
        lblBillboardName = createLabel("Billboard Name:");
        lblTextColour = createLabel("Text Colour:");
        lblBGColour = createLabel("Background Colour:");
        lblMsg = createLabel("Message:");
        lblImg = createLabel("Image:");
        lblInfo = createLabel("Information:");
        lblInfoColour = createLabel("Information Colour:");

        //create textBox
        txtBillboardName = createText();
        txtTextColour = createText();
        txtBGColour = createText();
        txtMsg = createText();
        txtImg = createText();
        txtInfo = createText();
        txtInfoColour = createText();
        txtTextColour.setEditable(false);
        txtBGColour.setEditable(false);
        txtMsg.setEditable(false);
        txtImg.setEditable(false);
        txtInfo.setEditable(false);
        txtInfoColour.setEditable(false);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(20, 20, 20, 20);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblBillboardName, constraints);

        constraints.gridx = 1;
        panel.add(txtBillboardName, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(lblTextColour, constraints);

        constraints.gridx = 1;
        panel.add(txtTextColour, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(lblBGColour, constraints);

        constraints.gridx = 1;
        panel.add(txtBGColour, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(lblMsg, constraints);

        constraints.gridx = 1;
        panel.add(txtMsg, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(lblImg, constraints);

        constraints.gridx = 1;
        panel.add(txtImg, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(lblInfo, constraints);

        constraints.gridx = 1;
        panel.add(txtInfo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        panel.add(lblInfoColour, constraints);

        constraints.gridx = 1;
        panel.add(txtInfoColour, constraints);

        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(btnGetInfo, constraints);
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(btnClear, constraints);

        getContentPane().add(panel);

        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);
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
}
