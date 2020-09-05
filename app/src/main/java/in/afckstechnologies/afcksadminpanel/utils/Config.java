package in.afckstechnologies.afcksadminpanel.utils;

import java.util.ArrayList;



public class Config {
    // public static final String BASE_URL = "http://192.168.1.10:80/afcks/api/";
    // public static final String BASE_URL = "http://testprojects.in/afcks/api/";
    public static final String BASE_URL = "https://afckstechnologies.in/afcks/api/";
    public static final String URL_GETAVAILABLEMOBILEDEVICES = BASE_URL + "user/getAvailableMobileDevices";
    public static final String URL_GETUSERNAMEPASSSMS = BASE_URL + "user/getUserNamePassSMS";
    public static final String URL_AVAILABLEADMINPANEL = BASE_URL + "user/availableAdminPanel";
    public static final String URL_UPDATEADMINPANELDETAILS = BASE_URL + "user/updateadminpaneldetails";
    public static final String URL_GETALLUSERSBYSTATUS = BASE_URL + "user/getAllUsersBystatus";
    public static final String URL_GETUSERDEPARTMENT = BASE_URL + "user/GetUserDepartment";
    public static final String URL_UPDATEUSERROLESSTATUS = BASE_URL + "user/updateUserRolesStatus";
    public static final String URL_GETALLTRAINERSTEMPLATE = BASE_URL + "user/getAllTrainersTemplate";
    public static final String URL_ADDNEWEMPTRAINERS= BASE_URL + "user/addNewEmpTrainers";
    public static final String URL_GETALLVALIDATEUSERDATA= BASE_URL + "user/getallValidateUserData";
    public static final String URL_UPDATEEMPTRAINERS= BASE_URL + "user/updateEmpTrainers";
    public static final String URL_UPDATEDEMPATTAPP= BASE_URL + "user/updatedEmpAttApp";
    public static final String URL_UPDATEDFACULTYUSERAPP= BASE_URL + "user/updatedFacultyUserApp";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "AFCKS Images";
    public static String DATA_ENTERLEVEL_COURSES = "";
    public static String DATA_SPLIZATION_COURSES = "";

    public static String DATA_MOVE_FROM_LOCATION = "";
    public static ArrayList<String> VALUE = new ArrayList<String>();
    // public static final String SMS_ORIGIN = "WAVARM";
    public static final String SMS_ORIGIN = "AFCKST";
}
