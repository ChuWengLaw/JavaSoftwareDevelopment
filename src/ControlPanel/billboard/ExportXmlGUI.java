package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.XmlRequest;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This class creates the GUI to be used to export a selected
 * billboard to xml format
 */
public class ExportXmlGUI extends JFrame {
    //set the width of the GUI
    public static final int WIDTH = 350;
    public static final int HEIGHT = 400;

    //define element to be used
    private JButton btnSubmit;

    //define the labels
    private JLabel lblBillboardName;

    //define the text boxes
    private JTextField txtBillboardName;

    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();


    /**
     * Constructor initialises the GUI creation.
     *
     * @throws HeadlessException
     */
    public ExportXmlGUI() throws HeadlessException {
        super("Export a billboard");
        createGUI();
    }

    /**
     * Create the base GUI to be used to export a billboard to xml format
     *
     * @author Law
     */
    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the labels
        lblBillboardName = createLabel("Billboard Name:");

        //create the text boxes to receive the data
        txtBillboardName = createText();

        //create the button and define what text it will contain
        btnSubmit = createButton("Submit");

        // sends request to server
        btnSubmit.addActionListener(new ActionListener() {
            //when the submit button is click make covert the inputs into string. then execute the CreateEditBilloard from the Billboard Class
            @Override
            public void actionPerformed(ActionEvent e) {
                XmlRequest xmlRequest = new XmlRequest(Main.loginUser.getSessionToken(), txtBillboardName.getText().toLowerCase(), true);
                try {
                    Client.connectServer(xmlRequest);
                    if(Client.isRequestState()){
                        try {
                            //Create a file chooser
                            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
                            jfc.setDialogTitle("Specify a location to save the file");
                            int returnValue = jfc.showSaveDialog(null);
                            if (returnValue == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = jfc.getSelectedFile();
                                Files.copy(Client.getExportFile().toPath(), selectedFile.toPath(),
                                        StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Xml not found!");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Billboard not found!");
                    }
                } catch(ConnectException ex) {
                    JOptionPane.showMessageDialog(null, "Connection fail.");
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected error occurred!");
                }
                //clear the textField
                txtBillboardName.setText("");
            }
        });

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2, 2, 2, 2);

        //add labels to panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblBillboardName, constraints);
        constraints.gridy = 1;

        //add txtfeilds to panel
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(txtBillboardName, constraints);

        //add button to panel
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(btnSubmit, constraints);

        getContentPane().add(panel);
        //set the location of the GUI
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/4,height/4);

        //make changes and then send to GUI
        pack();
        setVisible(true);
    }

    /**
     * This function creates a JButton
     *
     * @param text the text with will be on the button
     * @return a JButton with text on it
     * @author Law
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
     * @author Law
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
     * @author Law
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        return label;
    }
}
