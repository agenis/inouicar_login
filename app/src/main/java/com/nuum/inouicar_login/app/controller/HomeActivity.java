package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuum.inouicar_login.R;

public class HomeActivity extends AppCompatActivity {

    private TextView email, name;
    private Button Btn_logout;


    // aidé par le site https://www.youtube.com/watch?v=yS5n40h4Wlg
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // on attend 5 secs et on arrive sur l'autre activité?
        Intent intent = getIntent();
        String extraName = intent.getStringExtra("name");
        String extraEmail = intent.getStringExtra("email");
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        Btn_logout = findViewById(R.id.Btn_logout);

        name.setText(extraName);
        email.setText(extraEmail);

        Btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
