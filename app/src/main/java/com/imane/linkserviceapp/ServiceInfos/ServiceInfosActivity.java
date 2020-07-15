package com.imane.linkserviceapp.ServiceInfos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.HomeActivity;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;
import com.imane.linkserviceapp.ServicesList.ServicesListAdapter;
import com.imane.linkserviceapp.TypesService.ServicesActivity;
import com.imane.linkserviceapp.serviceMenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ServiceInfosActivity extends AppCompatActivity implements Serializable {
    Service service;
    User userConnected;
    private final Gson gson = new Gson();
    String originActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_infos);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");
        originActivity = getIntent().getStringExtra("from");

        TextView textView = (TextView) findViewById(R.id.PointsService);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.coin, 0, 0, 0);

        service = (Service) getIntent().getSerializableExtra("Service");
        setServiceInfo();

        final Button btnPostulate = findViewById(R.id.buttonPostuler);

        btnPostulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.apply(userConnected.getId());
                Log.i("click on postulate", service.getName());
            }
        });

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signalement, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.signal);

        myActionMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                displaySignalementPopup(findViewById(R.id.nameService));
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(originActivity.equals("ServiceList")){
                    Intent intent = new Intent(this, ServicesListActivity.class);
                    intent.putExtra("typeService",Integer.toString(service.getId_type()));
                    intent.putExtra("userConnected", userConnected);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (originActivity.equals("MesServicesActivity")){
                    Intent intent = new Intent(this, MesServicesActivity.class);
                    intent.putExtra("userConnected", userConnected);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
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

            checkIfCreatorIsConnectedUser(userCreator);

            return userCreator.getName() + " " + userCreator.getSurname();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displaySignalementPopup(View view){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_confirm_signal, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button btnValidate = popupView.findViewById(R.id.btn_validSignalement);
        Button btnCancel = popupView.findViewById(R.id.btn_cancelSignalement);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText description = popupView.findViewById(R.id.signalDescription);
                service.signalement(userConnected.getId(), description.getText().toString());
                popupWindow.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void checkIfCreatorIsConnectedUser(User userCreator){

        if(userCreator.getId() == userConnected.getId()){
            final Button btnPostulate = findViewById(R.id.buttonPostuler);
            btnPostulate.setVisibility(View.INVISIBLE);
        }

    }

}
