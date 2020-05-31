package Server;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class ScheduleSQL {
    public ScheduleSQL() {
    }

    public void ScheduleBillboard(String BillboardName, String DateTime, int Duration,
                                  int RecurType, int RecurAmount, String UserName) throws SQLException {
        boolean ExistFlag = false;
        String CreatedBy = "";
        try {
            //get billboard names and creators
            ResultSet resultSet = Server.statement.executeQuery("SELECT BillboardName, UserName FROM Billboard");
            while (resultSet.next()) {
                //search billboards for the inputted billboard name
                if (BillboardName.equals(resultSet.getString("BillboardName"))) {
                    ExistFlag = true;
                    //store the creators name
                    CreatedBy = resultSet.getString("UserName");
                    break;
                } else {
                    ExistFlag = false;
                }
            }
            if (ExistFlag == true) {
                //if the billbaord exists schedule it with the creators name not the schedulers name
                Server.statement.executeQuery("INSERT INTO Schedule (BillboardName,ScheduleTime, Duration, RecurType, RecurDuration, UserName) VALUES ('" +
                        BillboardName + "','" + DateTime + "','" +
                        Duration + "','" + RecurType + "','" + RecurAmount +
                        "','" + CreatedBy +
                        "');");
            }
            //if billboard does not exist dont schedule
            else {
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void DeleteSchedule(String BillboardName, String DateTime) throws SQLException {
        try {
            //send sql code to delete a scheduled billbaord with correct name and date
            Server.statement.executeQuery("DELETE FROM Schedule WHERE (BillboardName = '" +
                    BillboardName + "' AND ScheduleTime = '" + DateTime + "');");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String[]> ScheduledInformation() throws SQLException {
        ArrayList<String[]> ArrayList = new ArrayList<String[]>(1);
        try {
            //Select the billboard name and the schedule time returned in an array list
            ResultSet resultset = Server.statement.executeQuery("SELECT BillboardName, ScheduleTime FROM Schedule;");
            ResultSetMetaData rsmd = resultset.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (resultset.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultset.getString(i);
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
        try {
            //select all scheduled billboards
            ResultSet resultSet = Server.statement.executeQuery("SELECT * FROM  schedule");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int Min_in_Millis = 60000;
            int Hour_in_Millis = 60 * Min_in_Millis;
            int Day_in_Millis = 24 * Hour_in_Millis;
            //go through all the scheduled billboards and see if there current time is scheduled
            while (resultSet.next()) {
                String Billboard_Title = resultSet.getString(1);
                String Scheduled_Start_Time = resultSet.getString(2);
                int Duration = resultSet.getInt(3);
                int Recur_Type = resultSet.getInt(4);
                int Time_Recur = resultSet.getInt(5);

                Scheduled_Start_Time = Scheduled_Start_Time.substring(0, Scheduled_Start_Time.indexOf("."));
                long Start_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time, formatter);
                long End_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time, formatter) + (Min_in_Millis * Duration);
                long java_Current_Time = System.currentTimeMillis();
                long Recur_Start = Start_Time_Milli;
                long Recur_End = End_Time_Milli;

                //if the current time is during the first scheduled time return the billboard
                if (java_Current_Time > Start_Time_Milli && java_Current_Time < End_Time_Milli) {
                    Billboard_to_Display = Billboard_Title;
                } else if (Recur_Type != 0)//if there is a reoccur continue looping through replay times
                {
                    while (!(java_Current_Time < Recur_Start)) {
                        //add the specified reoccur time
                        //if the new time is in the future and not current time then go to next billboard
                        if (java_Current_Time > Recur_Start && java_Current_Time < Recur_End) {
                            Billboard_to_Display = Billboard_Title;
                        }
                        if (Recur_Type == 1) {
                            Recur_Start = Recur_Start + Day_in_Millis;
                            Recur_End = Recur_End + Day_in_Millis;
                        } else if (Recur_Type == 2) {
                            Recur_Start = Recur_Start + Hour_in_Millis;
                            Recur_End = Recur_End + Hour_in_Millis;
                        } else if (Recur_Type == 3) {
                            Recur_Start = Recur_Start + (Min_in_Millis * Time_Recur);
                            Recur_End = Recur_End + (Min_in_Millis * Time_Recur);
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
        //method to convert a string into a date format
        Date date = Format.parse(In_Date);
        long millis = date.getTime();
        return millis;
    }

    public static String[] GetObjectCurrentScheduled(String InputBillboardName) throws SQLException {
        try {
            //return all the details of a specific billboard in a array
            ResultSet resultSet = Server.statement.executeQuery("SELECT * FROM  Billboard");
            while (resultSet.next()) {
                if (InputBillboardName.equals(resultSet.getString("BillboardName"))) {
                    String BillboardName = resultSet.getString(1);
                    String UserName = resultSet.getString(2);
                    String TextColour = resultSet.getString(3);
                    String BackGroundColour = resultSet.getString(4);
                    String Message = resultSet.getString(5);
                    String Image = resultSet.getString(6);
                    String Information = resultSet.getString(7);
                    String InfoColour = resultSet.getString(8);
                    String[] Output = {BillboardName, UserName, TextColour, BackGroundColour, Message, Image, Information, InfoColour};
                    return Output;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public JTable GetScheduledInfo(SessionToken Token) throws SQLException {
        JTable table = new JTable();
        try {
            //get all the relevant info about the current scheduled billboards and return in a JTable
            ResultSet resultSet = Server.statement.executeQuery("SELECT BillboardName, UserName, ScheduleTime, Duration FROM Schedule;");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector columnHeader = new Vector(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                columnHeader.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();
            //enter the data into the JTables
            while (resultSet.next()) {
                //System.out.println("Loop");
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
            table = new JTable(data, columnHeader);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return table;
    }
}

