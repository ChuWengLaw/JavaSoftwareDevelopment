package Server;

import javax.swing.*;
import java.sql.*;
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

}
