package Main;  //not sure if this is correct
import java.util.Scanner;    //remove once gui is set up
//double check method prefixes  (public/private/static ect)
public class User {

    String UserName;
    String UserPassword;
    boolean CreateBillboardsPermission;
    boolean EditAllBillboardPermission;
    boolean ScheduleBillboardsPermission;
    boolean EditUsersPermission;

//Default constructor should throw an error because below info is required
    //Constructor
    public User(String UserName, String UserPassword, boolean CreateBillboardsPermission, boolean EditAllBillboardPermission, boolean ScheduleBillboardsPermission, boolean EditUsersPermission) {
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        this.CreateBillboardsPermission = CreateBillboardsPermission;
        this.EditAllBillboardPermission = EditAllBillboardPermission;
        this.ScheduleBillboardsPermission = ScheduleBillboardsPermission;
        this.EditUsersPermission = EditUsersPermission;

        //Send this info to database
        //clicking create new user will create a new User Object and will need to send to SQL


    }


    //Global Variable array of object users placeholder for server communications
    public User Users[] = {};

    public void GetUsers(){
        //Code that connects to Server and retrieves all User Infomation
        //filler code
        //Users[] = {};  //empty array of the users, replace with code that retrieves the actual user infomation
    }

    public String DeleteUser(String UserName){ //input is the username you want to edit not yours
//Code that returns SQL Code to Delete a user.


        return "SQL Code";
    }

    public String EditUser(String UserName) { //input is the username you want to edit not yours
        //should check occur in this function?
        if (EditUsersPermission) return "SQL CODE";
        //or before the this function.  I think BEfore so this code only send SQL code and not any "invalid permission" code
        return "filler code";
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


