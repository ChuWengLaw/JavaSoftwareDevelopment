package ControlPanel.billboard;

import ControlPanel.Client;
import ControlPanel.Main;
import Server.Request.XmlRequest;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

/**
 * This class creates the GUI to be used to display the available options for
 * managing billboard
 */
public class BillBoardManagementGUI extends JFrame {

    //define the buttons
    private JButton btnCreateBB;
    private JButton btnEditBB;
    private JButton btnDeleteBB;
    private JButton btnInfoBB;
    private JButton btnListBB;
    private JButton btnImport;
    private JButton btnExport;

    //define the JPanel and the GridBagConstraints
    private JPanel bBMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructor initialises the GUI creation.
     * @throws HeadlessException
     */
    public BillBoardManagementGUI() throws HeadlessException {
        super("Billboard Management");
        createGUI();
    }
    private void createGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Window Listener to prevent user from pressing other buttons in menu window
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                Main.menuWin.setEnabled(false);}

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {
                Main.menuWin.setEnabled(true);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };
        super.addWindowListener(windowListener);
        //set up buttons
        btnCreateBB = createButton("Create Billboard");
        btnDeleteBB = createButton("Delete Billboard");
        btnInfoBB = createButton("Billboard Info");
        btnListBB = createButton("List Existing Billboards");
        btnImport = createButton("Import Billboard");
        btnExport = createButton("Export Billboard");
        // Create/Edit billboard
        btnCreateBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateBillboardGUI();
            }
        });
        // Delete billboard
        btnDeleteBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteBillboardGUI();
            }
        });
        // Get billboard's information
        btnInfoBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformationGUI();
            }
        });
        // List billboards
        btnListBB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListBillboardsGUI();
            }
        });
        // Import billboard
        btnImport.addActionListener(new ActionListener() {
            // sends request to server
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
                jfc.setDialogTitle("Select a billboard xml file");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Xml", "xml");
                jfc.addChoosableFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    XmlRequest xmlRequest = new XmlRequest(selectedFile, Main.loginUser.getUserName());
                    try {
                        Client.connectServer(xmlRequest);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Export billboard
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExportXmlGUI();
            }
        });

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        bBMenu.add(btnCreateBB, constraints);

        constraints.gridx = 1;
        bBMenu.add(btnDeleteBB, constraints);

        constraints.gridx = 2;
        bBMenu.add(btnInfoBB, constraints);

        constraints.gridx = 3;
        bBMenu.add(btnListBB, constraints);

        constraints.gridx = 4;
        bBMenu.add(btnImport, constraints);

        constraints.gridx = 5;
        bBMenu.add(btnExport, constraints);
        getContentPane().add(bBMenu);

        // Display the window
        setLocation(900,350);
        pack();
        repaint();
        setVisible(true);
    }

    /**
     * Creates the button
     * @author Lachlan
     * @param text the text on the button
     * @return the formatted button
     */
    private JButton createButton (String text){
        JButton button = new JButton();
        button.setText(text);
        return button;
    }
}