package com.imane.linkserviceapp.ServiceInfos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
        final Button buttonDeleteService = findViewById(R.id.buttonDeleteService);

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

        buttonDeleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


                Button btnValidate = popupView.findViewById(R.id.btn_validVolunteer);
                Button btnCancel = popupView.findViewById(R.id.btn_cancelVolunteer);

                btnValidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.delete();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
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

