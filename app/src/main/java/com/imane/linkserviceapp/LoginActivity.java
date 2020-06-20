package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.Classes.API;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btnInscription, btnSeConnecter ;
    EditText etEmail, etMdp ;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnInscription = (Button) findViewById(R.id.btn_inscription);
        btnSeConnecter = (Button) findViewById(R.id.btn_seConnecter);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMdp = (EditText) findViewById(R.id.et_mdp) ;
        btnSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(etEmail.getText()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une adresse mail", Toast.LENGTH_LONG).show();
                }

                else if(TextUtils.isEmpty(etMdp.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer un mdp", Toast.LENGTH_LONG).show();
                }
                else{
                    User user = new User();
                    final String email = etEmail.getText().toString();
                    final String mdp = etMdp.getText().toString();
                    String userId = "";

                    try {
                        String table =  "user";

                        HashMap<String,String> login = user.signin(email, mdp);
                        // System.out.println(login);
                        HashMap<String, Object> userValue = new HashMap<>();

                        userValue.put("table", "user");
                        userValue.put("values", login);
                        System.out.println(userValue);
                        Gson gson = new Gson();
                        String json = gson.toJson(userValue);

                        API api = new API();
                        userId = api.sendRequest(json, "connection");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!userId.equals("") && !userId.equals("null")){
                        Log.i("USERID", userId);
                        User userConnected = gson.fromJson(userId, User.class);
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        intent.putExtra("userId",userConnected.getId());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cette combinaison est inconnue", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}

