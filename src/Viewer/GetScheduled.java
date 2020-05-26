package Viewer;


import Server.Server;

import javax.print.DocFlavor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetScheduled {

    public static String What_Billboard_To_Display() throws SQLException, ParseException {
        Statement statement = Server.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  schedule");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int Min_in_Millis = 60000;
        int Hour_in_Millis = 60 * Min_in_Millis;
        int Day_in_Millis = 24 * Hour_in_Millis;
        String Billboard_to_Display = "No Billboard should be displayed";

        outer:
        while (resultSet.next()) {
            String Billboard_Title = resultSet.getString(1);
            String Scheduled_Start_Time = resultSet.getString(2);
            int Duration = resultSet.getInt(3);
            String Recur_Type = resultSet.getString(4);
            int Time_Recur = resultSet.getInt(5);

            Scheduled_Start_Time = Scheduled_Start_Time.substring(0, Scheduled_Start_Time.indexOf("."));
            long Start_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time, formatter);
            long End_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time, formatter) + (Min_in_Millis * Duration);
            long java_Current_Time = System.currentTimeMillis();
            long Recur_Start = Start_Time_Milli;
            long Recur_End = End_Time_Milli;

            if (java_Current_Time > Start_Time_Milli && java_Current_Time < End_Time_Milli) {
                Billboard_to_Display = Billboard_Title;
                break outer;
            } else if (!Recur_Type.equals("None"))   //change
            {
                while (!(java_Current_Time < Recur_Start)) {
                    if (java_Current_Time > Recur_Start && java_Current_Time < Recur_End) {
                        Billboard_to_Display = Billboard_Title;
                        break outer;
                    }
                    if (Recur_Type.equals("Day")) {
                        Recur_Start = Recur_Start + Day_in_Millis;
                        Recur_End = Recur_End + Day_in_Millis;
                    } else if (Recur_Type.equals("Hour")) {
                        Recur_Start = Recur_Start + Hour_in_Millis;
                        Recur_End = Recur_End + Hour_in_Millis;
                    } else if (Recur_Type.equals("Minute")) {
                        Recur_Start = Recur_Start + (Min_in_Millis * Time_Recur);
                        Recur_End = Recur_End + (Min_in_Millis * Time_Recur);
                    }
                }
            }
        }
        return Billboard_to_Display;
    }

    public static long DateStr_2_Millis(String In_Date, SimpleDateFormat Format) throws ParseException {
        Date date = Format.parse(In_Date);
        long millis = date.getTime();
        return millis;
    }
}