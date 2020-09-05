package in.afckstechnologies.afcksadminpanel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.utils.AppStatus;
import in.afckstechnologies.afcksadminpanel.utils.Config;
import in.afckstechnologies.afcksadminpanel.utils.Constant;
import in.afckstechnologies.afcksadminpanel.utils.WebClient;

public class EditUserActivity extends AppCompatActivity {
    private Spinner spnrUserType;
    CheckBox chkSelectedFaculty, chkSelectedEmpAtt, chkSelectedEmpWFH, chkSelectedEmpFees;
    Button placeBtn;
    private EditText userIdEdtTxt, nameEdtTxt, lastnameEdtTxt, phEdtTxt, emailEdtTxt, leavesEdtTxt;
    RadioGroup radioGroup;
    private RadioButton male, female;
    int pos;

    String id = "";
    String f_name = "";
    String l_name = "";
    String mobile_no = "";
    String email_id = "";
    String gender = "";
    String faculty_type = "0";
    String leavecnt = "0";
    String wfh = "0";
    String empAtt = "0";
    String empFeesOptions = "0";
    String shiftid = "", type = "";

    ProgressDialog mProgressDialog;
    JSONObject jsonLeadObj;
    String addUserRespone = "", msg = "", statusResponse = "",shiftvalue="";
    Boolean status, statusv;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    String newText, flag;
    JSONObject jsonObj1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        //
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        f_name = intent.getStringExtra("f_name");
        l_name = intent.getStringExtra("l_name");
        mobile_no = intent.getStringExtra("mobile_no");
        email_id = intent.getStringExtra("email_id");
        gender = intent.getStringExtra("gender");
        shiftid = intent.getStringExtra("shiftid");
        wfh = intent.getStringExtra("wfh");
        leavecnt = intent.getStringExtra("leavecnt");
        faculty_type = intent.getStringExtra("faculty_type");
        empFeesOptions = intent.getStringExtra("empFeesOptions");

        if (shiftid.equals("0")) {
            shiftvalue = "None";
        } else if (shiftid.equals("1")) {
            shiftvalue = "Weekdays";
        } else if (shiftid.equals("2")) {
            shiftvalue = "Weekend";
        }

