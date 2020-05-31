package Server;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class BillboardSQL {
    public String backgroundColour, message, textColour, image, information, informationColour;

    /**
     * BillboardSQL constructor.
     */
    public BillboardSQL() {
    }

    /**
     * This function creates a public method to create/edit
     * the selected billboard content in database
     *
     * @param BillboardName             Name of the billboard
     * @param CreatedByUserName         Name of the billboard's owner
     * @param BillboardTextColour       Text colour of the billboard
     * @param BillboardBackgroundColour Background colour of the billboard
     * @param BillboardMessage          Message in the billboard
     * @param BillboardPicture          Image Url in the billboard
     * @param BillboardInformation      Information in the billboard
     * @throws SQLException if sql query error occurs
     * @author Law
     */
    public void CreateBillboard(String BillboardName, String CreatedByUserName, String BillboardTextColour,
                                String BillboardBackgroundColour, String BillboardMessage,
                                String BillboardPicture, String BillboardInformation, String InformationColour) throws SQLException {

        /* Flags for checking billboard existence */
        boolean ExistFlag = false;

        /* Loop through the billboard list and update ExistFlag's status */
        try {
            ResultSet resultSet = Server.statement.executeQuery("SELECT * FROM Billboard");
            while (resultSet.next()) {
                if (BillboardName.equals(resultSet.getString("BillboardName"))) {
                    ExistFlag = true;
                    break;
                } else {
                    ExistFlag = false;
                }
            }
            //If Billboard exists, return an error saying to use edit instead
            if (ExistFlag == true) {
                ResultSet update = Server.statement.executeQuery("UPDATE Billboard SET TextColour = '" + BillboardTextColour + "',BackGroundColour = '" +
                        BillboardBackgroundColour + "',Message = '" + BillboardMessage + "',Image = '" + BillboardPicture +
                        "',Information = '" + BillboardInformation + "',InfoColour = '" + InformationColour + "' WHERE BillboardName = '" + BillboardName + "';");
            }
            //if billboard does not exist it will create a new billboard
            else {
                ResultSet insert = Server.statement.executeQuery("INSERT INTO Billboard VALUES ('" +
                        BillboardName + "','" + CreatedByUserName + "','" + BillboardTextColour +
                        "','" + BillboardBackgroundColour + "','" + BillboardMessage + "','" +
                        BillboardPicture + "','" + BillboardInformation + "','" + InformationColour + "');");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function executes SQL to extract the billboard contents
     *
     * @param BillboardName
     * @throws SQLException
     * @author Law
     */
    public void EditBillboard(String BillboardName) throws SQLException {
        ResultSet getBillboard = Server.statement.executeQuery("SELECT * FROM Billboard WHERE BillboardName = '"
                + BillboardName + "';");
        getBillboard.next();
        backgroundColour = getBillboard.getString("BackGroundColour");
        message = getBillboard.getString("Message");
        textColour = getBillboard.getString("TextColour");
        image = getBillboard.getString("Image");
        information = getBillboard.getString("Information");
        informationColour = getBillboard.getString("InfoColour");
    }

    /**
     * This function executes SQL to edit the billboard contents
     *
     * @param BillboardName
     * @param BillboardTextColour
     * @param BillboardBackgroundColour
     * @param BillboardMessage
     * @param BillboardPicture
     * @param BillboardInformation
     * @param InformationColour
     * @throws SQLException
     * @author Law
     */
    public void EditBillboard(String BillboardName, String BillboardTextColour, String BillboardBackgroundColour, String BillboardMessage,
                              String BillboardPicture, String BillboardInformation, String InformationColour) throws SQLException {
        ResultSet update = Server.statement.executeQuery("UPDATE Billboard SET TextColour = '" + BillboardTextColour + "',BackGroundColour = '" +
                BillboardBackgroundColour + "',Message = '" + BillboardMessage + "',Image = '" + BillboardPicture +
                "',Information = '" + BillboardInformation + "',InfoColour = '" + InformationColour + "' WHERE BillboardName = '" + BillboardName + "';");
    }

    /**
     * This function creates a public method to retrieve
     * the selected billboard contents from database
     *
     * @param BillBoardName name of the billboard
     * @throws SQLException if sql query error occurs
     * @author Law
     */
    public void GetBillboardInfo(String BillBoardName) throws SQLException {
        ResultSet getBillboard = Server.statement.executeQuery("SELECT * FROM Billboard WHERE BillboardName = '"
                + BillBoardName + "';");
        getBillboard.next();
        backgroundColour = getBillboard.getString("BackGroundColour");
        message = getBillboard.getString("Message");
        textColour = getBillboard.getString("TextColour");
        image = getBillboard.getString("Image");
        information = getBillboard.getString("Information");
        informationColour = getBillboard.getString("InfoColour");
    }

    /**
     * This function creates a public method to retrieve
     * all of the billboards' contents from database
     *
     * @throws SQLException if sql query error occurs
     * @author Law
     */
    public JTable ListBillboards() throws SQLException {
        JTable table = new JTable();

        try {
            ResultSet list = Server.statement.executeQuery("SELECT * FROM Billboard"/*ORDER BY ScheduleValue (i.e. how ever we are going to schedule) ASC*/ + ";");
            ResultSetMetaData rsmd = list.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Vector columnHeader = new Vector(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                columnHeader.add(rsmd.getColumnName(i));
            }
            Vector data = new Vector();
            Vector row = new Vector();

            while (list.next()) {
                row = new Vector(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(list.getString(i));
                }
                data.add(row);
            }
            table = new JTable(data, columnHeader);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return table;
    }

    /**
     * This function creates a public method to delete
     * the selected billboard from database
     *
     * @param BillBoardName name of the billboard
     * @throws SQLException if sql query error occurs
     * @author Law
     */
    public void DeleteBillboard(String BillBoardName) throws SQLException {
        try {
            ResultSet deleteBB = Server.statement.executeQuery("DELETE FROM Billboard WHERE BillboardName = '" + BillBoardName + "';");
            ResultSet deleteSchedule = Server.statement.executeQuery("DELETE FROM Schedule WHERE BillboardName = '" + BillBoardName + "';");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function checks if the request user is the owner of the billboard
     *
     * @param BillboardName
     * @param userName
     * @return True if user is the owner
     * @throws SQLException
     */
    public boolean checkBillboardUser(String BillboardName, String userName) throws SQLException {
        boolean existing = false;

        ResultSet resultSet = Server.statement.executeQuery("SELECT UserName FROM Billboard WHERE BillboardName = '" + BillboardName + "';");
        resultSet.next();
        if (userName.equals(resultSet.getString("UserName"))) {
            existing = true;
        }
        return existing;
    }
}

