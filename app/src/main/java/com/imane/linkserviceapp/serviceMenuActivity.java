package com.imane.linkserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;
import com.imane.linkserviceapp.TypesService.ServicesActivity;

import java.io.Serializable;

public class serviceMenuActivity extends AppCompatActivity implements Serializable {

    Button btnParticipe, btnPropose;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_menu);
        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        btnParticipe = (Button) findViewById(R.id.btnParticipe);
        btnPropose = (Button) findViewById(R.id.btnPropose);

        btnParticipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(serviceMenuActivity.this, ServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();
            }
        });

        btnPropose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(serviceMenuActivity.this, CreateServiceActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();

            }
        });


        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServicesListActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
