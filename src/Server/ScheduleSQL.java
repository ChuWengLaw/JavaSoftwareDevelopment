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

    public String[][] ScheduledInformation() throws SQLException {
        try {
            ResultSet resultSet = Server.statement.executeQuery("SELECT BillboardName, ScheduleTime FROM Schedule;");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            String[][] ScheduleArray = new String[1][columnCount];
            while(resultSet.next()) {
                String[] CurrentRow = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    CurrentRow[i] = resultSet.getString(i);
                }
                ScheduleArray = AddRowSchedule(ScheduleArray, CurrentRow);
            }
            return ScheduleArray;
        } catch (SQLException e) {
            System.out.println(e);

        }
        return new String[1][1];
    }

    public static String[][] AddRowSchedule(String[][] Init, String[] ToAdd)
    {
        String[][] Output = new String[Init[0].length+1][ToAdd.length];
        Output[Output[0].length][0] = ToAdd[0];
        Output[Output[0].length][1] = ToAdd[1];
        return Output;
    }
}