        if (leavecnt.equals("")) {
            leavecnt = "0";
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        dataAdapter.add("None");
        dataAdapter.add("Weekend");
        dataAdapter.add("Weekdays");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnrUserType = (Spinner) findViewById(R.id.spnrType);
        spnrUserType.setBackgroundColor(Color.parseColor("#FFFFFF"));
        spnrUserType.setAdapter(dataAdapter);
        int spinnerPosition = dataAdapter.getPosition(shiftvalue);
        spnrUserType.setSelection(spinnerPosition);



        chkSelectedEmpWFH = (CheckBox) findViewById(R.id.chkSelectedEmpWFH);
        chkSelectedEmpFees = (CheckBox) findViewById(R.id.chkSelectedEmpFees);
        userIdEdtTxt = (EditText) findViewById(R.id.userIdEdtTxt);
        userIdEdtTxt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        nameEdtTxt = (EditText) findViewById(R.id.nameEdtTxt);
        lastnameEdtTxt = (EditText) findViewById(R.id.lastNameEdtTxt);
        phEdtTxt = (EditText) findViewById(R.id.phEdtTxt);
        emailEdtTxt = (EditText) findViewById(R.id.emailEdtTxt);
        leavesEdtTxt = (EditText) findViewById(R.id.leavesEdtTxt);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        userIdEdtTxt.setText(id);
        nameEdtTxt.setText(f_name);
        lastnameEdtTxt.setText(l_name);
        phEdtTxt.setText(mobile_no);
        emailEdtTxt.setText(email_id);
        leavesEdtTxt.setText(leavecnt);
        if (gender.equals("Male")) {
            male.setChecked(true);
        }
        if (gender.equals("Female")) {
            female.setChecked(true);
        }
        if (wfh.equals("1")) {
            chkSelectedEmpWFH.setChecked(true);
        }
        else {
            chkSelectedEmpWFH.setChecked(false);
        }

        if (empFeesOptions.equals("1")) {
            chkSelectedEmpFees.setChecked(true);
        }
        else {
            chkSelectedEmpFees.setChecked(false);
        }


        placeBtn = (Button) findViewById(R.id.placeBtn);

        chkSelectedEmpWFH.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    wfh = "1";
                } else {

                    wfh = "0";
                }
            }
        });
        chkSelectedEmpFees.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    empFeesOptions = "1";
                } else {

                    empFeesOptions = "0";
                }
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

                        gender = "Female";
                        // Toast.makeText(getActivity(), "You have Clicked RadioButton 1"+gender,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        gender = "Male";
                        // Toast.makeText(getActivity(), gender, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        //The default selection is RadioButton 1
                        gender = "Female";
                        // Toast.makeText(getActivity(), gender,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        userIdEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                flag = "0";
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                flag = "1";
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailEdtTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                flag = "2";
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(userIdEdtTxt.getText()) && flag.equals("0")) {
                        newText = userIdEdtTxt.getText().toString();
                        getchannelPartnerSelect(newText);


                    }
                    if (!TextUtils.isEmpty(phEdtTxt.getText()) && flag.equals("1")) {
                        newText = phEdtTxt.getText().toString();
                        getchannelPartnerSelect(newText);


                    }
                    if (!TextUtils.isEmpty(emailEdtTxt.getText()) && flag.equals("2")) {
                        newText = emailEdtTxt.getText().toString();
                        getchannelPartnerSelect(newText);

                    }
                }
                return false;
            }
        });
        placeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = userIdEdtTxt.getText().toString();
                f_name = nameEdtTxt.getText().toString();
                l_name = lastnameEdtTxt.getText().toString();
                mobile_no = phEdtTxt.getText().toString();
                email_id = emailEdtTxt.getText().toString();
                leavecnt = leavesEdtTxt.getText().toString();
                type = spnrUserType.getSelectedItem().toString();
                if (type.equals("None")) {
                    shiftid = "0";
                } else if (type.equals("Weekend")) {
                    shiftid = "2";
                } else if (type.equals("Weekdays")) {
                    shiftid = "1";
                }
                if (leavecnt.equals("")) {
                    leavecnt = "0";
                }
                if (validate(gender, id, f_name, l_name, mobile_no, email_id)) {
                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                        new updateRoleStatusData().execute();

                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getchannelPartnerSelect(final String newText) {
        //  Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();

        jsonObj1 = new JSONObject() {
            {
                try {
                    put("Prefixtext", newText);
                    put("status", flag);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                statusResponse = serviceAccess.SendHttpPost(Config.URL_GETALLVALIDATEUSERDATA, jsonObj1);
                Log.i("statusResponse", "statusResponse" + statusResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (statusResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(statusResponse);
                                        statusv = jObject.getBoolean("status");

                                        if (statusv) {
                                            if (flag.equals("0")) {
                                                userIdEdtTxt.setTextColor(Color.parseColor("#FF0000"));
                                            }
                                            if (flag.equals("1")) {
                                                phEdtTxt.setTextColor(Color.parseColor("#FF0000"));
                                            }
                                            if (flag.equals("2")) {
                                                emailEdtTxt.setTextColor(Color.parseColor("#FF0000"));
                                            }

                                            Toast.makeText(getApplicationContext(), "Already exists.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (flag.equals("0")) {
                                                userIdEdtTxt.setTextColor(Color.parseColor("#0000FF"));
                                            }
                                            if (flag.equals("1")) {
                                                phEdtTxt.setTextColor(Color.parseColor("#0000FF"));
                                            }
                                            if (flag.equals("2")) {
                                                emailEdtTxt.setTextColor(Color.parseColor("#0000FF"));
                                            }
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    public boolean validate(String gender, String id, String f_name, String l_name, String mobile_no, String email_id) {
        boolean isValidate = false;
        if (gender.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Gender.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (id.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter ID.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (mobile_no.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter mobile number.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (mobile_no.length() != 10) {
            Toast.makeText(getApplicationContext(), "Please enter a 10 digit valid Mobile No.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else if (f_name.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter First Name.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (l_name.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter Last Name.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (email_id.trim().compareTo("") == 0) {
            Toast.makeText(getApplicationContext(), "Please enter email id.", Toast.LENGTH_LONG).show();
            isValidate = false;

        } else if (!validateEmail(email_id)) {
            Toast.makeText(getApplicationContext(), "Please enter valid Email Id.", Toast.LENGTH_LONG).show();
            isValidate = false;
        } else {
            isValidate = true;
        }
        return isValidate;
    }

    /**
     * email validation
     */
    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");

    public boolean validateEmail(String email) {
        if (!email.contains("@")) {
            return false;
        }
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private class updateRoleStatusData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(EditUserActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            mProgressDialog.setMessage("Updating user...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("pId", id);
                        put("pFirstName", f_name);
                        put("pLastName", l_name);
                        put("pMobileNo", mobile_no);
                        put("pEmailId", email_id);
                        put("pGender", gender);
                        put("pShiftStatus", shiftid);
                        put("pWFHStatus", wfh);
                        put("pEmpYLeaves", leavecnt);
                        put("pFacultyStatus", faculty_type);
                        put("pEmpFeesOptions", empFeesOptions);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            addUserRespone = serviceAccess.SendHttpPost(Config.URL_UPDATEEMPTRAINERS, jsonLeadObj);
            Log.i("resp", "addStudentRespone" + addUserRespone);


            if (addUserRespone.compareTo("") != 0) {
                if (isJSONValid(addUserRespone)) {


                    try {

                        JSONObject jsonObject = new JSONObject(addUserRespone);
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
                finish();
                Intent intent = new Intent(getApplicationContext(), UserAfcksDispalyActivity.class);
                startActivity(intent);
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
