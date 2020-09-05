package in.afckstechnologies.afcksadminpanel.Jsonparser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.afckstechnologies.afcksadminpanel.models.AppsModels;
import in.afckstechnologies.afcksadminpanel.models.UserModel;


/**
 * Created by admin on 2/18/2017.
 */

public class JsonHelper {

    private ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
    private UserModel studentsDAO;

    private ArrayList<AppsModels> appsModelsArrayList = new ArrayList<AppsModels>();
    private AppsModels appsModels;

    //templatePaser
    public ArrayList<UserModel> parseUserPanelList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                studentsDAO = new UserModel();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                studentsDAO.setId(json_data.getString("id"));
                studentsDAO.setFirst_name(json_data.getString("first_name"));
                studentsDAO.setLast_name(json_data.getString("last_name"));
                studentsDAO.setMobile_no(json_data.getString("mobile_no"));
                studentsDAO.setEmail_id(json_data.getString("email_id"));
                studentsDAO.setGender(json_data.getString("gender"));
                studentsDAO.setShift_id(json_data.getString("shift_id"));
                studentsDAO.setWfh_applicable_status(json_data.getString("wfh_applicable_status"));
                studentsDAO.setFaculty_flag(json_data.getString("faculty_flag"));
                studentsDAO.setFees_options_status(json_data.getString("fees_options_status"));
                studentsDAO.setYearly_leaves(json_data.getString("yearly_leaves"));
                userModelArrayList.add(studentsDAO);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userModelArrayList;
    }

    //templatePaser
    public ArrayList<AppsModels> parseAppsPanelList(String rawLeadListResponse) {
        // TODO Auto-generated method stub
        Log.d("scheduleListResponse", rawLeadListResponse);
        try {
            JSONArray leadJsonObj = new JSONArray(rawLeadListResponse);

            for (int i = 0; i < leadJsonObj.length(); i++) {
                appsModels = new AppsModels();
                JSONObject json_data = leadJsonObj.getJSONObject(i);
                appsModels.setRole_id(json_data.getString("role_id"));
                appsModels.setRole_desc(json_data.getString("role_desc"));
                appsModels.setChecked(json_data.getString("checked"));
                appsModelsArrayList.add(appsModels);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return appsModelsArrayList;
    }

}
