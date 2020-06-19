package com.imane.linkserviceapp.ServiceInfos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class ServiceInfosActivityCreator extends AppCompatActivity implements Serializable{
        Service service;
        private final Gson gson = new Gson();

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_infos_creator);

        TextView textView = (TextView) findViewById(R.id.PointsService);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin, 0, 0, 0);

        service = (Service) getIntent().getSerializableExtra("Service");
        setServiceInfo();


        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServicesListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        private void setServiceInfo(){
        TextView nameService = (TextView) findViewById(R.id.nameService);
        TextView creatorService = (TextView) findViewById(R.id.creatorService);
        TextView profitService = (TextView) findViewById(R.id.PointsService);
        TextView descriptionService = (TextView) findViewById(R.id.descriptionService);
        TextView dateService = (TextView) findViewById(R.id.dateService);
        TextView typeService = (TextView) findViewById(R.id.typeService);

        nameService.setText(service.getName());
        creatorService.setText(getCreatorName());
        profitService.setText(Integer.toString(service.getProfit()));
        descriptionService.setText(service.getDescription());
        dateService.setText(service.getDate());
        typeService.setText(TypeService.getNameById(service.getId_type()));
    }

        private String getCreatorName(){
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        User userCreator;
        String where = " WHERE id="+service.getId_creator();

        try {
            jsonParamValues.put("where", where);
            jsonParam.put("table", "user");
            jsonParam.put("values",jsonParamValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            userCreator = gson.fromJson(API.sendRequest(jsonParam.toString(), "readWithFilter"),User.class);
            Log.i("UserCreator", userCreator.getName());
            return userCreator.getName() + " " + userCreator.getSurname();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    }
