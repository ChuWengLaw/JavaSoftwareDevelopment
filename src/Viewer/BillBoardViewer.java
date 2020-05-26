package Viewer;

import ControlPanel.Client;
import Server.ExtractFromXML;
import Server.Request.GetCurrentScheduledRequest;
import Server.Request.WeeklyScheduleRequest;
import org.mariadb.jdbc.BasePrepareStatement;

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
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Billboard Viewer which connects to billboard server every
 * 15 seconds and display the billboards on screen
 *
 * @author Law
 */
public class BillBoardViewer extends JFrame {
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = dim.width;
    private final int screenHeight = dim.height;

    ExtractFromXML extractFromXML = new ExtractFromXML("starWars.xml");


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
        GetCurrentScheduledRequest GetCurrentScheduledRequest = new GetCurrentScheduledRequest();
        try{
            Client.connectServer(GetCurrentScheduledRequest);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(Client.getScheduledBillboardTitle());


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
            JPanel messagePanel = new JPanel();
            messagePanel.setBackground(extractFromXML.backgroundColour);

            JLabel messageLabel = new JLabel();
            messageLabel.setText(extractFromXML.message);
            messageLabel.setForeground(extractFromXML.textColour);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
            int componentWidth = screenWidth;

            double widthRatio = (double) componentWidth / (double) messageWidth;

            int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
            int componentHeight = screenHeight;

            int fontSizeToUse = Math.min(newFontSize, componentHeight);

            messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));


            messagePanel.setLayout(new BorderLayout());
            messagePanel.add(messageLabel, BorderLayout.CENTER);

            getContentPane().add(messagePanel);
            repaint();
            setVisible(true);

        }
        //only image
        else if (extractFromXML.message.isBlank() && extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            JLabel picture = new JLabel();
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picture.setIcon(myPicture);
            picture.setHorizontalAlignment(SwingConstants.CENTER);
            picture.setVerticalAlignment(SwingConstants.CENTER);

            JPanel picPanel = new JPanel();
            picPanel.setBackground(extractFromXML.backgroundColour);

            picPanel.setLayout(new BorderLayout());
            picPanel.add(picture, BorderLayout.CENTER);
            getContentPane().add(picPanel);

            repaint();
            setVisible(true);

        }
        //info only
        else if (extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && extractFromXML.image.isBlank()) {
            JPanel infoPanel = new JPanel();
            infoPanel.setBackground(extractFromXML.backgroundColour);

            JTextPane infoTextPane = new JTextPane();
            infoTextPane.setText(extractFromXML.information);
            infoTextPane.setEditable(false);
            infoTextPane.setForeground(extractFromXML.informationColour);
            infoTextPane.setBackground(extractFromXML.backgroundColour);
            infoTextPane.setFont(infoTextPane.getFont().deriveFont(32.0f));

            //center the TextPane
            StyledDocument doc = infoTextPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            infoPanel.setLayout(new BorderLayout());
            infoPanel.setBorder(new EmptyBorder(screenHeight / 4, screenWidth / 8, screenHeight / 4, screenWidth / 8));
            infoPanel.add(infoTextPane, BorderLayout.CENTER);

            getContentPane().add(infoPanel);
            repaint();
            setVisible(true);

        }
        //message and image
        else if (!extractFromXML.message.isBlank() && extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            JPanel layoutPanel = new JPanel();
            layoutPanel.setBackground(extractFromXML.backgroundColour);

            JLabel messageLabel = new JLabel();
            messageLabel.setText(extractFromXML.message);
            messageLabel.setForeground(extractFromXML.textColour);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3)));

            int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
            int componentWidth = screenWidth;

            double widthRatio = (double) componentWidth / (double) messageWidth;

            int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
            int componentHeight = screenHeight / 3;

            int fontSizeToUse = Math.min(newFontSize, componentHeight);

            messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

            JLabel picture = new JLabel();
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picture.setIcon(myPicture);
            picture.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3) * 2));
            picture.setHorizontalAlignment(SwingConstants.CENTER);

            layoutPanel.setLayout(new BorderLayout());
            layoutPanel.add(messageLabel, BorderLayout.NORTH);
            layoutPanel.add(picture, BorderLayout.CENTER);

            getContentPane().add(layoutPanel);
            repaint();
            setVisible(true);
        } else if (!extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && extractFromXML.image.isBlank()) {
            JPanel layoutPanel = new JPanel();
            layoutPanel.setBackground(extractFromXML.backgroundColour);

            JLabel messageLabel = new JLabel();
            messageLabel.setText(extractFromXML.message);
            messageLabel.setForeground(extractFromXML.textColour);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

            int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
            int componentWidth = screenWidth;
            double widthRatio = (double) componentWidth / (double) messageWidth;
            int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
            int componentHeight = screenHeight / 2;
            int fontSizeToUse = Math.min(newFontSize, componentHeight);
            messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

            JTextPane infoTextPane = new JTextPane();
            infoTextPane.setText(extractFromXML.information);
            infoTextPane.setEditable(false);
            infoTextPane.setForeground(extractFromXML.informationColour);
            infoTextPane.setBackground(extractFromXML.backgroundColour);

            int infoWidth = messageLabel.getFontMetrics(infoTextPane.getFont()).stringWidth(infoTextPane.getText());
            int infoComponentWidth = screenWidth-1;
            double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
            int infoNewFontSize = (int) (infoTextPane.getFont().getSize() * infoWidthRatio);
            int infoComponentHeight = (screenHeight / 2)-1;
            int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
            infoTextPane.setFont(new Font(infoTextPane.getFont().getName(), Font.PLAIN, infoFontSizeToUse));


            infoTextPane.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

            //center the TextPane
            StyledDocument doc = infoTextPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            layoutPanel.setLayout(new BorderLayout());
            layoutPanel.add(messageLabel, BorderLayout.NORTH);
            layoutPanel.add(infoTextPane, BorderLayout.SOUTH);

            getContentPane().add(layoutPanel);
            repaint();
            setVisible(true);
        } else if (extractFromXML.message.isBlank() && !extractFromXML.information.isBlank() && !extractFromXML.image.isBlank()) {
            JPanel layoutPanel = new JPanel();
            layoutPanel.setBackground(extractFromXML.backgroundColour);

            JLabel picture = new JLabel();
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth / 2, screenHeight / 2, Image.SCALE_SMOOTH));
            picture.setIcon(myPicture);
            picture.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3) * 2));
            picture.setHorizontalAlignment(SwingConstants.CENTER);

            JTextPane infoTextPane = new JTextPane();
            infoTextPane.setText(extractFromXML.information);
            infoTextPane.setEditable(false);
            infoTextPane.setForeground(extractFromXML.informationColour);
            infoTextPane.setBackground(extractFromXML.backgroundColour);
            infoTextPane.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));
            infoTextPane.setFont(infoTextPane.getFont().deriveFont(32.0f));

            //center the TextPane
            StyledDocument doc = infoTextPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            layoutPanel.setLayout(new BorderLayout());
            layoutPanel.add(picture, BorderLayout.NORTH);
            layoutPanel.setBorder(new EmptyBorder(0, screenWidth / 4, 0, screenWidth / 4));
            layoutPanel.add(infoTextPane, BorderLayout.SOUTH);

            getContentPane().add(layoutPanel);
            repaint();
            setVisible(true);
        } else {
            JPanel layoutPanel = new JPanel();
            layoutPanel.setBackground(extractFromXML.backgroundColour);

            layoutPanel.setLayout(new BorderLayout());

            JLabel picture = new JLabel();
            ImageIcon myPicture = new ImageIcon(ImageIO.read(new File("src/Viewer/Image/0341b9f3f27a6ba0a3b8de6de9d864949f0dbc23.jpg"))
                    .getScaledInstance(screenWidth/3 , screenHeight/3, Image.SCALE_SMOOTH));
            picture.setIcon(myPicture);
            picture.setPreferredSize(new Dimension(screenWidth, screenHeight/3));
            picture.setHorizontalAlignment(SwingConstants.CENTER);
            picture.setVerticalAlignment(SwingConstants.CENTER);

            JLabel messageLabel = new JLabel();
            messageLabel.setText(extractFromXML.message);
            messageLabel.setForeground(extractFromXML.textColour);
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setVerticalAlignment(SwingConstants.CENTER);
            messageLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));

            int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
            int componentWidth = screenWidth;
            double widthRatio = (double) componentWidth / (double) messageWidth;
            int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
            int componentHeight = screenHeight / 3;
            int fontSizeToUse = Math.min(newFontSize, componentHeight);
            messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

            Color thisColor = Color.decode("#7F3FBF");

            JTextPane infoTextPane = new JTextPane();
            infoTextPane.setText(extractFromXML.information);
            infoTextPane.setEditable(false);
            infoTextPane.setForeground(extractFromXML.informationColour);
            infoTextPane.setBackground(extractFromXML.backgroundColour);
            infoTextPane.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));
            infoTextPane.setFont(infoTextPane.getFont().deriveFont(32.0f));
            infoTextPane.setBorder(new EmptyBorder(0, screenWidth / 8, 0, screenWidth / 8));

            //center the TextPane
            StyledDocument doc = infoTextPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);


            layoutPanel.setLayout(new BorderLayout());
            layoutPanel.add(picture,BorderLayout.CENTER);
            layoutPanel.add(messageLabel,BorderLayout.NORTH);

            layoutPanel.add(infoTextPane,BorderLayout.SOUTH);

            getContentPane().add(layoutPanel);
            repaint();
            setVisible(true);
        }
            /*lblMessage.setText(extractFromXML.message);
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
