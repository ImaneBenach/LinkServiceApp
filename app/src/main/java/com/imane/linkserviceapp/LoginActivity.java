package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.Classes.API;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    Button btnInscription, btnSeConnecter ;
    EditText etEmail, etMdp ;
    private String url = "http://10.0.2.2:4000/connection";

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

                    try {
                        String login = user.signin(email, mdp);
                        // System.out.println(login);

                        API api = new API();
                        String id = api.sendRequest(login, "connection");
                        System.out.println("print = " + id);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
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

