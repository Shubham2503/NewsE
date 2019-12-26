package com.innovative_coder.news;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;

public class settingsActivity extends AppCompatActivity {

    private Switch aSwitch;
    SharedPref sharedpr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpr = new SharedPref(this);
        super.onCreate(savedInstanceState);


        if (sharedpr.loadNightModeState()==true){
            setTheme(R.style.darkTheme);
        }
        else setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.NightSwitch);
        if (sharedpr.loadNightModeState()==true){
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedpr.setNightModeState(true);
                    reStartApp();
                }else {
                    sharedpr.setNightModeState(false);
                    reStartApp();
                }
            }
        });
    }

    public void reStartApp(){
        startActivity(new Intent(getApplicationContext(),settingsActivity.class));
//        super.onBackPressed();
        finish();

//        Intent mStartActivity = new Intent(getApplicationContext(), NewsHomeActivity.class);
//        int mPendingIntentId = 123456;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(settingsActivity.this, mPendingIntentId, mStartActivity,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager) settingsActivity.this.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, 0, mPendingIntent);
//        System.exit(0);

//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),NewsHomeActivity.class));

    }
}
