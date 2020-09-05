package in.afckstechnologies.afcksadminpanel.activity;


import in.afckstechnologies.afcksadminpanel.R;
import in.afckstechnologies.afcksadminpanel.utils.AppStatus;
import in.afckstechnologies.afcksadminpanel.utils.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminPanelDashboardActivity extends AppCompatActivity {
    Button takeAppAccessDetalis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_dashboard);
        takeAppAccessDetalis=(Button)findViewById(R.id.takeAppAccessDetalis);
        takeAppAccessDetalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Intent intent = new Intent(getApplicationContext(), UserAfcksDispalyActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
