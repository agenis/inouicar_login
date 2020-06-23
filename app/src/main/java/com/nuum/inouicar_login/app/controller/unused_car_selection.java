package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuum.inouicar_login.R;



public class unused_car_selection extends AppCompatActivity {

    private Button buttonVehicleSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unused_car_selection);

        buttonVehicleSearcher = findViewById(R.id.buttonVehicleSearcher);
        buttonVehicleSearcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(unused_car_selection.this, unused_choix_pro_particulier.class));
            }
        });
    }
}
