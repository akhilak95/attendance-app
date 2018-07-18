package com.example.akhil.webkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        final EditText etUsername = (EditText) findViewById(R.id.enrollmentnumber);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button bLogin = (Button) findViewById(R.id.blogin);
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",""+response);
                        if(response.contains("Invalid")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid enrollment number or password")

                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        } else {


                            session.createLoginSession(username, password,response);




                            Log.e("session",""+response);
                            Intent intent = new Intent(LoginActivity.this,Home
                                    .class);

                            intent.putExtra("username",username);

                            LoginActivity.this.startActivity(intent);

                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(username ,password ,responseListener );
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
