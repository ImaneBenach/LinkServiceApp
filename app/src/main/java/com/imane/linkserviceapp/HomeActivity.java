package com.imane.linkserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnServices, btnMesServices, btnMessages, btnProfil ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnServices = (Button) findViewById(R.id.btnServices);
        btnMesServices = (Button) findViewById(R.id.btnMesServices);
        btnMessages = (Button) findViewById(R.id.btnMessages);
        btnProfil = (Button) findViewById(R.id.btnProfil);


        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ServicesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMesServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,MesServicesActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MessagesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProfilActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
