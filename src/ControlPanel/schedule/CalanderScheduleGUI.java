package ControlPanel.schedule;

import ControlPanel.Client;
import ControlPanel.Main;

import Server.Request.ListScheduleRequest;
import Server.Request.WeeklyScheduleRequest;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class CalanderScheduleGUI extends JFrame {

    private JButton btnBillboardScheduler = new JButton();
    private JButton btnDeleteSchedule = new JButton();
    private JButton btnViewAllScheduled = new JButton();
    private JPanel pnlScheduleMenu = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;

    public CalanderScheduleGUI(){
        super("Scheduled Billboards");
        createGUI();
    }
    private void createGUI() {

        int Min_in_Millis = 60000;
        int Hour_in_Millis = 60*Min_in_Millis;
        int Day_in_Millis = 24*Hour_in_Millis;

        long CurrentTimeMillis = System.currentTimeMillis();
        lbl1 = createLabel(CurrentTimeMillis);
        lbl2 = createLabel(CurrentTimeMillis + 1*Day_in_Millis);
        lbl3 = createLabel(CurrentTimeMillis + 2*Day_in_Millis);
        lbl4 = createLabel(CurrentTimeMillis + 3*Day_in_Millis);
        lbl5 = createLabel(CurrentTimeMillis + 4*Day_in_Millis);
        lbl6 = createLabel(CurrentTimeMillis + 5*Day_in_Millis);
        lbl7 = createLabel(CurrentTimeMillis + 6*Day_in_Millis);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btnBillboardScheduler.setText("Schedule a Billboard");

        btnBillboardScheduler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduleBillboardGUI();
            }
        });

        btnDeleteSchedule.setText("Delete a Scheduled Billboard");

        btnDeleteSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteScheduleGUI();
            }
        });

        btnViewAllScheduled.setText("View All Scheduled Billboards");

        btnViewAllScheduled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAllScheduledGUI();
            }
        });

        if (!Main.loginUser.getScheduleBillboardsPermission())
        {
            btnBillboardScheduler.setEnabled(false);
            btnViewAllScheduled.setEnabled(false);
            btnDeleteSchedule.setEnabled(false);
        }
        else
        {
            btnBillboardScheduler.setEnabled(true);
            btnViewAllScheduled.setEnabled(true);
            btnDeleteSchedule.setEnabled(true);
        }

        WeeklyScheduleRequest WeeklyScheduleRequest = new WeeklyScheduleRequest(Main.loginUser.getSessionToken());
        try{
            Client.connectServer(WeeklyScheduleRequest);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        ArrayList<String[]> ScheduleArray = Client.getScheduleArray();
        lbl1 = AddInfoToLabel(ScheduleArray, lbl1,CurrentTimeMillis + 0*Day_in_Millis);
        lbl2 = AddInfoToLabel(ScheduleArray, lbl2,CurrentTimeMillis + 1*Day_in_Millis);
        lbl3 = AddInfoToLabel(ScheduleArray, lbl3,CurrentTimeMillis + 2*Day_in_Millis);
        lbl4 = AddInfoToLabel(ScheduleArray, lbl4,CurrentTimeMillis + 3*Day_in_Millis);
        lbl5 = AddInfoToLabel(ScheduleArray, lbl5,CurrentTimeMillis + 4*Day_in_Millis);
        lbl6 = AddInfoToLabel(ScheduleArray, lbl6,CurrentTimeMillis + 5*Day_in_Millis);
        lbl7 = AddInfoToLabel(ScheduleArray, lbl7,CurrentTimeMillis + 6*Day_in_Millis);

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlScheduleMenu.add(btnBillboardScheduler, constraints);
        constraints.gridx = 2;
        pnlScheduleMenu.add(btnDeleteSchedule, constraints);
        constraints.gridx = 3;
        pnlScheduleMenu.add(btnViewAllScheduled, constraints);

        constraints.gridy = 1;
        constraints.gridx = 1;
        pnlScheduleMenu.add(lbl1, constraints);
        constraints.gridx = 2;
        pnlScheduleMenu.add(lbl2, constraints);
        constraints.gridx = 3;
        pnlScheduleMenu.add(lbl3, constraints);
        constraints.gridx = 4;
        pnlScheduleMenu.add(lbl4, constraints);
        constraints.gridx = 5;
        pnlScheduleMenu.add(lbl5, constraints);
        constraints.gridx = 6;
        pnlScheduleMenu.add(lbl6, constraints);
        constraints.gridx = 7;
        pnlScheduleMenu.add(lbl7, constraints);

        getContentPane().add(pnlScheduleMenu);
        // Display the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        setLocation(width/2,height/2);

        pack();
        repaint();
        setVisible(true);
    }

    private JLabel AddInfoToLabel(ArrayList<String[]> scheduleArray, JLabel lblIn, long Day_Start_Millis) {
        SimpleDateFormat start_formatter= new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat end_formatter= new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        //SimpleDateFormat Date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date DateStart = new Date(Day_Start_Millis);
        String StringStart = start_formatter.format(DateStart);
        String StringEnd = end_formatter.format(DateStart);

        for (int i = 0; i < scheduleArray.size(); i++)
        {
            int IntStart = scheduleArray.get(i)[1].compareTo(StringStart);
            int IntEnd = scheduleArray.get(i)[1].compareTo(StringEnd);
            if (IntStart > 0 && IntEnd < 0)
            {
                lblIn.setText(lblIn.getText() +"<br/>"+ scheduleArray.get(i)[0] + "<br/>" +
                        scheduleArray.get(i)[1].substring(0,scheduleArray.get(i)[1].length()-2));
            }
        }
        lblIn.setText(lblIn.getText() +"</html>");
        return lblIn;
    }

    private JLabel createLabel(long millis) {

        Date datePrint = new Date(millis);
        SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM E");
        JLabel label = new JLabel();
        label.setText("<html><strong>"+formatter.format(datePrint)+"</strong>");
        return label;
    }

    public static boolean IsCurrentlyScheduled(String BillboardName)
    {
        JTable Table;
        boolean IsScheduled = false;
        ListScheduleRequest listScheduleRequest = new ListScheduleRequest(Main.loginUser.getSessionToken());
        try {
            Client.connectServer(listScheduleRequest);
        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "Connection fail.");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Table = Client.getListScheduleBillboardTable();

        int rows = Table.getRowCount();
        for (int i = 0; i < rows; i++)
        {
            if (Table.getValueAt(i,0).equals(BillboardName))
            {
                IsScheduled = true;
            }
        }
        return IsScheduled;
    }
}