package com.imane.linkserviceapp.ServiceInfos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ApplyAPI;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.ServiceAPI;
import com.imane.linkserviceapp.API.TypeAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.Messagerie.ChatFragment;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ServiceEffectedInforsActivity extends AppCompatActivity implements Serializable {
    Service service;
    User userConnected;
    private final Gson gson = new Gson();
    String originActivity;
    Retrofit retrofit = ConfigAPI.getRetrofitClient();

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
        final Button btnContact = findViewById(R.id.buttonContacter);

        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.existApply(service.getId(), userConnected.getId());
        callType.enqueue(
                new Callback<List<Apply>>() {
                    @Override
                    public void onResponse(Call<List<Apply>> call, Response<List<Apply>> response) {
                        List<Apply> applies = response.body();
                        if(applies == null || applies.size() == 0){
                            btnPostulate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    service.apply(userConnected.getId());
                                }
                            });
                            btnContact.setVisibility(View.INVISIBLE);
                        }else if (applies.get(0).getExecute() == 0){
                            btnPostulate.setVisibility(View.INVISIBLE);
                            btnContact.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    redirectionMessages();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );

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

    public void redirectionMessages(){
        Intent intent = new Intent(this, ChatFragment.class);
        intent.putExtra("userConnected", userConnected);
        intent.putExtra("idUserCreator", service.getId_creator());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MesServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
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

        setCreatorName();

        nameService.setText(service.getName());
        profitService.setText(Integer.toString(service.getProfit()));
        descriptionService.setText(service.getDescription());
        dateService.setText(service.getDate());
        displayNameTypeService(typeService, service.getId_type());
    }

    private void setCreatorName(){
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call callUser = userAPI.getOne(service.getId_creator());

        callUser.enqueue(
                new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.code()==200){
                            List<User> users = response.body();
                            if(users != null && users.size() > 0){
                                User userCreator = users.get(0);

                                TextView creatorService = (TextView) findViewById(R.id.creatorService);
                                creatorService.setText(userCreator.getName() + " " + userCreator.getSurname());
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

    private void displayNameTypeService(TextView typeService, int idType){
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        TypeAPI typeAPI = retrofit.create(TypeAPI.class);
        Call callType = typeAPI.getTypesActives(idType);

        callType.enqueue(
                new Callback<List<TypeService>>() {
                    @Override
                    public void onResponse(Call<List<TypeService>> call, Response<List<TypeService>> response) {
                        if(response.code()==200){
                            List<TypeService> users = response.body();
                            if(users != null && users.size() > 0){
                                TypeService type = users.get(0);
                                typeService.setText(type.getName());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );
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

    private void checkButton(User userCreator){
        if(userCreator.getId() == userConnected.getId()){
            final Button btnPostulate = findViewById(R.id.buttonPostuler);
            btnPostulate.setVisibility(View.INVISIBLE);
        }else{

            ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
            Call<List<Apply>> callApply = serviceAPI.getExecutor(service.getId());

            callApply.enqueue(
                    new Callback<List<Apply>>() {
                        @Override
                        public void onResponse(Call<List<Apply>> call, Response<List<Apply>> response) {

                            if (response.code() == 200){
                                List<Apply> executorList = (List<Apply>) response.body();
                                if (executorList != null && executorList.size() > 0){
                                        // change btn cancel participation
                                    final Button btnPostulate = findViewById(R.id.buttonPostuler);
                                    btnPostulate.setVisibility(View.INVISIBLE);
                                }else{
                                    final Button btnPostulate = findViewById(R.id.buttonPostuler);
                                    btnPostulate.setText("Annuler ma participation");
                                    btnPostulate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Apply updatedApplyUserConnected = new Apply(userConnected.getId(), service.getId(), 0);
                                            ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
                                            Call callApply = applyAPI.updateApply(updatedApplyUserConnected);

                                            callApply.enqueue(
                                                    new Callback() {
                                                        @Override
                                                        public void onResponse(Call call, Response response) {

                                                            if (response.code() == 200){
                                                                Intent intent = new Intent(ServiceEffectedInforsActivity.this, MesServicesActivity.class);
                                                                intent.putExtra("userConnected", userConnected);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call call, Throwable t) {
                                                            Log.d("Failure code ", String.valueOf(t));
                                                        }
                                                    }
                                            );

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Apply>> call, Throwable t) {
                            Log.d("Failure code ", String.valueOf(t));
                        }
                    }
            );

        }
    }

}
