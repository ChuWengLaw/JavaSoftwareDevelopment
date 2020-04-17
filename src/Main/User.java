package Main;  //not sure if this is correct
import java.util.Scanner;    //remove once gui is set up

//Double check method prefixes  (public/private/static ect)
public class User {

    private String UserID;
    private String UserName;
    private String UserPassword;
    private boolean CreateBillboardsPermission;
    private boolean EditAllBillboardPermission;
    private boolean ScheduleBillboardsPermission;
    private boolean EditUsersPermission;

//Default constructor should throw an error because below info is required

    //Constructor
    public User(String UserID, String UserName, String UserPassword, boolean CreateBillboardsPermission, boolean EditAllBillboardPermission, boolean ScheduleBillboardsPermission, boolean EditUsersPermission) {
        this.UserID = UserID;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        this.CreateBillboardsPermission = CreateBillboardsPermission;
        this.EditAllBillboardPermission = EditAllBillboardPermission;
        this.ScheduleBillboardsPermission = ScheduleBillboardsPermission;
        this.EditUsersPermission = EditUsersPermission;

        // ̶c̶l̶i̶c̶k̶i̶n̶g̶ ̶c̶r̶e̶a̶t̶e̶ ̶n̶e̶w̶ ̶u̶s̶e̶r̶ ̶w̶i̶l̶l̶ ̶c̶r̶e̶a̶t̶e̶ ̶a̶ ̶n̶e̶w̶ ̶U̶s̶e̶r̶ ̶O̶b̶j̶e̶c̶t̶ ̶a̶n̶d̶ ̶w̶i̶l̶l̶ ̶n̶e̶e̶d̶ ̶t̶o̶ ̶s̶e̶n̶d̶ ̶t̶o̶ ̶S̶Q̶L̶
        // No matter what, a new user object will be created, the difference is for new user,
        // they will need to type in their prefer user name and password, and store it into SQL,
        // where old user just retrieve data from SQL.

    }

//Get Methods to be called to check user's information
    public String GetUserID(){
        return UserID;
    }

    public String GetUserName(){
        return UserName;
    }

    public String GetUserPassword(){
        return UserPassword;
    }

//Get Methods to be called to check if user has permissions to call above functions
    public Boolean GetCreateBillboardsPermission() {
        return CreateBillboardsPermission;
    }

    public Boolean GetEditAllBillboardPermission() {
        return EditAllBillboardPermission;
    }

    public Boolean GetScheduleBillboardsPermission() {
        return ScheduleBillboardsPermission;
    }

    public Boolean GetEditUsersPermission() {
        return EditUsersPermission;
    }

}


