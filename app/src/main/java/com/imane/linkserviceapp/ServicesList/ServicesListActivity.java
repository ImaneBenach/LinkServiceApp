package com.imane.linkserviceapp.ServicesList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.HomeActivity;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.TypesService.ServicesActivity;
import com.imane.linkserviceapp.TypesService.ServicesAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicesListActivity extends AppCompatActivity {

    RecyclerView listServices;
    List<Service> Services = new ArrayList<>();
    ServicesListAdapter adapter;
    SearchView searchView;

    private final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HashMap<String, Service> ServicesData = new HashMap<>();
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        String ServicesList = "";
        int counter;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        String idTypeService = getIntent().getStringExtra("typeService");
        try {
            jsonParamValues.put("where"," WHERE id_type="+idTypeService);

            jsonParam.put("table", "service");
            jsonParam.put("values",jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            ServicesList = API.sendRequest(jsonParam.toString(), "readWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        if (!ServicesList.equals("")) {
            if (ServicesList.startsWith("i", 2)) {
                ServicesData.put("0", gson.fromJson(ServicesList, Service.class));
            } else {
                ServicesData = API.decodeResponseMultipleAsService(ServicesList);
            }

            for (counter = 0; counter < ServicesData.size(); counter++){
                Service type = ServicesData.get(Integer.toString(counter));
                Services.add(type);
            }

        }

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listServices = (RecyclerView) findViewById(R.id.listshow);
        listServices.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listServices.setLayoutManager(linearLayoutManager);
        adapter = new ServicesListAdapter(Services, ServicesListActivity.this);
        listServices.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        searchView.findViewById(R.id.search_src_text);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Service> filtermodelist = filter(Services, newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        return true;
    }

    private List<Service> filter(List<Service> p1, String query) {
        query = query.toLowerCase();

        final List<Service> filteredModeList = new ArrayList<>();
        for (Service model : p1) {
            final String text = model.getName().toLowerCase();
            if (text.startsWith(query)) {
                filteredModeList.add(model);
            }

        }

        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServicesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
