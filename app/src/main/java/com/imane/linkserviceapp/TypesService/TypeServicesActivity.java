package com.imane.linkserviceapp.TypesService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.TypeAPI;
import com.imane.linkserviceapp.Classes.TypeService;

import java.util.ArrayList;
import java.util.List;

import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.serviceMenuActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TypeServicesActivity extends AppCompatActivity {

    RecyclerView listServices;
    List<TypeService> typeServices = new ArrayList<>();
    TypeServicesAdapter adapter;
    SearchView searchView;
    User userConnected;

    Retrofit retrofit = ConfigAPI.getRetrofitClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        TypeAPI typeAPI = retrofit.create(TypeAPI.class);
        Call callType = typeAPI.getTypesActives();

        callType.enqueue(
            new Callback<List<TypeService>>() {
                @Override
                public void onResponse(Call<List<TypeService>> call, Response<List<TypeService>> response) {
                    if(response.code()==200){
                        List<TypeService> types = response.body();
                        if(types != null && types.size() > 0){
                            typeServices = types;
                            adapter = new TypeServicesAdapter(typeServices, TypeServicesActivity.this, userConnected);
                            listServices.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            }
        );

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listServices = (RecyclerView) findViewById(R.id.listshow);
        listServices.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listServices.setLayoutManager(linearLayoutManager);
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
                final List<TypeService> filtermodelist = filter(typeServices, newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        return true;
    }

    private List<TypeService> filter(List<TypeService> p1, String query) {
        query = query.toLowerCase();

        final List<TypeService> filteredModeList = new ArrayList<>();
        for (TypeService model : p1) {
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
                Intent intent = new Intent(this, serviceMenuActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
