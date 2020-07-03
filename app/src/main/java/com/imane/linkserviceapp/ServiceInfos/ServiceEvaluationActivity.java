package com.imane.linkserviceapp.ServiceInfos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;

import java.io.Serializable;

public class ServiceEvaluationActivity extends AppCompatActivity implements Serializable {

    Service service;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_evaluation);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        service = (Service) getIntent().getSerializableExtra("Service");

        final Button buttonEndService = findViewById(R.id.EvaluationValidation);




        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServiceInfosActivityCreator.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("service", service);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
