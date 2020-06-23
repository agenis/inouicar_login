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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button Btn_login;
    private TextView Link_register;
    private TextView Link_register_sinda;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://spajkpw.cluster029.hosting.ovh.net/api/users/signin/";
    //private static String URL_LOGIN = "http://localhost/inouicar/www/api/users/signin/";
    // video tuto https://www.youtube.com/watch?v=yS5n40h4Wlg
    // on peut tester marc@yopmail.com et 123456

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Btn_login = findViewById(R.id.Btn_login);
        Link_register = findViewById(R.id.Link_register);
        Link_register_sinda = findViewById(R.id.Link_register_sinda);

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().toLowerCase().trim();
                String mPass  = password.getText().toString().trim();

                if (!mEmail.isEmpty() && !mPass.isEmpty()){
                    /////////////////////
                    Login(mEmail, mPass);
                    /////////////////////
                } else {
                    if (mEmail.isEmpty()) {
                        email.setError("Please insert a valid email");
                    }
                    if (mPass.isEmpty()) {
                        password.setError("Please insert a password");
                    }
                }

            }
        });

        Link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        Link_register_sinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, unused_registration_form.class));
            }
        });
    }

    private void Login(final String email, final String password) {

        loading.setVisibility(View.VISIBLE);
        Btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i=0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    Toast.makeText(LoginActivity.this, "Success login. \nYour name: "+name+"\nYour email: "+email, Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    Intent intent = new Intent(LoginActivity.this, CatalogueActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            Btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Login failure1: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        Btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Login failure2: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
            return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
