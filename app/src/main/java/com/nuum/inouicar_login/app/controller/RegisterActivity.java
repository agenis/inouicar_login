package com.nuum.inouicar_login.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nuum.inouicar_login.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    TextView Link_login;
    private Button Btn_register;
    private ProgressBar loading;
    private static String URL_REGIST = "http://spajkpw.cluster029.hosting.ovh.net/api/users/register/";
    // private static String URL_REGIST = "http://localhost/inouicar/www/api/users/register/";
    // pour tester un user enregistré: marc.agenis@gmail.com, marc, 123456


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading    = findViewById(R.id.loading);
        name       = findViewById(R.id.name);
        email      = findViewById(R.id.email);
        password   = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        Btn_register = findViewById(R.id.Btn_register);
        Link_login = findViewById(R.id.Link_login);

        Btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Regist();
            }
        });

        Link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }



    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        Btn_register.setVisibility(View.GONE);

        final String user_name = this.name.getText().toString().toLowerCase().trim();
        final String user_email = this.email.getText().toString().toLowerCase().trim();
        final String user_password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register success, welcome to InouiCar!", Toast.LENGTH_SHORT).show();
                            }
                            startActivity(new Intent(RegisterActivity.this, CatalogueActivity.class));
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register failure" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            Btn_register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register failure" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        Btn_register.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("user_name", user_name);
                params.put("user_email", user_email);
                params.put("user_password", user_password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
