package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.TypesService.ServicesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    Button btnServices, btnMesServices, btnMessages, btnProfil ;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        String UserData = "";


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int userId = getIntent().getIntExtra("userId",0);
        try {
            jsonParamValues.put("where"," WHERE id="+userId);

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
            userConnected = gson.fromJson(UserData, User.class);
        }
        Log.i("USER CONNECTED", userConnected.getName());

        btnServices = (Button) findViewById(R.id.btnServices);
        btnMesServices = (Button) findViewById(R.id.btnMesServices);
        btnMessages = (Button) findViewById(R.id.btnMessages);
        btnProfil = (Button) findViewById(R.id.btnProfil);


        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();
            }
        });

        btnMesServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, MesServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();

            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MessagesActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProfilActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();

            }
        });

    }
}
