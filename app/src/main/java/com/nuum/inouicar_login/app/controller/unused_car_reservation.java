package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nuum.inouicar_login.R;



public class unused_car_reservation extends AppCompatActivity {

    private TextView clicked_car_concatenate_textview;
    private Button buttonVehicleSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unused_car_reservation);
        String clicked_car_brand =  getIntent().getExtras().getString("clicked_car_brand");
        String clicked_car_model =  getIntent().getExtras().getString("clicked_car_model");
        String clicked_car_color =  getIntent().getExtras().getString("clicked_car_color");
        clicked_car_concatenate_textview = findViewById(R.id.car_description);
        clicked_car_concatenate_textview.setText(clicked_car_brand + " " + clicked_car_model + ", " + clicked_car_color);

        buttonVehicleSearcher = findViewById(R.id.buttonVehicleSearcher);
        buttonVehicleSearcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(unused_car_reservation.this, unused_car_selection.class));
            }
        });
    }
}
