package com.imane.linkserviceapp;

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

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    RecyclerView listServices ;
    List<Services> services = new ArrayList<>();
    ServicesAdapter adapter ;
    SearchView searchView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        services.add(new Services("Course", R.drawable.services_logo));
        services.add(new Services("Ménage", R.drawable.services_logo));
        services.add(new Services("Aide aux devoirs", R.drawable.services_logo));
        services.add(new Services("Aide à la personne", R.drawable.services_logo));
        services.add(new Services("Conseils", R.drawable.services_logo));
        services.add(new Services("Aidez-moi", R.drawable.services_logo));
        services.add(new Services("Cueillette de fruits", R.drawable.services_logo));
        services.add(new Services("Travaux", R.drawable.services_logo));
        services.add(new Services("Course à réaliser", R.drawable.services_logo));

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listServices = (RecyclerView) findViewById(R.id.listshow);
        listServices.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listServices.setLayoutManager(linearLayoutManager);
        adapter = new ServicesAdapter(services,ServicesActivity.this);
        listServices.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        searchView.findViewById(R.id.search_src_text) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false ;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Services> filtermodelist = filter(services, newText);
                adapter.setfilter(filtermodelist);
                return true ;
            }
        });
        return true;
    }

    private List<Services> filter (List<Services> p1, String query){
        query = query.toLowerCase();

        final List<Services> filteredModeList = new ArrayList<>();
        for(Services model:p1) {
            final String text=model.getTitle().toLowerCase();
            if(text.startsWith(query)) {
                filteredModeList.add(model);
            }

        }

        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if(view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view ;
                for(int i = 0 ; i < viewGroup.getChildCount();i++){
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
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
