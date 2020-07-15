package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.Classes.API;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Button btnInscription, btnSeConnecter ;
    EditText etEmail, etMdp ;
    private final Gson gson = new Gson();

    Retrofit retrofit = ConfigAPI.getRetrofitClient();

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

                    final String email = etEmail.getText().toString();
                    final String mdp = etMdp.getText().toString();
                    String userId = "";
                    User user = null;
                    try {
                        user = new User(email, API.passwordHash(mdp));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    UserAPI userAPI = retrofit.create(UserAPI.class);
                    Call callUser = userAPI.connection(user);

                    callUser.enqueue(
                        new Callback<List<User>>() {
                            @Override
                            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                Log.d("response", String.valueOf(response.code()));
                                if(response.code()==200){
                                    List<User> users = response.body();


                                    if(users != null && users.size() > 0){
                                        User userConnected = users.get(0);

                                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                        intent.putExtra("userConnected",userConnected);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Cette combinaison est inconnue", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        }
                    );
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

    private User getUserConnectedInfos(User userId){
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        String UserData = "";

        try {
            jsonParamValues.put("where"," WHERE id="+userId.getId());

            jsonParam.put("table", "user");
            jsonParam.put("values",jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            UserData = API.sendRequest(jsonParam.toString(), "readWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!UserData.equals("")) {
            return gson.fromJson(UserData, User.class);
        } else {
            return null;
        }

    }
}

