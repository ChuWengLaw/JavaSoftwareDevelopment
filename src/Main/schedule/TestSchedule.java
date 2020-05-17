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

    public String TestDateString;
    public static String test_string = "Nothing";
    public static int Duration;

    public static void getScheduleInfo() throws SQLException, ParseException {
        Statement statement = Main.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  schedule");

        while(resultSet.next()) {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            test_string = resultSet.getString(2);
            Duration = resultSet.getInt(3);
            test_string = test_string.substring(0, test_string.indexOf("."));
            System.out.println("Start Time\t" + test_string);
            int Min_in_Millis = 60000;
            long End_Time_Milli = DateStr_2_Millis(test_string,formatter) + (Min_in_Millis*Duration);
            Date date=new Date(End_Time_Milli);
            System.out.println("End Time\t" + formatter.format(date));
        }

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
