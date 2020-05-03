package Main;//not sure if this is correct

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

//double check method prefixes  (public/private/static ect)

public class Billboard {

    /* attributes
    User Creator
    Billboard Name
    Text on Billboard
    Colour of Text and background
    Images
    */
//overloading constructors depending on info submitted.  OR
    //One constructor but when you create a new billboard it inputs '0' to show that there was no input
    //when you click create billboard you first enter a name and it checks if it exists

    /**
     *  This function establish a connection to database and　retrieving data from the XML file
     *  holding the address list.
     *  @author Law
     */
    public Billboard() {}

    //use the GetCreateBillboardPermission() method
    /**
     * This function creates a public method to create/edit
     * the selected billboard content in database
     * @author Law
     * @param BillboardName Name of the billboard
     * @param CreatedByUserName Name of the billboard's owner
     * @param BillboardTextColour Text colour of the billboard
     * @param BillboardBackgroundColour Background colour of the billboard
     * @param BillboardMessage Message in the billboard
     * @param BillboardPicture Image Url in the billboard
     * @param BillboardInformation Information in the billboard
     * @exception SQLException if sql query error occursr
     */
    public void CreateEditBillboard(String BillboardName, String CreatedByUserName, String BillboardTextColour,
                                    String BillboardBackgroundColour, String BillboardMessage,
                                    String BillboardPicture, String BillboardInformation) throws SQLException {

        /* Flags for checking billboard existence */
        boolean ExistFlag = false;

        /* Loop through the billboard list and update ExistFlag's status */
        try {
            ResultSet resultSet = Main.statement.executeQuery("SELECT * FROM Billboard");
            while (resultSet.next()) {
                if ( BillboardName.equals(resultSet.getString("BillboardName")) ) {
                    ExistFlag = true;
                }
                else {
                    ExistFlag = false;
                }
            }
            /* If the billboard exists, Update the table row */
            if (ExistFlag == true) {
                ResultSet update = Main.statement.executeQuery("UPDATE Billboard SET UserName = '" + CreatedByUserName + "',TextColour = '" + BillboardTextColour +
                        "',BackGroundColour = '" + BillboardBackgroundColour + "',Message = '" + BillboardMessage + "',Image = '" +
                        BillboardPicture + "',Information = '" + BillboardInformation + "' WHERE BillboardName = '" + BillboardName + "';");
            }

            /* Else, Insert the table row */
            else {
                ResultSet insert = Main.statement.executeQuery("INSERT INTO Billboard VALUES ('" +
                        BillboardName + "','" + CreatedByUserName + "','" + BillboardTextColour +
                        "','" + BillboardBackgroundColour + "','" + BillboardMessage + "','" +
                        BillboardPicture + "','" + BillboardInformation + "');");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function creates a public method to retrieve
     * the selected billboard information from database
     * @author Law
     * @param BillBoardName name of the billboard
     * @exception SQLException if sql query error occurs
     */
    public void GetBillboardInfo(String BillBoardName) throws SQLException {
        try {
            ResultSet info = Main.statement.executeQuery("SELECT Information FROM Billboard WHERE BillboardName = '"
                    + BillBoardName + "';");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This function creates a public method to retrieve
     * all of the billboards' contents from database
     * @author Law
     * @param BillBoardName name of the billboard
     * @exception SQLException if sql query error occurs
     */
    public void ListBillboards(String BillBoardName) throws SQLException {
        try {
            ResultSet list = Main.statement.executeQuery("SELECT * FROM Billboard"/*ORDER BY ScheduleValue (i.e. how ever we are going to schedule) ASC*/+";");
        } catch (SQLException e) {
            System.out.println(e);
        }
        //TODO: Order by Schedule
    }

    /**
     * This function creates a public method to delete
     * the selected billboard from database
     * @author Law
     * @param BillBoardName name of the billboard
     * @exception SQLException if sql query error occurs
     */
    public void DeleteBillboard(String BillBoardName) throws SQLException {
        try {
            ResultSet delete = Main.statement.executeQuery("DELETE FROM Billboard WHERE BillboardName = '" + BillBoardName + "';");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

