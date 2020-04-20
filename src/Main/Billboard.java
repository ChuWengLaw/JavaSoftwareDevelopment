package Main;//not sure if this is correct

import java.util.Scanner;    //remove once gui is set up


//double check method prefixes  (public/private/static ect)

public class Billboard {
    /* attributes
    User Creator
    Billboard Name
    Text on Billboard
    Colour of Text and background
    Images
    */

    String BillboardName;
    String CreatedByUserName;
    String BillboardTextColour;
    String BillboardBackgroundColour;
    String BillboardMessage;
    String BillboardPicture;
    String BillboardInformation;

//overloading constructors depending on info submitted.  OR
    //One constructor but when you create a new billboard it inputs '0' to show that there was no input
    //when you click create billboard you first enter a name and it checks if it exists

    //Constructor
    public Billboard(String BillboardName, String CreatedByUserName, String BillboardTextColour, String BillboardBackgroundColour, String BillboardMessage, String BillboardPicture, String BillboardInformation) {
        this.BillboardName = BillboardName;
        this.CreatedByUserName = CreatedByUserName;
        this.BillboardTextColour = BillboardTextColour;
        this.BillboardBackgroundColour = BillboardBackgroundColour;
        this.BillboardMessage = BillboardMessage;
        this.BillboardPicture = BillboardPicture;
        this.BillboardInformation = BillboardInformation;
    }

    /**use the GetCreateBillboardPermission() method
     * @author Callum
     * @param BillBoardName
     * @return
     */
    public String CreateBillboard(String BillBoardName)
    {
        //check if billboard already exists, if it doesnt create a new one.

        //gui that gets the info you want it to store
        //placeholder code
        Scanner input = new Scanner(System.in);
        String myString = input.next();

        Billboard placeholderVariable = new Billboard(BillBoardName, )


        return "sql code";
        /*
        select * from billboards where billboardname = 'input';

        if the returned value is not null
        {
        update billboards
        set (billboardname=..., textcolour=..., message=..., image=..., information=...)
        where billboardname = input;
        }
        else{
        insert into billboards (billboardname, textcolour, message, image, information)
        values(...);
        }
         */
    }

    /*
    -------------------------------DELETE BILLBOARD-------------------------------------------
    delete from billboards
    where billboardname = input;

    -----------------------------LIST BILLBOARD-----------------------------------------------
    select * from billboards;

    ----------------------------GET BILLBOARD INFO--------------------------------------------
    select information from billboards
    where billboardname = input;

    ----------------------------VIEW BILLBOARD------------------------------------------------
    select * from billboards
    order by schedule;
     */

    

}

