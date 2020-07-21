package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.Messagerie.ChatActivity;
import com.imane.linkserviceapp.Messagerie.MessagesActivity;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    Button btnServices, btnMesServices, btnMessages, btnProfil ;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        btnServices = (Button) findViewById(R.id.btnServices);
        btnMesServices = (Button) findViewById(R.id.btnMesServices);
        btnMessages = (Button) findViewById(R.id.btnMessages);
        btnProfil = (Button) findViewById(R.id.btnProfil);


        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, serviceMenuActivity.class);
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
                Intent intent = new Intent(HomeActivity.this, MessagesActivity.class);
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
