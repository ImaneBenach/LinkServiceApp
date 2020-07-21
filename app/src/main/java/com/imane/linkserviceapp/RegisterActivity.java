package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.User;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    DatePickerDialog.OnDateSetListener dateSetListener ;
    private static final String TAG = "RegisterActivity";

    Button btnConnexion ;
    Button btnSeConnecter ;

    EditText etEmail, etMdp, etNom, etPrenom, etBirth, etAddress, etCity, etCp ;
    Retrofit retrofit = ConfigAPI.getRetrofitClient();

    private String email;
    private String nom;
    private String prenom;
    private String pwd;
    private String hash_pwd;
    private String birthdate;
    private String address;
    private String city;
    private String cp;



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
        btnSeConnecter = findViewById(R.id.btn_connexion);
        etAddress = findViewById(R.id.et_address);
        etCity = findViewById(R.id.et_city);
        etCp = findViewById(R.id.et_cp);

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

                SimpleDateFormat formater = null;
                Date date = null;
                formater = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = formater.parse(day + "/" + month + "/" + year);
                    etBirth.setText(formater.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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
                } else if (TextUtils.isEmpty(etAddress.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une adresse", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etCity.getText())) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer une ville", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etCp.getText()) && etCp.getText().toString().length() != 5 ) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer un code postal valide", Toast.LENGTH_LONG).show();
                } else {

                    email = etEmail.getText().toString();
                    pwd = etMdp.getText().toString();
                    nom = etNom.getText().toString();
                    prenom = etPrenom.getText().toString();
                    address = etAddress.getText().toString();
                    city = etCity.getText().toString();
                    cp = etCp.getText().toString();

                    Date inputDate = null;

                    SimpleDateFormat formatDateDisplayed = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        inputDate = formatDateDisplayed.parse(etBirth.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                    if (inputDate != null){
                        SimpleDateFormat formatDateStocked = new SimpleDateFormat("yyyy-MM-dd");
                        birthdate = formatDateStocked.format(inputDate);
                    }

                    if (birthdate.length() == 0){
                        Toast.makeText(getApplicationContext(), "Nous avons un problème technique, réessayer ultérieurement", Toast.LENGTH_LONG).show();
                    }else{
                        //Hash password
                        hash_pwd = null;
                        try {
                            hash_pwd = API.passwordHash(pwd);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        // Check mail exist :
                        UserAPI userAPI = retrofit.create(UserAPI.class);
                        Call<List<User>> callUserEmail = userAPI.getByMail(email);
                        callUserEmail.enqueue(
                                new Callback<List<User>>() {
                                    @SuppressLint("LongLogTag")
                                    @Override
                                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                        if (response.code() == 200){
                                            List<User> usersEmail = (List<User>) response.body();
                                            if (usersEmail.size() == 0){
                                                // create user and redirection
                                                insertUser();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Email adress already use", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<User>> call, Throwable t) {
                                    }
                                }
                        );
                    }
                }
            }
        });

        btnSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void insertUser(){
        User userToregister = new User(
                this.email,
                this.hash_pwd,
                this.nom,
                this.prenom,
                this.birthdate,
                10,
                this.address,
                this.city,
                this.cp,
                "membre"
        );

        // Call api to insert new user
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<Void> callUserInsert = userAPI.create(userToregister);
        callUserInsert.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (response.code() == 400){
                            Toast.makeText(getApplicationContext(), "Nous avons un problème technique, réessayer ultérieurement", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                    }
                }
        );
    }

}
