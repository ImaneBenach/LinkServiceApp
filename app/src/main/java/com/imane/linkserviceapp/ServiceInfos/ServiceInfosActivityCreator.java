package com.imane.linkserviceapp.ServiceInfos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.VolunteersList.VolunteersListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class ServiceInfosActivityCreator extends AppCompatActivity implements Serializable {
    Service service;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_infos_creator);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        TextView textView = (TextView) findViewById(R.id.PointsService);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin, 0, 0, 0);

        service = (Service) getIntent().getSerializableExtra("Service");
        setServiceInfo();

        final Button btnPostulate = findViewById(R.id.buttonVolunteers);

        btnPostulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceInfosActivityCreator.this, VolunteersListActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("service",service);
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
                Intent intent = new Intent(this, MesServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setServiceInfo() {
        TextView nameService = (TextView) findViewById(R.id.nameService);
        TextView profitService = (TextView) findViewById(R.id.PointsService);
        TextView descriptionService = (TextView) findViewById(R.id.descriptionService);
        TextView dateService = (TextView) findViewById(R.id.dateService);
        TextView typeService = (TextView) findViewById(R.id.typeService);

        nameService.setText(service.getName());
        profitService.setText(Integer.toString(service.getProfit()));
        descriptionService.setText(service.getDescription());
        dateService.setText(service.getDate());
        typeService.setText(TypeService.getNameById(service.getId_type()));
    }

    private String getCreatorName() {
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        User userCreator;
        String where = " WHERE id=" + service.getId_creator();

        try {
            jsonParamValues.put("where", where);
            jsonParam.put("table", "user");
            jsonParam.put("values", jsonParamValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            userCreator = gson.fromJson(API.sendRequest(jsonParam.toString(), "readWithFilter"), User.class);
            Log.i("UserCreator", userCreator.getName());
            return userCreator.getName() + " " + userCreator.getSurname();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}

