package Server;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public ArrayList<String[]> ScheduledInformation() throws SQLException {
        ArrayList<String[]> ArrayList = new ArrayList<String[]>(1);

        try {
            ResultSet resultset = Server.statement.executeQuery("SELECT BillboardName, ScheduleTime FROM Schedule;");
            ResultSetMetaData rsmd = resultset.getMetaData();
            int columnCount = rsmd.getColumnCount();



            while(resultset.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i-1] = resultset.getString(i);
                }
                ArrayList.add(row);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return ArrayList;
    }

    public String GetTitleCurrentScheduled() throws SQLException {
        String Billboard_to_Display = null;
        //System.out.println("Stuck");
        try {
            ResultSet resultSet = Server.statement.executeQuery("SELECT * FROM  schedule");
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int Min_in_Millis = 60000;
            int Hour_in_Millis = 60*Min_in_Millis;
            int Day_in_Millis = 24*Hour_in_Millis;
            //System.out.println("Stuck2");

            while(resultSet.next()) {
                //System.out.println("StuckWhile1");
                String Billboard_Title = resultSet.getString(1);
                String Scheduled_Start_Time = resultSet.getString(2);
                int Duration = resultSet.getInt(3);
                int Recur_Type = resultSet.getInt(4);
                int Time_Recur = resultSet.getInt(5);
                //System.out.println(Recur_Type);
                Scheduled_Start_Time = Scheduled_Start_Time.substring(0, Scheduled_Start_Time.indexOf("."));
                long Start_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time,formatter);
                long End_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time,formatter) + (Min_in_Millis*Duration);
                long java_Current_Time = System.currentTimeMillis();
                long Recur_Start = Start_Time_Milli;
                long Recur_End = End_Time_Milli;

                if (java_Current_Time > Start_Time_Milli && java_Current_Time < End_Time_Milli)
                {
                    Billboard_to_Display = Billboard_Title;
                }
                else if (Recur_Type != 0)   //change
                {
                    while (!(java_Current_Time < Recur_Start))
                    {
                        //System.out.println(java_Current_Time);
                        //System.out.println(Recur_Start);
                        if (java_Current_Time > Recur_Start && java_Current_Time < Recur_End)
                        {
                            Billboard_to_Display = Billboard_Title;
                        }
                        if (Recur_Type == 1)
                        {
                            Recur_Start = Recur_Start + Day_in_Millis;
                            Recur_End = Recur_End + Day_in_Millis;
                        }
                        else if (Recur_Type == 2)
                        {
                            Recur_Start = Recur_Start + Hour_in_Millis;
                            Recur_End = Recur_End + Hour_in_Millis;
                        }
                        else if (Recur_Type == 3)
                        {
                            Recur_Start = Recur_Start + (Min_in_Millis*Time_Recur);
                            Recur_End = Recur_End + (Min_in_Millis*Time_Recur);
                        }
                    }
                }
            }

            return Billboard_to_Display;

        } catch (SQLException | ParseException e) {
            System.out.println(e);
        }


        return Billboard_to_Display;
    }
    public static long DateStr_2_Millis(String In_Date, SimpleDateFormat Format) throws ParseException {
            Date date = Format.parse(In_Date);
            long millis = date.getTime();
            return millis;
    }

    public static String[] GetObjectCurrentScheduled(String InputBillboardName) throws SQLException {
        try {
            ResultSet resultSet = Server.statement.executeQuery("SELECT * FROM  Billboard");
            while(resultSet.next()) {
                if ( InputBillboardName.equals(resultSet.getString("BillboardName")) )
                {
                    String BillboardName = resultSet.getString(1);
                    String UserName = resultSet.getString(2);
                    String TextColour = resultSet.getString(3);
                    String BackGroundColour = resultSet.getString(4);
                    String Message  = resultSet.getString(5);
                    String Image  = resultSet.getString(6);
                    String Information = resultSet.getString(7);
                    String InfoColour = resultSet.getString(8);
                    String[] Output = {BillboardName,UserName,TextColour,BackGroundColour,Message,Image,Information,InfoColour};
                    return Output;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}