package Main.user;

import Main.Main;
import Server.Server;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.transform.Result;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ListUserWin {
    public ListUserWin(){
        try{
            User user = new User();
            Statement statement = Server.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "select userName,CreateBillboardsPermission,EditAllBillboardPermission," +
                            "ScheduleBillboardsPermission, EditUsersPermission from user");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector column  = new Vector(columnCount);
            for (int i = 1; i<= columnCount; i++){
                column.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();
            while(rs.next()){
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++){
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            JFrame frame = new JFrame();
            frame.setSize(500,120);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            JTable table = new JTable(data,column);
            JScrollPane scrollpane = new JScrollPane(table);
            panel.setLayout(new BorderLayout());
            panel.add(scrollpane, BorderLayout.CENTER);
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
