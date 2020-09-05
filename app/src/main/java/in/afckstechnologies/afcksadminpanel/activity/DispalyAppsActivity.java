package in.afckstechnologies.afcksadminpanel.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import in.afckstechnologies.afcksadminpanel.Jsonparser.JsonHelper;
import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.adapter.AppsPanelListAdpter;
import in.afckstechnologies.afcksadminpanel.adapter.UserPanelListAdpter;
import in.afckstechnologies.afcksadminpanel.models.AppsModels;
import in.afckstechnologies.afcksadminpanel.models.UserModel;
import in.afckstechnologies.afcksadminpanel.utils.Config;
import in.afckstechnologies.afcksadminpanel.utils.WebClient;

public class DispalyAppsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
     String id,FullName;
     TextView name;
     RecyclerView afcksAppsList;
    JSONObject jsonObj;
    JSONArray jsonArray;
    String userListResponse="";
    List<AppsModels> data;
    AppsPanelListAdpter appsPanelListAdpter;
    CheckBox chkAll;
    TextView submit;
    int cnt=0;
    ProgressDialog mProgressDialog;
    JSONObject jsonLeadObj;
    String addRoleRespone="",msg="";
    Boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispaly_apps);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        Intent intent=getIntent();
                id=intent.getStringExtra("id");
                prefEditor.putString("AUID",id);
                prefEditor.commit();
                FullName=intent.getStringExtra("FullName");
        name=(TextView)findViewById(R.id.name);
        submit=(TextView)findViewById(R.id.submit);
        name.setText(FullName);
        afcksAppsList=(RecyclerView)findViewById(R.id.afcksAppsList);
        new getAdminPanelList().execute();
        /** Getting reference to checkbox available in the main.xml layout */
        chkAll = (CheckBox) findViewById(R.id.chkAllSelected);

        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox cb = (CheckBox) v;
                //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {

                    List<AppsModels> list = ((AppsPanelListAdpter) appsPanelListAdpter).getSservicelist();
                    for (AppsModels workout : list) {
                        workout.setSelected(true);

                    }

                    ((AppsPanelListAdpter) afcksAppsList.getAdapter()).notifyDataSetChanged();
                } else {

                    List<AppsModels> list = ((AppsPanelListAdpter) appsPanelListAdpter).getSservicelist();
                    for (AppsModels workout : list) {
                        workout.setSelected(false);

                    }

                    ((AppsPanelListAdpter) afcksAppsList.getAdapter()).notifyDataSetChanged();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AppsModels> stList = ((AppsPanelListAdpter) appsPanelListAdpter).getSservicelist();
                  cnt=0;
                for (int i = 0; i < stList.size(); i++) {
                    AppsModels serviceListDAO = stList.get(i);
                    if (serviceListDAO.isSelected() == true) {
                        cnt=cnt+Integer.parseInt(serviceListDAO.getRole_id());
                    } else {
                       // cnt=0;
                        System.out.println("not selected");
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DispalyAppsActivity.this);
                builder.setMessage("Do you want to Update App Status "+cnt+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                 //Toast.makeText(getApplicationContext(),""+cnt,Toast.LENGTH_SHORT).show();
                               new  updateRoleStatusData().execute();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Updating App Status");
                alert.show();


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

                        put("user_id", id);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            //  String baseURL = "http://192.168.1.13:8088/srujanlms_new/api/Leadraw/showleadraw";
            Log.i("json", "json" + jsonObj);
            userListResponse = serviceAccess.SendHttpPost(Config.URL_GETUSERDEPARTMENT, jsonObj);
            Log.i("resp", "batchesListResponse" + userListResponse);
            if (userListResponse.compareTo("") != 0) {
                if (isJSONValid(userListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {


                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseAppsPanelList(userListResponse);
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
                appsPanelListAdpter = new AppsPanelListAdpter(DispalyAppsActivity.this, data);
                afcksAppsList.setAdapter(appsPanelListAdpter);
                afcksAppsList.setLayoutManager(new LinearLayoutManager(DispalyAppsActivity.this));
                appsPanelListAdpter.notifyDataSetChanged();

            } else {

            }
        }
    }

    private class updateRoleStatusData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DispalyAppsActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Updating status...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("user_role", cnt);
                        put("user_id",id);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            addRoleRespone = serviceAccess.SendHttpPost(Config.URL_UPDATEUSERROLESSTATUS, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addRoleRespone);


            if (addRoleRespone.compareTo("") != 0) {
                if (isJSONValid(addRoleRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addRoleRespone);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


                    Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                // Close the progressdialog
                mProgressDialog.dismiss();
                //   getBatchSelect(batch_id);
            } else {
                // Close the progressdialog
                mProgressDialog.dismiss();

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
