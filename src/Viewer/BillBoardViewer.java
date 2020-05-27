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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import java.util.ArrayList;

import java.net.URL;
import java.util.Base64;
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

    /**Updates is called every 15 seconds and will display the currently scheduled billboard for its determined time
     * @author Lachlan
     * @throws IOException exception is thrown if connection fails etc.
     */
    private void update() throws IOException {
        GetCurrentScheduledRequest GetCurrentScheduledRequest = new GetCurrentScheduledRequest();
        try {
            Client.connectServer(GetCurrentScheduledRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String currentBillboardString = Client.getScheduledBillboardTitle();

        if (currentBillboardString == null) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.black);

            JLabel lblNoConnection = new JLabel("No Billboard Scheduled");
            int messageWidth = lblNoConnection.getFontMetrics(lblNoConnection.getFont()).stringWidth(lblNoConnection.getText());
            int componentWidth = screenWidth;
            double widthRatio = (double) componentWidth / (double) messageWidth;
            int newFontSize = (int) (lblNoConnection.getFont().getSize() * widthRatio);
            int componentHeight = screenHeight;
            int fontSizeToUse = Math.min(newFontSize, componentHeight);
            lblNoConnection.setFont(new Font(lblNoConnection.getFont().getName(),Font.PLAIN,fontSizeToUse));
            lblNoConnection.setForeground(Color.red);
            lblNoConnection.setHorizontalAlignment(SwingConstants.CENTER);
            lblNoConnection.setVerticalAlignment(SwingConstants.CENTER);


            JLabel picNoConnection = new JLabel();
            URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRh_IKTuBKhex6jQsgVoMtSPnc0ZbR0RAdzv7UfBMbOMS45Wj_h&usqp=CAU");
            BufferedImage image = ImageIO.read(url);
            picNoConnection.setIcon(new ImageIcon(image.getScaledInstance(-1, (screenHeight/2), Image.SCALE_SMOOTH)));
            picNoConnection.setHorizontalAlignment(SwingConstants.CENTER);
            picNoConnection.setVerticalAlignment(SwingConstants.CENTER);

            panel.setLayout(new BorderLayout());
            panel.add(lblNoConnection,BorderLayout.NORTH);
            panel.add(picNoConnection,BorderLayout.CENTER);

            getContentPane().add(panel);
            repaint();
            setVisible(true);

        } else {
            ExtractFromXML currentScheduledBillboard = new ExtractFromXML(currentBillboardString+".xml");

            //if only message is present the display only message
            if (!currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {
                JPanel messagePanel = new JPanel();
                messagePanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel messageLabel = new JLabel();
                messageLabel.setText(currentScheduledBillboard.message);
                messageLabel.setForeground(currentScheduledBillboard.textColour);
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
            //if only the image is present display only the image
            else if (currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {
                JLabel picture = new JLabel();
                //if it is a url image
                if (currentScheduledBillboard.image.startsWith("http")) {
                    URL url = new URL(currentScheduledBillboard.image);
                    BufferedImage image = ImageIO.read(url);
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                    //else if image is in data form
                } else {
                    byte[] encodedImage;
                    encodedImage = currentScheduledBillboard.image.getBytes();
                    byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedImage));
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                }
                picture.setHorizontalAlignment(SwingConstants.CENTER);
                picture.setVerticalAlignment(SwingConstants.CENTER);

                JPanel picPanel = new JPanel();
                picPanel.setBackground(currentScheduledBillboard.backgroundColour);

                picPanel.setLayout(new BorderLayout());
                picPanel.add(picture, BorderLayout.CENTER);
                getContentPane().add(picPanel);

                repaint();
                setVisible(true);

            }
            //if only the info present the display the info only
            else if (currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {
                JPanel infoPanel = new JPanel();
                infoPanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel infoLabel = new JLabel();
                infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                infoLabel.setForeground(currentScheduledBillboard.informationColour);
                infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                infoLabel.setFont(infoLabel.getFont().deriveFont(32.0f));
                infoLabel.setVerticalAlignment(SwingConstants.CENTER);
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);


                infoPanel.setLayout(new BorderLayout());
                infoPanel.setBorder(new EmptyBorder(screenHeight / 4, screenWidth / 8, screenHeight / 4, screenWidth / 8));
                infoPanel.add(infoLabel, BorderLayout.CENTER);

                getContentPane().add(infoPanel);
                repaint();
                setVisible(true);

            }
            //if the message and image are present then display message and image
            else if (!currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {
                JPanel layoutPanel = new JPanel();
                layoutPanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel messageLabel = new JLabel();
                messageLabel.setText(currentScheduledBillboard.message);
                messageLabel.setForeground(currentScheduledBillboard.textColour);
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                messageLabel.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3)));

                //set messages font size
                int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                int componentWidth = screenWidth;
                double widthRatio = (double) componentWidth / (double) messageWidth;
                int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                int componentHeight = screenHeight / 3;
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

                JLabel picture = new JLabel();
                //if it is a url image
                if (currentScheduledBillboard.image.startsWith("http")) {
                    URL url = new URL(currentScheduledBillboard.image);
                    BufferedImage image = ImageIO.read(url);
                    if (image.getWidth() < image.getHeight()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                    //else if image is in data form
                } else {
                    byte[] encodedImage;
                    encodedImage = currentScheduledBillboard.image.getBytes();
                    byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedImage));
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                }
                picture.setHorizontalAlignment(SwingConstants.CENTER);
                picture.setVerticalAlignment(SwingConstants.CENTER);

                layoutPanel.setLayout(new BorderLayout());
                layoutPanel.add(messageLabel, BorderLayout.NORTH);
                layoutPanel.add(picture, BorderLayout.CENTER);

                getContentPane().add(layoutPanel);
                repaint();
                setVisible(true);
            }
            //if message and info are present display the message and info
            else if (!currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {
                JPanel layoutPanel = new JPanel();
                layoutPanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel messageLabel = new JLabel();
                messageLabel.setText(currentScheduledBillboard.message);
                messageLabel.setForeground(currentScheduledBillboard.textColour);
                messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                messageLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

                int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                int componentWidth = screenWidth;
                double widthRatio = (double) componentWidth / (double) messageWidth;
                int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                int componentHeight = screenHeight / 2;
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

                JLabel infoLabel = new JLabel();
                infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                infoLabel.setForeground(currentScheduledBillboard.informationColour);
                infoLabel.setVerticalAlignment(SwingConstants.CENTER);
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

                int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                int infoComponentWidth = screenWidth * 6;
                double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                int infoComponentHeight = screenHeight / 2;
                int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                if (infoFontSizeToUse >= fontSizeToUse) {
                    infoFontSizeToUse = fontSizeToUse - 50;
                }
                infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));
                infoLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

                layoutPanel.setLayout(new BorderLayout());
                layoutPanel.add(messageLabel, BorderLayout.NORTH);
                layoutPanel.add(infoLabel, BorderLayout.SOUTH);

                getContentPane().add(layoutPanel);
                repaint();
                setVisible(true);
            }
            //if the image and info are present display the image and info
            else if (currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {
                JPanel layoutPanel = new JPanel();
                layoutPanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel picture = new JLabel();
                //if it is a url image
                if (currentScheduledBillboard.image.startsWith("http")) {
                    URL url = new URL(currentScheduledBillboard.image);
                    BufferedImage image = ImageIO.read(url);
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                    //else if image is in data form
                } else {
                    byte[] encodedImage;
                    encodedImage = currentScheduledBillboard.image.getBytes();
                    byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedImage));
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 2, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 2, -1, Image.SCALE_SMOOTH)));
                    }
                }
                picture.setHorizontalAlignment(SwingConstants.CENTER);
                picture.setVerticalAlignment(SwingConstants.CENTER);

                JLabel infoLabel = new JLabel();
                infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                infoLabel.setForeground(currentScheduledBillboard.informationColour);
                infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                infoLabel.setPreferredSize(new Dimension((screenWidth / 4) * 3, screenHeight / 3));
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                infoLabel.setVerticalAlignment(SwingConstants.CENTER);

                int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                int infoComponentWidth = ((screenWidth / 4) * 3) * 6;
                double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                int infoComponentHeight = screenHeight / 3;
                int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                if (infoFontSizeToUse >= 100) {
                    infoFontSizeToUse = 50;
                }
                infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));

                layoutPanel.setLayout(new BorderLayout());
                layoutPanel.add(picture, BorderLayout.CENTER);
                layoutPanel.setBorder(new EmptyBorder(0, screenWidth / 8, 0, screenWidth / 8));
                layoutPanel.add(infoLabel, BorderLayout.SOUTH);

                getContentPane().add(layoutPanel);
                repaint();
                setVisible(true);
            }
            //else display the image, message and info
            else {
                JPanel layoutPanel = new JPanel();
                layoutPanel.setBackground(currentScheduledBillboard.backgroundColour);

                JLabel picture = new JLabel();
                //if it is a url image
                if (currentScheduledBillboard.image.startsWith("http")) {
                    URL url = new URL(currentScheduledBillboard.image);
                    BufferedImage image = ImageIO.read(url);
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 3, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 3, -1, Image.SCALE_SMOOTH)));
                    }
                    //else if image is in data form
                } else {
                    byte[] encodedImage;
                    encodedImage = currentScheduledBillboard.image.getBytes();
                    byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedImage));
                    if (image.getWidth() < image.getHeight() || image.getHeight() == image.getWidth()) {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(-1, screenHeight / 3, Image.SCALE_SMOOTH)));
                    } else {
                        picture.setIcon(new ImageIcon(image.getScaledInstance(screenWidth / 3, -1, Image.SCALE_SMOOTH)));
                    }
                }
                picture.setHorizontalAlignment(SwingConstants.CENTER);
                picture.setVerticalAlignment(SwingConstants.CENTER);

                JLabel messageLabel = new JLabel();
                messageLabel.setText(currentScheduledBillboard.message);
                messageLabel.setForeground(currentScheduledBillboard.textColour);
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

                JLabel infoLabel = new JLabel();
                infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                infoLabel.setForeground(currentScheduledBillboard.informationColour);
                infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                infoLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));
                infoLabel.setBorder(new EmptyBorder(0, screenWidth / 8, 0, screenWidth / 8));

                int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                int infoComponentWidth = ((screenWidth / 4) * 3) * 6;
                double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                int infoComponentHeight = screenHeight / 3;
                int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                if (infoFontSizeToUse >= fontSizeToUse) {
                    infoFontSizeToUse = fontSizeToUse - 50;
                }
                infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));
                infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                infoLabel.setVerticalAlignment(SwingConstants.CENTER);


                layoutPanel.setLayout(new BorderLayout());
                layoutPanel.add(picture, BorderLayout.CENTER);
                layoutPanel.add(messageLabel, BorderLayout.NORTH);
                layoutPanel.add(infoLabel, BorderLayout.SOUTH);

                getContentPane().add(layoutPanel);
                repaint();
                setVisible(true);
            }
        }
    }
}
