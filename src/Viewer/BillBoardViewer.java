package Viewer;

import Server.ExtractFromXML;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = dim.width;
    private final int screenHeight = dim.height;

    ExtractFromXML extractFromXML = new ExtractFromXML("starWars.xml");

    private JLabel lblMessage = new JLabel();
    private JLabel lblInfo = new JLabel();


    /**
     * The constructor of the billboard viewer.
     *
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
                if (e.getKeyCode() == 27) {
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

//        try{
//            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
//        }
//        catch(Exception e) {
//            //TODO: Display Error Screen
//            myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));
//        }
        //only message
        if (!extractFromXML.message.isBlank() && extractFromXML.information.isBlank() && extractFromXML.image.isBlank()) {
            Color background =null;
            Color messageColour = null;
            try {
                Field backgroundField = Class.forName("java.awt.Color").getField(extractFromXML.backgroundColour);
                background = (Color) backgroundField.get(null);

                Field messageColourField = Class.forName("java.awt.Color").getField(extractFromXML.textColour);
                messageColour = (Color) messageColourField.get(null);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            JPanel messagePanel = new JPanel();
            messagePanel.setBackground(background);

            JLabel messageLabel = new JLabel();
            messageLabel.setText(extractFromXML.message);
            messageLabel.setForeground(messageColour);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
            int componentWidth = screenWidth;

            double widthRatio = (double)componentWidth/(double)messageWidth;

            int newFontSize = (int)(messageLabel.getFont().getSize()*widthRatio);
            int componentHeight = screenHeight;

            int fontSizeToUse = Math.min(newFontSize, componentHeight);

            messageLabel.setFont(new Font(messageLabel.getFont().getName(),Font.PLAIN, fontSizeToUse));


            messagePanel.setLayout(new BorderLayout());
            messagePanel.add(messageLabel, BorderLayout.CENTER);

            getContentPane().add(messagePanel);
            repaint();
            setVisible(true);

        }
        //only image
        else if (extractFromXML.message.isBlank() && extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            Color background =null;

            try {
                Field backgroundField = Class.forName("java.awt.Color").getField(extractFromXML.backgroundColour);
                background=(Color)backgroundField.get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            JLabel picture = new JLabel();
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picture.setIcon(myPicture);
            picture.setHorizontalAlignment(SwingConstants.CENTER);
            picture.setVerticalAlignment(SwingConstants.CENTER);

            JPanel picPanel = new JPanel();
            picPanel.setBackground(background);

            picPanel.setLayout(new BorderLayout());
            picPanel.add(picture, BorderLayout.CENTER);
            getContentPane().add(picPanel);

            repaint();
            setVisible(true);

        } else if (extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && extractFromXML.image.isBlank()) {
            Color background = null;
            Color infoColour=null;
            try{
                Field backgroundField = Class.forName("java.awt.Color").getField(extractFromXML.backgroundColour);
                background = (Color)backgroundField.get(null);

                Field infoField = Class.forName("java.awt.Color").getField(extractFromXML.informationColour);
                infoColour = (Color)infoField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(background);

            JTextPane infoTextPane = new JTextPane();
            infoTextPane.setText(extractFromXML.information);
            infoTextPane.setEditable(false);
            infoTextPane.setForeground(infoColour);
            infoTextPane.setBackground(background);
            infoTextPane.setFont(infoTextPane.getFont().deriveFont(32.0f));

            //center the
            StyledDocument doc = infoTextPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            infoPanel.setLayout(new BorderLayout());
            infoPanel.setBorder(new EmptyBorder(screenHeight/4, (int) screenWidth/8,screenHeight/4,(int) screenWidth/8));
            infoPanel.add(infoTextPane, BorderLayout.CENTER);

            getContentPane().add(infoPanel);
            repaint();
            setVisible(true);


            /*lblInfo.setText(extractFromXML.information);
            lblInfo.setForeground(extractFromXML.informationColour);
            lblInfo.setPreferredSize(new Dimension((screenWidth / 4) * 3, screenHeight / 2));

            panel.setBackground(extractFromXML.backgroundColour);

            panel.setLayout(new BorderLayout());
            panel.add(lblInfo, BorderLayout.CENTER);
            getContentPane().add(panel);

            repaint();
            setVisible(true);*/
        } /*else if (!extractFromXML.message.isBlank() && extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            lblMessage.setText(extractFromXML.message);
            lblMessage.setForeground(extractFromXML.textColour);
            lblMessage.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));

            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picLabel.setIcon(myPicture);
            picLabel.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3) * 2));

            panel.setBackground(extractFromXML.backgroundColour);

            panel.setLayout(new BorderLayout());
            panel.add(lblMessage, BorderLayout.NORTH);
            panel.add(picLabel, BorderLayout.CENTER);

            getContentPane().add(panel);
            repaint();
            setVisible(true);
        } else if (!extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && extractFromXML.image.isBlank()) {
            lblMessage.setText(extractFromXML.message);
            lblMessage.setForeground(extractFromXML.textColour);
            lblMessage.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

            lblInfo.setText(extractFromXML.information);
            lblInfo.setForeground(extractFromXML.informationColour);
            lblInfo.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

            panel.setBackground(extractFromXML.backgroundColour);

            panel.setLayout(new BorderLayout());
            panel.add(lblMessage, BorderLayout.NORTH);
            panel.add(lblInfo, BorderLayout.SOUTH);

            getContentPane().add(panel);
            repaint();
            setVisible(true);
        } else if (extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picLabel.setIcon(myPicture);
            picLabel.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3) * 2));

            lblInfo.setText(extractFromXML.information);
            lblInfo.setForeground(extractFromXML.informationColour);
            lblInfo.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));

            panel.setBackground(extractFromXML.backgroundColour);

            panel.setLayout(new BorderLayout());
            panel.add(picLabel, BorderLayout.NORTH);
            panel.add(lblInfo, BorderLayout.SOUTH);

            getContentPane().add(panel);
            repaint();
            setVisible(true);
        } else {
            lblMessage.setText(extractFromXML.message);
            lblMessage.setForeground(extractFromXML.textColour);
            lblMessage.setPreferredSize(new Dimension(screenWidth / 3, screenHeight / 3));

            lblInfo.setText(extractFromXML.information);
            lblInfo.setForeground(extractFromXML.informationColour);
            lblInfo.setPreferredSize(new Dimension(screenWidth / 3, screenHeight / 3));

            panel.setBackground(extractFromXML.backgroundColour);

            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 3, screenHeight / 3, Image.SCALE_SMOOTH));
            picLabel.setIcon(myPicture);

            panel.setLayout(new BorderLayout());
            panel.add(lblMessage, BorderLayout.NORTH);
            panel.add(picLabel, BorderLayout.CENTER);
            panel.add(lblInfo, BorderLayout.SOUTH);

            getContentPane().add(panel);
            repaint();
            setVisible(true);
        }*/
    }
}
