package com.innovative_coder.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPf;
    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPf = getSharedPreferences("Brodcast",MODE_PRIVATE);
        firstTime = sharedPf.getBoolean("FirstTime",true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar();

        if (!firstTime){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sharedPf.edit();
                    firstTime = false;
                    editor.putBoolean("FirstTime",firstTime);
                    editor.apply();

                    Intent HomeIntent = new Intent(MainActivity.this,NewsHomeActivity.class);
                    startActivity(HomeIntent);
                    finish();
                }
            },3000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences.Editor editor = sharedPf.edit();
                    firstTime = false;
                    editor.putBoolean("FirstTime",firstTime);
                    editor.apply();

                    Intent HomeIntent = new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(HomeIntent);
                    finish();
                }
            },3000);
        }
    }

    private void hideNavigationBar(){
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                );
    }
}
