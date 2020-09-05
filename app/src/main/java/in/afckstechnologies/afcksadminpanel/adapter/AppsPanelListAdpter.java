package in.afckstechnologies.afcksadminpanel.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.activity.DispalyAppsActivity;
import in.afckstechnologies.afcksadminpanel.models.AppsModels;
import in.afckstechnologies.afcksadminpanel.utils.Config;
import in.afckstechnologies.afcksadminpanel.utils.WebClient;


public class AppsPanelListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<AppsModels> data;
    AppsModels current;
    int ID;
    String role_id = "",empAtt="",Response="",fcType="";
    JSONObject jsonLeadObj;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;


    // create constructor to innitilize context and data sent from MainActivity
    public AppsPanelListAdpter(Context context, List<AppsModels> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();


    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity__appslist, parent, false);
        final AppsPanelListAdpter.MyHolder holder = new AppsPanelListAdpter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;


        // Get current position of item in recyclerview to bind data and assign values from list
        final AppsPanelListAdpter.MyHolder myHolder = (AppsPanelListAdpter.MyHolder) holder;
        current = data.get(position);
        myHolder.view_subject.setText(current.getRole_desc());
        myHolder.view_subject.setTag(position);
        myHolder.chkSelected.setTag(position);

        myHolder.chkSelected.setChecked(data.get(position).isSelected());
        myHolder.chkSelected.setTag(data.get(position));

        if (current.getChecked().equals("1")) {
            myHolder.chkSelected.setChecked(true);
            // myHolder.viewchkSelected.setVisibility(View.VISIBLE);
            Config.VALUE.add(data.get(pos).getRole_id());
            current.setSelected(true);
        }

        myHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                AppsModels contact = (AppsModels) cb.getTag();
                contact.setSelected(cb.isChecked());
                data.get(pos).setSelected(cb.isChecked());
                //  Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                if (cb.isChecked()) {
                    //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getId(), Toast.LENGTH_LONG).show();

                    role_id = data.get(pos).getRole_id();
                    //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getRole_id(), Toast.LENGTH_LONG).show();
                    if (role_id.equals("2")) {
                        empAtt="1";
                        new updateEmpAtt().execute();
                    } else if (role_id.equals("4")) {
                        fcType="1";
                       new updateFaculty().execute();
                    }

                } else if (!cb.isChecked()) {
                    //  Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getRole_id(), Toast.LENGTH_LONG).show();
                    role_id = data.get(pos).getRole_id();
                    if (role_id.equals("2")) {
                        empAtt="0";
                        new updateEmpAtt().execute();
                    } else if (role_id.equals("4")) {
                        fcType="0";
                        new updateFaculty().execute();
                    }
                }

            }
        });

        myHolder.view_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);


            }
        });


    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView view_subject;
        public CheckBox chkSelected, viewchkSelected;


        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_subject = (TextView) itemView.findViewById(R.id.view_subject);

            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);


        }


    }


    private class updateEmpAtt extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("attendance_flag", empAtt);
                        put("id", preferences.getString("AUID",""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            Response = serviceAccess.SendHttpPost(Config.URL_UPDATEDEMPATTAPP, jsonLeadObj);
            Log.i("resp", "Response" + Response);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

        }
    }

    private class updateFaculty extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("faculty_flag", fcType);
                        put("id", preferences.getString("AUID",""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            Response = serviceAccess.SendHttpPost(Config.URL_UPDATEDFACULTYUSERAPP, jsonLeadObj);
            Log.i("resp", "Response" + Response);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

        }
    }

    //
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
    // method to access in activity after updating selection
    public List<AppsModels> getSservicelist() {
        return data;
    }


}

