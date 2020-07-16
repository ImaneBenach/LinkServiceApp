package com.imane.linkserviceapp.VolunteerInfos;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DefailtsExecutorActivity extends AppCompatActivity {

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

//        tv_name = findViewById(R.id.executor_name);
//        tv_surname = findViewById(R.id.executor_surname);
        iv_photo_profil = findViewById(R.id.executor_fdp);
        iv_bagde = findViewById(R.id.executor_badge_img);
        tv_badge_name = findViewById(R.id.executor_badge);

        VolunteerID = (int) getIntent().getSerializableExtra("volunteerID");
        userConnected = (User) getIntent().getSerializableExtra("userConnected");
        service = (Service) getIntent().getSerializableExtra("service");

        getVolunteer(VolunteerID);
        iv_photo_profil.setImageResource(R.drawable.photo_profil);
        iv_bagde.setImageResource(R.drawable.photo_badge);
    }

    public void getBadge(int VolunteerID, int ServiceTypeID){

    }

    public void displayVolunteerInfos(User volunteer){
        tv_name = findViewById(R.id.executor_name);
        tv_surname = findViewById(R.id.executor_surname);

        tv_name.setText(volunteer.getName());
        tv_surname.setText(volunteer.getSurname());
    }

    public void getVolunteer(int VolunteerID){
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call callUser = userAPI.getOne(VolunteerID);

        callUser.enqueue(
                new Callback<List<User>>(){

                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        Log.d("Response getvolunteer", String.valueOf(response.code()));
                        if (response.code() == 200){
                            List<User> volunteer = (List<User>) response.body();
                            Log.d("Volunter", volunteer.get(0).getName());

                            if (volunteer != null){
                                Log.d("Volunteer ID : ", String.valueOf(volunteer.get(0).getId()));
                                displayVolunteerInfos(volunteer.get(0));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d("Failure, code : ", t.toString());
                    }
                }
        );

    }
}
