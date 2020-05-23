package Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = dim.width;
    private final int screenHeight = dim.height;

    /**
     * The constructor of the billboard viewer.
     * @throws HeadlessException
     * @throws IOException
     */
    public BillBoardViewer() throws HeadlessException, IOException {
        super("Billboard Viewer");

        // Set the window to borderless.
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(screenWidth, screenHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Connect every 15 seconds
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

        // Mouse setting
        MouseListener mouseCloseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        addMouseListener(mouseCloseListener);

        // Keyboard setting
        KeyListener keyCloseListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 27){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        addKeyListener(keyCloseListener);
}

    private void update() throws IOException {
        // Create a new image.
        ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
        .getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH));
//        try{
//            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
//        }
//        catch(Exception e) {
//            //TODO: Display Error Screen
//            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
//        }

        picLabel.setIcon(myPicture);
        panel.setLayout(new BorderLayout());
        panel.add(picLabel, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
