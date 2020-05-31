package Viewer;

import ControlPanel.Client;
import Server.ExtractFromXML;
import Server.Request.GetCurrentScheduledRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.ConnectException;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.*;

/**
 * Billboard Viewer which connects to billboard server every
 * 15 seconds and display the billboards on screen
 *
 * @author Law
 */
public class BillboardViewer extends JFrame {
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = dim.width;
    private final int screenHeight = dim.height;
    private JPanel panel = new JPanel();

    /**
     * The constructor of the billboard viewer.
     *
     * @throws HeadlessException
     */
    public BillboardViewer() throws HeadlessException {
        super("Billboard Viewer");

        // Set the window to borderless.
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setSize(screenWidth, screenHeight);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Connect every 15 seconds
        Runnable fifteenSec = () -> {
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(fifteenSec, 0, 15, TimeUnit.SECONDS);
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

    /**
     * Updates is called every 15 seconds and will display the currently scheduled billboard for its determined time
     *
     * @throws IOException exception is thrown if connection fails etc.
     * @author Lachlan
     */
    private void update() throws IOException {
        panel.removeAll();

        GetCurrentScheduledRequest GetCurrentScheduledRequest = new GetCurrentScheduledRequest();
        try {
            Client.connectServer(GetCurrentScheduledRequest);
            String currentBillboardString = Client.getScheduledBillboardTitle();
            //if no billboard scheduled then display the no billboard scheduled billboard
            if (currentBillboardString == null) {
                panel.setBackground(Color.black);
                JLabel lblNoSchedule = new JLabel("No Billboard Scheduled");
                int messageWidth = lblNoSchedule.getFontMetrics(lblNoSchedule.getFont()).stringWidth(lblNoSchedule.getText());
                int componentWidth = screenWidth-10;
                double widthRatio = (double) componentWidth / (double) messageWidth;
                int newFontSize = (int) (lblNoSchedule.getFont().getSize() * widthRatio);
                int componentHeight = screenHeight;
                int fontSizeToUse = Math.min(newFontSize, componentHeight);
                lblNoSchedule.setFont(new Font(lblNoSchedule.getFont().getName(), Font.PLAIN, fontSizeToUse));
                lblNoSchedule.setForeground(Color.red);
                lblNoSchedule.setHorizontalAlignment(SwingConstants.CENTER);
                lblNoSchedule.setVerticalAlignment(SwingConstants.CENTER);

                //define the image
                JLabel picNoSchedule = new JLabel();
                URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRh_IKTuBKhex6jQsgVoMtSPnc0ZbR0RAdzv7UfBMbOMS45Wj_h&usqp=CAU");
                BufferedImage image = ImageIO.read(url);
                picNoSchedule.setIcon(new ImageIcon(image.getScaledInstance(-1, (screenHeight / 2), Image.SCALE_SMOOTH)));
                picNoSchedule.setHorizontalAlignment(SwingConstants.CENTER);
                picNoSchedule.setVerticalAlignment(SwingConstants.CENTER);

                panel.setLayout(new BorderLayout());
                panel.add(lblNoSchedule, BorderLayout.NORTH);
                panel.add(picNoSchedule, BorderLayout.CENTER);

                getContentPane().add(panel);
                repaint();
                setVisible(true);
                toFront();

            } else {
                ExtractFromXML currentScheduledBillboard = new ExtractFromXML(currentBillboardString + ".xml");

                //if nothing entered
                if (currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {
                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    getContentPane().add(panel);
                    repaint();
                    toFront();
                }

                //if only message is present the display only message
                else if (!currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define message
                    JLabel messageLabel = new JLabel();
                    messageLabel.setText(currentScheduledBillboard.message);
                    messageLabel.setForeground(currentScheduledBillboard.textColour);
                    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    //set message size in relation to the screen
                    int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                    int componentWidth = screenWidth;
                    double widthRatio = (double) componentWidth / (double) messageWidth;
                    int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                    int componentHeight = screenHeight;
                    int fontSizeToUse = Math.min(newFontSize, componentHeight);

                    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));


                    panel.setLayout(new BorderLayout());
                    panel.add(messageLabel, BorderLayout.CENTER);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();

                }
                //if only the image is present display only the image
                else if (currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {
                    panel.setBackground(currentScheduledBillboard.backgroundColour);
                    //define the image
                    JLabel picture = new JLabel();
                    try {
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
                    }
                    //if the image is invalid then display a message saying invalid image
                    catch (NullPointerException np) {
                        picture.setText("Image is not a valid image");
                        picture.setFont(picture.getFont().deriveFont(32.0f));
                        if(panel.getBackground()==Color.BLACK){
                            picture.setForeground(Color.white);
                        }
                    }
                    picture.setHorizontalAlignment(SwingConstants.CENTER);
                    picture.setVerticalAlignment(SwingConstants.CENTER);

                    panel.setLayout(new BorderLayout());
                    panel.add(picture, BorderLayout.CENTER);
                    getContentPane().add(panel);

                    repaint();
                    setVisible(true);
                    toFront();

                }
                //if only the info present the display the info only
                else if (currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define the info
                    JLabel infoLabel = new JLabel();
                    infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                    infoLabel.setForeground(currentScheduledBillboard.informationColour);
                    infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                    infoLabel.setFont(infoLabel.getFont().deriveFont(32.0f));
                    infoLabel.setVerticalAlignment(SwingConstants.CENTER);
                    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);


                    panel.setLayout(new BorderLayout());
                    panel.setBorder(new EmptyBorder(screenHeight / 4, screenWidth / 8, screenHeight / 4, screenWidth / 8));
                    panel.add(infoLabel, BorderLayout.CENTER);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();

                }
                //if the message and image are present then display message and image
                else if (!currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define the message
                    JLabel messageLabel = new JLabel();
                    messageLabel.setText(currentScheduledBillboard.message);
                    messageLabel.setForeground(currentScheduledBillboard.textColour);
                    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    messageLabel.setPreferredSize(new Dimension(screenWidth, (screenHeight / 3)));

                    //set messages font size
                    int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                    int componentWidth = screenWidth-10;
                    double widthRatio = (double) componentWidth / (double) messageWidth;
                    int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                    int componentHeight = screenHeight / 3;
                    int fontSizeToUse = Math.min(newFontSize, componentHeight);
                    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

                    //define the image
                    JLabel picture = new JLabel();
                    try {
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
                    }
                    //if the image is invalid then display a message saying invalid image
                    catch (NullPointerException np) {
                        picture.setText("Image is not a valid image");
                        picture.setFont(picture.getFont().deriveFont(32.0f));
                        if(panel.getBackground()==Color.BLACK){
                            picture.setForeground(Color.white);
                        }
                    }
                    picture.setHorizontalAlignment(SwingConstants.CENTER);
                    picture.setVerticalAlignment(SwingConstants.CENTER);

                    panel.setLayout(new BorderLayout());
                    panel.add(messageLabel, BorderLayout.NORTH);
                    panel.add(picture, BorderLayout.CENTER);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();
                }
                //if message and info are present display the message and info
                else if (!currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define the message
                    JLabel messageLabel = new JLabel();
                    messageLabel.setText(currentScheduledBillboard.message);
                    messageLabel.setForeground(currentScheduledBillboard.textColour);
                    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    messageLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

                    //set message size in relation to the screen
                    int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                    int componentWidth = screenWidth-10;
                    double widthRatio = (double) componentWidth / (double) messageWidth;
                    int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                    int componentHeight = screenHeight / 2;
                    int fontSizeToUse = Math.min(newFontSize, componentHeight);
                    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

                    //define the info
                    JLabel infoLabel = new JLabel();
                    infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                    infoLabel.setForeground(currentScheduledBillboard.informationColour);
                    infoLabel.setVerticalAlignment(SwingConstants.CENTER);
                    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    //set info size in relation to the screen
                    int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                    int infoComponentWidth = screenWidth * 6;
                    double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                    int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                    int infoComponentHeight = screenHeight / 2;
                    int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                    //if it is the same size as the message reduce the size by 50
                    if (infoFontSizeToUse >= fontSizeToUse) {
                        infoFontSizeToUse = fontSizeToUse - 50;
                    }
                    infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));
                    infoLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 2));

                    panel.setLayout(new BorderLayout());
                    panel.add(messageLabel, BorderLayout.NORTH);
                    panel.add(infoLabel, BorderLayout.SOUTH);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();
                }
                //if the image and info are present display the image and info
                else if (currentScheduledBillboard.message.isBlank() && !currentScheduledBillboard.information.isBlank() && !currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define the image
                    JLabel picture = new JLabel();
                    try {
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
                    }
                    //if the image is invalid then display a message saying invalid image
                    catch (NullPointerException np) {
                        picture.setText("Image is not a valid image");
                        picture.setFont(picture.getFont().deriveFont(32.0f));
                        if(panel.getBackground()==Color.BLACK){
                            picture.setForeground(Color.white);
                        }
                    }
                    picture.setHorizontalAlignment(SwingConstants.CENTER);
                    picture.setVerticalAlignment(SwingConstants.CENTER);

                    //define the info
                    JLabel infoLabel = new JLabel();
                    infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                    infoLabel.setForeground(currentScheduledBillboard.informationColour);
                    infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                    infoLabel.setPreferredSize(new Dimension((screenWidth / 4) * 3, screenHeight / 3));
                    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    infoLabel.setVerticalAlignment(SwingConstants.CENTER);

                    //set info size in relation to the screen
                    int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                    int infoComponentWidth = ((screenWidth / 4) * 3) * 6;
                    double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                    int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                    int infoComponentHeight = screenHeight / 3;
                    int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                    //if the info font size is bigger than 100 set the font size to 50
                    if (infoFontSizeToUse >= 100) {
                        infoFontSizeToUse = 50;
                    }
                    infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));

                    panel.setLayout(new BorderLayout());
                    panel.add(picture, BorderLayout.CENTER);
                    panel.setBorder(new EmptyBorder(0, screenWidth / 8, 0, screenWidth / 8));
                    panel.add(infoLabel, BorderLayout.SOUTH);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();
                }
                //else display the image, message and info
                else {
                    panel.setBackground(currentScheduledBillboard.backgroundColour);

                    //define the image
                    JLabel picture = new JLabel();
                    try {
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
                    }
                    //if the image is invalid then display a message saying invalid image
                    catch (NullPointerException np) {
                        picture.setText("Image is not a valid image");
                        picture.setFont(picture.getFont().deriveFont(32.0f));
                        if(panel.getBackground()==Color.BLACK){
                            picture.setForeground(Color.white);
                        }
                    }
                    picture.setHorizontalAlignment(SwingConstants.CENTER);
                    picture.setVerticalAlignment(SwingConstants.CENTER);

                    //define the message
                    JLabel messageLabel = new JLabel();
                    messageLabel.setText(currentScheduledBillboard.message);
                    messageLabel.setForeground(currentScheduledBillboard.textColour);
                    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    messageLabel.setVerticalAlignment(SwingConstants.CENTER);
                    messageLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));

                    //set message size in relation to the screen
                    int messageWidth = messageLabel.getFontMetrics(messageLabel.getFont()).stringWidth(messageLabel.getText());
                    int componentWidth = screenWidth -10;
                    double widthRatio = (double) componentWidth / (double) messageWidth;
                    int newFontSize = (int) (messageLabel.getFont().getSize() * widthRatio);
                    int componentHeight = screenHeight / 3;
                    int fontSizeToUse = Math.min(newFontSize, componentHeight);
                    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, fontSizeToUse));

                    //define info
                    JLabel infoLabel = new JLabel();
                    infoLabel.setText("<HTML>" + currentScheduledBillboard.information + "</HTML>");
                    infoLabel.setForeground(currentScheduledBillboard.informationColour);
                    infoLabel.setBackground(currentScheduledBillboard.backgroundColour);
                    infoLabel.setPreferredSize(new Dimension(screenWidth, screenHeight / 3));
                    infoLabel.setBorder(new EmptyBorder(0, screenWidth / 8, 0, screenWidth / 8));

                    //set info size in relation to the screen
                    int infoWidth = infoLabel.getFontMetrics(infoLabel.getFont()).stringWidth(infoLabel.getText());
                    int infoComponentWidth = ((screenWidth / 4) * 3) * 6;
                    double infoWidthRatio = (double) infoComponentWidth / (double) infoWidth;
                    int infoNewFontSize = (int) (infoLabel.getFont().getSize() * infoWidthRatio);
                    int infoComponentHeight = screenHeight / 3;
                    int infoFontSizeToUse = Math.min(infoNewFontSize, infoComponentHeight);
                    //if it is the same size as the message reduce the size by 50
                    if (infoFontSizeToUse >= fontSizeToUse) {
                        infoFontSizeToUse = fontSizeToUse - 50;
                    }

                    infoLabel.setFont(new Font(infoLabel.getFont().getName(), Font.PLAIN, infoFontSizeToUse));
                    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    infoLabel.setVerticalAlignment(SwingConstants.CENTER);

                    panel.setLayout(new BorderLayout());
                    panel.add(picture, BorderLayout.CENTER);
                    panel.add(messageLabel, BorderLayout.NORTH);
                    panel.add(infoLabel, BorderLayout.SOUTH);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();
                }
            }
        }
        //if no connection to the server a billboard with an appropriate warning will be displayed
        catch (Exception e) {
            panel.setBackground(Color.RED);
            JLabel lblNoServerConection = new JLabel("Not Connected To Server!!!");
            lblNoServerConection.setFont(lblNoServerConection.getFont().deriveFont(64.0f));
            lblNoServerConection.setVerticalAlignment(SwingConstants.CENTER);
            lblNoServerConection.setHorizontalAlignment(SwingConstants.CENTER);
            lblNoServerConection.setForeground(Color.black);

            //define the picture
            JLabel picNoConnection = new JLabel();
            URL url = new URL("https://cdn.dribbble.com/users/385451/screenshots/5087974/fox-expenses-500.png");
            BufferedImage image = ImageIO.read(url);
            picNoConnection.setIcon(new ImageIcon(image.getScaledInstance(-1, (screenHeight / 2), Image.SCALE_SMOOTH)));
            picNoConnection.setHorizontalAlignment(SwingConstants.CENTER);
            picNoConnection.setVerticalAlignment(SwingConstants.CENTER);

            //define the message
            JLabel lblMessage = new JLabel();
            lblMessage.setText("Waiting for connection ...");
            lblMessage.setFont(lblMessage.getFont().deriveFont(32.0f));
            lblMessage.setForeground(Color.black);
            lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
            lblMessage.setVerticalAlignment(SwingConstants.CENTER);

            panel.setLayout(new BorderLayout());
            panel.setBorder(new EmptyBorder(30, 30, 30, 30));
            panel.add(lblNoServerConection, BorderLayout.NORTH);
            panel.add(picNoConnection, BorderLayout.CENTER);
            panel.add(lblMessage, BorderLayout.SOUTH);


            getContentPane().add(panel);
            repaint();
            setVisible(true);
            toFront();
        }
    }
}
