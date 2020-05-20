package ControlPanel.billboard;

import ControlPanel.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class XMLInExportGUI extends JFrame {
    //define the buttons
    private JButton btnImportXML;
    private JButton btnExportXML;

    //define the JPanel and the GridBagConstraints
    private JPanel bBMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public XMLInExportGUI() {
        super("Import/Export GUI");
        createGUI();
    }

    private void createGUI(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //set up buttons
        btnImportXML = createButton("Import Billboard XML");
        btnExportXML = createButton("Export Billboard XML");

        btnImportXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Import

            }
        });

        btnExportXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Export
                // TODO: pass in the xml file created and sent from server
                //String filepath = "";
//                File file = new File(filepath);
//                try {
//                    FileWriter fw = new FileWriter(file);
//                    BufferedWriter bw = new BufferedWriter(fw);
//                    bw.close();
//                    fw.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }
        });

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        bBMenu.add(btnImportXML, constraints);

        constraints.gridx = 1;
        bBMenu.add(btnExportXML, constraints);

        getContentPane().add(bBMenu);

        // Display the window
        setLocation(900,350);
        pack();
        repaint();
        setVisible(true);
    }

    private JButton createButton (String text){
        JButton button = new JButton();
        button.setText(text);
        return button;
    }
}
