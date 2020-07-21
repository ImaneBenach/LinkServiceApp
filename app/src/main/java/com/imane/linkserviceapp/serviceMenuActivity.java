package com.imane.linkserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.imane.linkserviceapp.API.TypeAPI;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.TypesService.TypeServicesActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.imane.linkserviceapp.API.ConfigAPI.retrofit;

public class serviceMenuActivity extends AppCompatActivity implements Serializable {

    Button btnParticipe, btnPropose;
    User userConnected;

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
                Intent intent = new Intent(serviceMenuActivity.this, TypeServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                startActivity(intent);
                finish();
            }
        });

        btnPropose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TypeAPI typeAPI = retrofit.create(TypeAPI.class);
                Call callType = typeAPI.getTypesActives();



                callType.enqueue(
                        new Callback<List<TypeService>>() {
                            @Override
                            public void onResponse(Call<List<TypeService>> call, Response<List<TypeService>> response) {
                                if(response.code()==200){
                                    List<TypeService> types = response.body();
                                    if(types != null && types.size() > 0){
                                        List<TypeService> typeServices;
                                        typeServices = types;

                                        TypeService undefinedType = new TypeService(-1, "--TypeService--", "", "", 1);
                                        typeServices.add(0, undefinedType);

                                        ArrayList<TypeService> typesServicesList = ((ArrayList<TypeService>) typeServices);

                                        Intent intent = new Intent(serviceMenuActivity.this, CreateServiceActivity.class);
                                        intent.putExtra("userConnected", userConnected);
                                        intent.putExtra("typeServices", typesServicesList);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        }
                );

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
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
