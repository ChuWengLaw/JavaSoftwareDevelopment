package Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;


/**
 * Billboard Viewer which connects to billboard server every
 * 15 seconds and display the billboards on screen
 * @author Law
 */
public class BillBoardViewer extends JFrame {
    private JLabel picLabel = new JLabel();
    private JPanel panel = new JPanel();

    public BillBoardViewer() throws HeadlessException, IOException {
        super("Billboard Viewer");

        // Create frame
        setSize(500,400);
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
        try{
            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
        }
        catch(Exception e) {
            //TODO: Display Error Screen
            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
        }

        picLabel.setIcon(new ImageIcon(myPicture));
        panel.setLayout(new BorderLayout());
        panel.add(picLabel, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().add(panel);
        pack();
    }
}
