package Server;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class ScheduleSQL {
    public ScheduleSQL() {}

    public void ScheduleBillboard(String BillboardName, String DateTime, int Duration,
                                int RecurType, int RecurAmount) throws SQLException {
        boolean ExistFlag = false;

        try {
            ResultSet resultSet = Server.statement.executeQuery("SELECT BillboardName FROM Billboard");
            while (resultSet.next()) {
                if ( BillboardName.equals(resultSet.getString("BillboardName")) ) {
                    ExistFlag = true;
                    break;
                }
                else {
                    ExistFlag = false;
                }
            }
            if (ExistFlag == true) {
                ResultSet Insert = Server.statement.executeQuery("INSERT INTO Schedule VALUES ('" +
                        BillboardName + "','" + DateTime + "','" +
                        Duration + "','" + RecurType + "','" + RecurAmount +
                        "');");
            }
            //if billboard does not exist it will create a new billboard
            else {

            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void DeleteSchedule(String BillboardName, String DateTime) throws SQLException {
        //boolean ExistFlag = false;
        //Server.statement.executeQuery("DELETE FROM Schedule WHERE BillboardName = '" + BillboardName + "';");

        try {
            Server.statement.executeQuery("DELETE FROM Schedule WHERE (BillboardName = '" +
                        BillboardName + "' AND ScheduleTime = '" + DateTime + "');");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public JTable ScheduledInformation() throws SQLException {
        JTable table = new JTable();
        try {
            ResultSet list = Server.statement.executeQuery("SELECT BillboardName, ScheduleTime FROM Schedule;");
            ResultSetMetaData rsmd = list.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector columnHeader = new Vector(columnCount);
            for (int i = 1; i<= columnCount; i++){
                columnHeader.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();

            while(list.next()) {
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(list.getString(i));
                }
                data.add(row);
            }
            table = new JTable(data,columnHeader);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return table;
    }
}
