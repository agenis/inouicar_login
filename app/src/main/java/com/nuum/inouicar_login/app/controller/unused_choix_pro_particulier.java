package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuum.inouicar_login.R;



public class unused_choix_pro_particulier extends AppCompatActivity {

    private Button activity_part;
    private Button activity_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unused_choix_pro_particulier);

        activity_part = findViewById(R.id.activity_part);
        activity_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(unused_choix_pro_particulier.this, CatalogueActivity.class));
            }
        });
        activity_pro = findViewById(R.id.activity_pro);
        activity_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(unused_choix_pro_particulier.this, CatalogueActivity.class));
            }
        });
    }
}
