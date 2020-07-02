package com.imane.linkserviceapp.VolunteersList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServiceInfos.ServiceInfosActivityCreator;
import com.imane.linkserviceapp.ServicesList.ServicesListAdapter;

import java.io.Serializable;
import java.util.List;

public class VolunteersListActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerViewVolunteers;
    VolunteerListAdapter adapter;
    User userConnected;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<User> listVolunteers;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_volunteers);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");
        service = (Service) getIntent().getSerializableExtra("service");

        listVolunteers = service.getVolunteers();
        Log.i("Volunteers", listVolunteers.get(0).getName());

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewVolunteers = (RecyclerView) findViewById(R.id.listshow);
        recyclerViewVolunteers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewVolunteers.setLayoutManager(linearLayoutManager);
        adapter = new VolunteerListAdapter(listVolunteers, VolunteersListActivity.this, userConnected);
        recyclerViewVolunteers.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServiceInfosActivityCreator.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("Service", service);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
