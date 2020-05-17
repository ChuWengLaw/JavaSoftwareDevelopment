package Viewer;

import Main.Main;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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
        Image myPicture = ImageIO.read(new File("src/Viewer/Image/soyeon-idle-pfantaken-Favim.com-6382977.jpg"));

        try{
//            Statement statement = Main.connection.createStatement();
//            ResultSet rs = statement.executeQuery(
//                    "SELECT BillboardName, UserName, TextColour, " +
//                            "BackGroundColour, Message, Image, Information FROM billboard");
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnCount = rsmd.getColumnCount();
//            Vector column  = new Vector(columnCount);
//            for (int i = 1; i<= columnCount; i++){
//
//                column.add(rsmd.getColumnName(i));
//            }
//            Vector data = new Vector();
//            Vector row = new Vector();
//            while(rs.next()){
//                row = new Vector(columnCount);
//                for (int i = 1; i <= columnCount; i++){
//                    row.add(rs.getString(i));
//                }
//                data.add(row);
//            }

//            JLabel label = new JLabel();
//            label.setText(text);
//            JTable table = new JTable(data,column);
//            JScrollPane scrollpane = new JScrollPane(table);

        }
        catch(Exception e) {
            //TODO: Display Error Screen
        }

        picLabel.setIcon(new ImageIcon(myPicture));
        panel.setLayout(new BorderLayout());
        panel.add(picLabel, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().add(panel);
        pack();
    }
}
