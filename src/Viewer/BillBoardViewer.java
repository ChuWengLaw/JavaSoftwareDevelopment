package Viewer;

import ControlPanel.billboard.ExractFromXML;

import javax.imageio.ImageIO;
import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.*;

/**
 * Billboard Viewer which connects to billboard server every
 * 15 seconds and display the billboards on screen
 *
 * @author Law
 */
public class BillBoardViewer extends JFrame {
    private JLabel picLabel = new JLabel();
    private JPanel panel = new JPanel();

    ExractFromXML exractFromXML = new ExractFromXML("starWars");

    private String message = exractFromXML.message;
    private Color textColour = exractFromXML.textColour;
    private String info = exractFromXML.information;
    private Color infoColour = exractFromXML.informationColour;
    private Color background = exractFromXML.backgroundColour;
    private String imageAdress = exractFromXML.image;

    private JLabel lblMessage;
    private JLabel lblInfo;
    private JPanel pnlBackground = new JPanel();
    private JPanel pnlImage;

    public BillBoardViewer() throws HeadlessException, IOException {
        super("Billboard Viewer");

        // Create frame
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        //Connect every 15 seconds
        Runnable fifteenSec = new Runnable() {
            @Override
            public void run() {
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(fifteenSec, 0, 3, TimeUnit.SECONDS);
    }

    private void update() throws IOException {
        Image myPicture;
        try {
            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
        } catch (Exception e) {
            //TODO: Display Error Screen
            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
        }

        if (!message.isBlank() && info.isBlank() && imageAdress.isBlank()) {

        } else if (message.isBlank() && info.isBlank() && !imageAdress.isBlank()) {

        } else if (message.isBlank() && !info.isBlank() && imageAdress.isBlank()) {

        } else if(!message.isBlank() && info.isBlank() && !imageAdress.isBlank()){

        }
        else if(!message.isBlank() && !info.isBlank()&& imageAdress.isBlank()){

        }
        else if(message.isBlank() && !info.isBlank()&& !imageAdress.isBlank()){

        }
        else{
            
        }


        lblInfo = new JLabel(info);
        lblInfo.setForeground(textColour);
        lblMessage = new JLabel(message);
        lblMessage.setForeground(infoColour);
        pnlBackground.setBackground(background);
        picLabel.setIcon(new ImageIcon(myPicture));
        panel.setLayout(new BorderLayout());
        panel.add(pnlBackground, BorderLayout.CENTER);
        panel.add(lblInfo, BorderLayout.NORTH);
        panel.add(lblMessage, BorderLayout.SOUTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().add(panel);
        pack();
    }
}
