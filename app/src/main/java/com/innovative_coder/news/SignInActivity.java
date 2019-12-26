package com.innovative_coder.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.innovative_coder.news.common.common;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView textView = findViewById(R.id.txtskip);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),NewsHomeActivity.class));
                finish();
            }
        });

        Button btn = findViewById(R.id.materialButton);

        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.LoginTheme)
                                        .setLogo(R.mipmap.logo).build(),
                                common.SIGN_IN_REQUEST_CODE);
                    }
                }
        });


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

    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getBaseContext(),NewsHomeActivity.class));
            finish();
        }
    }
}
