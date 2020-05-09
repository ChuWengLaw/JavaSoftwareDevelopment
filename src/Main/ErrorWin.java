package Main;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ErrorWin extends JFrame{
    private JLabel labelErrorMessage = new JLabel();
    private JButton exitButton = new JButton("OK");
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    public ErrorWin(String errorText){
        // Setting default value of the frame
        super("Error");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Label setting
        labelErrorMessage.setText(errorText);

        // Button setting
        ActionListener exitListener = e -> {
            super.dispose();
        };
        exitButton.addActionListener(exitListener);

        // Panel setting
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelErrorMessage, constraints);

        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(exitButton, constraints);

        getContentPane().add(panel);

        // Display the window
        setLocation(900,350);
        pack();
        setVisible(true);
    }
}
