package in.afckstechnologies.afcksadminpanel.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.activity.DispalyAppsActivity;
import in.afckstechnologies.afcksadminpanel.activity.EditUserActivity;
import in.afckstechnologies.afcksadminpanel.models.UserModel;


public class UserPanelListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<UserModel> data;
    UserModel current;
    int ID;

    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;


    // create constructor to innitilize context and data sent from MainActivity
    public UserPanelListAdpter(Context context, List<UserModel> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();


    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity__splash, parent, false);
        final UserPanelListAdpter.MyHolder holder = new UserPanelListAdpter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;


        // Get current position of item in recyclerview to bind data and assign values from list
        final UserPanelListAdpter.MyHolder myHolder = (UserPanelListAdpter.MyHolder) holder;
        current = data.get(position);
        myHolder.view_subject.setText(current.getFirst_name() + " " + current.getLast_name());
        myHolder.view_subject.setTag(position);
        myHolder.editUser.setTag(position);
        if (preferences.getString("Status_id", "").equals("0")) {
            myHolder.editUser.setVisibility(View.GONE);
        } else {
            myHolder.editUser.setVisibility(View.VISIBLE);
        }
        myHolder.view_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                if (preferences.getString("Status_id", "").equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(current.getFirst_name() + " " + current.getLast_name() + " is currently disabled would you like to enable it?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id1) {

                                    Intent intent = new Intent(context, DispalyAppsActivity.class);
                                    intent.putExtra("id", current.getId());
                                    intent.putExtra("FullName", current.getFirst_name() + " " + current.getLast_name());
                                    context.startActivity(intent);
                                    dialog.cancel();
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
                    alert.setTitle("User Enable");
                    alert.show();

                } else {
                    Intent intent = new Intent(context, DispalyAppsActivity.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("FullName", current.getFirst_name() + " " + current.getLast_name());
                    context.startActivity(intent);
                }

            }
        });

        myHolder.editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = (Integer) v.getTag();
                Log.e("", "list Id" + ID);
                current = data.get(ID);
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("id", current.getId());
                intent.putExtra("f_name", current.getFirst_name());
                intent.putExtra("l_name", current.getLast_name());
                intent.putExtra("mobile_no", current.getMobile_no());
                intent.putExtra("email_id", current.getEmail_id());
                intent.putExtra("gender", current.getGender());
                intent.putExtra("shiftid", current.getShift_id());
                intent.putExtra("wfh", current.getWfh_applicable_status());
                intent.putExtra("leavecnt", current.getYearly_leaves());
                intent.putExtra("faculty_type", current.getFaculty_flag());
                intent.putExtra("empFeesOptions", current.getFees_options_status());
                context.startActivity(intent);
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
        ImageView editUser;

        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            view_subject = (TextView) itemView.findViewById(R.id.view_subject);
            editUser = (ImageView) itemView.findViewById(R.id.editUser);

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

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            ArrayList<String> parts = smsManager.divideMessage(msg);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);

            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


}

