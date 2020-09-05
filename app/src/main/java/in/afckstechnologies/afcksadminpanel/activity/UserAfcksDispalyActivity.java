package in.afckstechnologies.afcksadminpanel.activity;


import in.afckstechnologies.afcksadminpanel.Jsonparser.JsonHelper;
import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.adapter.UserPanelListAdpter;
import in.afckstechnologies.afcksadminpanel.models.UserModel;
import in.afckstechnologies.afcksadminpanel.utils.Config;
import in.afckstechnologies.afcksadminpanel.utils.WebClient;
import in.afckstechnologies.afcksadminpanel.utils.WebServiceUtility;

import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UserAfcksDispalyActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    RecyclerView afcksUserList;
    RadioGroup radioGroup;
    int pos,Status_id=1;
    JSONObject jsonObj;
    JSONArray jsonArray;
    String userListResponse="";
    List<UserModel> data;
    UserPanelListAdpter userPanelListAdpter;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_add_user_layout);
        afcksUserList = (RecyclerView) findViewById(R.id.afcksUserList);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        prefEditor.putBoolean("0", true);
        prefEditor.commit();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new getAdminPanelList().execute();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAfcksDispalyActivity.this, AddEmpTrainerActivity.class);
                startActivity(intent);

            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos = radioGroup.indexOfChild(findViewById(checkedId));
                switch (pos) {
                    case 1:
                        Status_id = 0;
                        new getAdminPanelList().execute();

                        break;
                    case 2:
                        break;

                    default:
                        //The default selection is RadioButton 1
                        Status_id = 1;
                        new getAdminPanelList().execute();
                        break;
                }
            }
        });

       // getData();

    }

    private void getData() {

        JSONObject json = new JSONObject();
        try {
            json.put("Status", Status_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        WebServiceUtility utility = new WebServiceUtility(UserAfcksDispalyActivity.this);
        utility.getString(Config.URL_GETALLUSERSBYSTATUS, json, new WebServiceUtility.VolleyCallback() {
            @Override
            public void onSuccess(String result) throws OperationApplicationException, RemoteException {

                 Log.d("r",result);
            }

            @Override
            public void onRequestError(VolleyError errorMessage) {

            }
        });
    }

    //
    private class getAdminPanelList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonObj = new JSONObject() {
                {
                    try {
                        prefEditor.putString("Status_id", ""+Status_id);
                        prefEditor.commit();
                        //put("course_id", course_id);
                        put("Status", Status_id);
                        //put("adminpanel_user_id", preferences.getString("adminpanel_user_id",""));

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            JSONObject json = new JSONObject();
            try {
                json.put("Status", Status_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
           // Log.i("json", "json" + jsonObj);
           userListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLUSERSBYSTATUS, jsonObj);
           Log.i("resp", "batchesListResponse" + userListResponse);


            if (userListResponse.compareTo("") != 0) {
                if (isJSONValid(userListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseUserPanelList(userListResponse);
                                jsonArray = new JSONArray(userListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (data.size() > 0) {
                userPanelListAdpter = new UserPanelListAdpter(UserAfcksDispalyActivity.this, data);
                afcksUserList.setAdapter(userPanelListAdpter);
                afcksUserList.setLayoutManager(new LinearLayoutManager(UserAfcksDispalyActivity.this));
                userPanelListAdpter.notifyDataSetChanged();

            } else {

            }
        }
    }

    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
