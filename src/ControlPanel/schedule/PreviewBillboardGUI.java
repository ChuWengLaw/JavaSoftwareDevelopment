package ControlPanel.schedule;

import Server.ExtractFromXML;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

public class PreviewBillboardGUI  extends JFrame {
    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = (dim.width/4);
    private final int screenHeight = (dim.height/4);
    private JPanel panel = new JPanel();

    public PreviewBillboardGUI(String BillboardName) throws HeadlessException {
        super("Billboard Viewer");

        setSize(screenWidth, screenHeight);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            update(BillboardName);
        } catch (IOException e) {

        }

    }

    private void update(String BillboardName) throws IOException {
        //getContentPane().remove(panel);
        panel.removeAll();

        try {
            String currentBillboardString = BillboardName;
            System.out.println(currentBillboardString);
            ExtractFromXML currentScheduledBillboard = new ExtractFromXML(currentBillboardString + ".xml");
            System.out.println(currentBillboardString);
                //if only message is present the display only message
            if (currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank())
            {
                panel.setBackground(currentScheduledBillboard.backgroundColour);
                panel.setLayout(new BorderLayout());

                getContentPane().add(panel);
                repaint();
                setVisible(true);
                toFront();
            }
                else if (!currentScheduledBillboard.message.isBlank() && currentScheduledBillboard.information.isBlank() && currentScheduledBillboard.image.isBlank()) {

                    panel.setBackground(currentScheduledBillboard.backgroundColour);

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


                    panel.setLayout(new BorderLayout());
                    panel.add(messageLabel, BorderLayout.CENTER);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();

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


                    panel.setBackground(currentScheduledBillboard.backgroundColour);

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


                    panel.setLayout(new BorderLayout());
                    panel.add(picture, BorderLayout.CENTER);
                    panel.add(messageLabel, BorderLayout.NORTH);
                    panel.add(infoLabel, BorderLayout.SOUTH);

                    getContentPane().add(panel);
                    repaint();
                    setVisible(true);
                    toFront();
                }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Billboard does not exist.");
        }
    }
}