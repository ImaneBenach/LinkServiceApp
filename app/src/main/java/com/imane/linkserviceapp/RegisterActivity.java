package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    DatePickerDialog.OnDateSetListener dateSetListener ;
    private static final String TAG = "RegisterActivity";

    Button btnConnexion ;

    EditText etEmail, etMdp, etNom, etPrenom, etBirth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnConnexion = (Button)findViewById(R.id.btn_seConnecter);
        etEmail = (EditText)findViewById(R.id.et_email);
        etMdp = (EditText)findViewById(R.id.et_mdp);
        etNom =(EditText) findViewById(R.id.et_nom);
        etPrenom = (EditText)findViewById(R.id.et_prenom) ;
        etBirth = findViewById(R.id.et_birth);

        etBirth.setShowSoftInputOnFocus(false);

        etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);

                String date = year + "-" + month + "-" + day;
                etBirth.setText(date);
            }
        };


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
                } else if (TextUtils.isEmpty(etBirth.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une date d'anniversaire", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    try {
                        String table = "user";
                        final String email = etEmail.getText().toString();
                        final String mdp = etMdp.getText().toString();
                        final String nom = etNom.getText().toString();
                        final String prenom = etPrenom.getText().toString();
                        final String birthdate = etBirth.getText().toString();

                        HashMap<String, String> register = user.register(email, mdp, nom, prenom, birthdate, "admin");
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
