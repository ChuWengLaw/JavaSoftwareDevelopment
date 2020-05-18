package Main.schedule;

import Main.Main;

import javax.print.DocFlavor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestSchedule {


    public static String What_Billboard_To_Display() throws SQLException, ParseException {
        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  schedule");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int Min_in_Millis = 60000;
        int Hour_in_Millis = 60*Min_in_Millis;
        int Day_in_Millis = 24*Hour_in_Millis;
        String Billboard_to_Display = "No Billboard should be displayed";

        outer:
        while(resultSet.next()) {
            String Billboard_Title = resultSet.getString(1);
            String Scheduled_Start_Time = resultSet.getString(2);
            int Duration = resultSet.getInt(3);
            String Recur_Type = resultSet.getString(4);
            int Time_Recur = resultSet.getInt(5);

            Scheduled_Start_Time = Scheduled_Start_Time.substring(0, Scheduled_Start_Time.indexOf("."));
            long Start_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time,formatter);
            long End_Time_Milli = DateStr_2_Millis(Scheduled_Start_Time,formatter) + (Min_in_Millis*Duration);
            long java_Current_Time = System.currentTimeMillis();
            long Recur_Start = Start_Time_Milli;
            long Recur_End = End_Time_Milli;

            if (java_Current_Time > Start_Time_Milli && java_Current_Time < End_Time_Milli)
            {
                Billboard_to_Display = Billboard_Title;
                break outer;
            }
            else if (!Recur_Type.equals("None"))   //change
            {
                while (!(java_Current_Time < Recur_Start))
                {
                    if (java_Current_Time > Recur_Start && java_Current_Time < Recur_End)
                    {
                        Billboard_to_Display = Billboard_Title;
                        break outer;
                    }
                    if (Recur_Type.equals("Day"))
                    {
                        Recur_Start = Recur_Start + Day_in_Millis;
                        Recur_End = Recur_End + Day_in_Millis;
                    }
                    else if (Recur_Type.equals("Hour"))
                    {
                        Recur_Start = Recur_Start + Hour_in_Millis;
                        Recur_End = Recur_End + Hour_in_Millis;
                    }
                    else if (Recur_Type.equals("Minute"))
                    {
                        Recur_Start = Recur_Start + (Min_in_Millis*Time_Recur);
                        Recur_End = Recur_End + (Min_in_Millis*Time_Recur);
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

//    public static void End_Date(String[] start_date, int Duration)
//    {
//        int start_mins=Integer.parseInt(start_date[4]);
//        int How_many_hours_add= (start_mins + Duration)/60;
//        int End_min_time = (start_mins + Duration)%60;
//        int End_Hour_time = Integer.parseInt(start_date[3])+How_many_hours_add;
//        End_Hour_time = End_Hour_time%24;
//        String end_min = "";
//        String end_hour = "";
//        if (End_min_time<10)
//        {
//            end_min = "0"+Integer.toString(End_min_time);
//        }
//        else
//        {
//             end_min = Integer.toString(End_min_time);
//        }
//        if (End_Hour_time < 10)
//        {
//             end_hour = "0"+Integer.toString(End_Hour_time);
//        }
//        else
//        {
//             end_hour = Integer.toString(End_Hour_time);
//        }
//
//
//
//        String End_date = start_date[0]+"-" +start_date[1]+ "-" +start_date[2]+ " "+ end_hour+":"+end_min+":"+start_date[5];
//        System.out.println(End_date);
//    }
//
//
//    public static String[] SplitDate(String input)
//    {
//        //YYYY-MM-DD HH:MI:SS
//        //String[] Temp
//        String[] values = input.split("-| |:");
//        return  values;
//    }
//
//    public static void print_dmy(String[] input)
//    {
//        for (int i = 0; i < input.length;i++)
//        {
//            System.out.println(input[i]);
//        }
//    }
//
//
//    public static String get_java_date()
//    {
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        //System.out.println(formatter.format(date));
//        return formatter.format(date);
//    }
//
//    public static void compare_date(String SQL, String Java)
//    {
//        int compare = SQL.compareTo(Java);
//        if (compare < 0)
//        {
//            System.out.println("SQL is Smaller");
//        }
//        else if (compare > 0)
//        {
//            System.out.println("Java is Smaller");
//        }
//        else if (compare == 0)
//        {
//            System.out.println("They are the same");
//        }
//    }
}
