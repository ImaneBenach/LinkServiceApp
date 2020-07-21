package com.imane.linkserviceapp.VolunteerInfos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.imane.linkserviceapp.API.BadgeAPI;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.Badge;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.serviceMenuActivity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsExecutorActivity extends AppCompatActivity {

    TextView tv_name;
    TextView tv_surname;
    ImageView iv_photo_profil;
    ImageView iv_bagde;
    TextView tv_badge_name;

    User userConnected;
    Service service;
    int VolunteerID;

    Retrofit retrofit = ConfigAPI.getRetrofitClient();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_executor);

        // IMAGES static pour le moment car pas possible de les récupérer sur un serveur
        iv_photo_profil = findViewById(R.id.executor_fdp);
        iv_bagde = findViewById(R.id.executor_badge_img);

        VolunteerID = (int) getIntent().getSerializableExtra("volunteerID");
        userConnected = (User) getIntent().getSerializableExtra("userConnected");
        service = (Service) getIntent().getSerializableExtra("service");

        getVolunteer(VolunteerID);
        iv_photo_profil.setImageResource(R.drawable.photo_profil);
        iv_bagde.setImageResource(R.drawable.photo_badge);
        getBadge(VolunteerID, service.getId_type());


    }


    public void display(Badge BestBadge){
        tv_badge_name = findViewById(R.id.executor_badge);
        tv_badge_name.setText(BestBadge.getName());
    }

    public void displayNoBadge(){
        tv_badge_name = findViewById(R.id.executor_badge);
        tv_badge_name.setText("Pas de badge pour ce type de service ");
    }

    public void getBadge(int VolunteerID, int ServiceTypeID){
        BadgeAPI badgeAPI = retrofit.create(BadgeAPI.class);
        Call callBadge = badgeAPI.getBestBadge(VolunteerID, ServiceTypeID);

        callBadge.enqueue(
                new Callback<List<Badge>>() {
                    @Override
                    public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {

                        if (response.code() == 200){
                            List<Badge> badges = (List<Badge>) response.body();

                            if (badges != null && badges.size() > 0){
                                display(badges.get(0));
                            }else {
                                displayNoBadge();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Badge>> call, Throwable t) {
                        Log.d("Failure, code ", t.toString());
                        displayNoBadge();
                    }
                }
        );
    }

    public void displayVolunteerInfos(User volunteer){
        tv_name = findViewById(R.id.executor_name);
        tv_surname = findViewById(R.id.executor_surname);

        tv_name.setText(volunteer.getName());
        tv_surname.setText(volunteer.getSurname());
    }

    public void getVolunteer(int VolunteerID){
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<User>> callUser = userAPI.getOne(VolunteerID);

        callUser.enqueue(
                new Callback<List<User>>(){

                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        if (response.code() == 200){
                            List<User> volunteer = (List<User>) response.body();

                            if (volunteer != null){

                                displayVolunteerInfos(volunteer.get(0));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d("Failure, code", t.toString());
                    }
                }
        );

    }
}
