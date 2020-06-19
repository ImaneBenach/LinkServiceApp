package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button btnConnexion ;

    EditText etEmail, etMdp, etNom, etPrenom, etBirthDate ;
    CheckBox type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnConnexion = (Button)findViewById(R.id.btn_seConnecter);
        etEmail = (EditText)findViewById(R.id.et_email);
        etMdp = (EditText)findViewById(R.id.et_mdp);
        etNom =(EditText) findViewById(R.id.et_nom);
        etPrenom = (EditText)findViewById(R.id.et_prenom) ;
        etBirthDate = findViewById(R.id.et_birth) ;
        type= findViewById(R.id.cb_type);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etEmail.getText()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une adresse mail", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etMdp.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer un mdp", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etNom.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer un nom", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etPrenom.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer un prenom", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etBirthDate.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une date d'anniversaire", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    try {
                        String table = "user";
                        final String email = etEmail.getText().toString();
                        final String mdp = etMdp.getText().toString();
                        final String nom = etNom.getText().toString();
                        final String prenom = etPrenom.getText().toString();
                        final String birthdate = etBirthDate.getText().toString();

                        HashMap<String, String> register = user.register(email, mdp, nom, prenom, "1998-10-01", "admin");
                        HashMap<String, Object> userValue = new HashMap<>();

                        userValue.put("table", "user");
                        userValue.put("values", register);
                        Gson gson = new Gson();
                        String json = gson.toJson(userValue);
                        System.out.println(json);

                        API api = new API();
                        String id = api.sendRequest(json, "create");

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
//                    }
                }
            }
        });
    }
}
