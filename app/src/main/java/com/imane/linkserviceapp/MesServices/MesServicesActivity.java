package com.imane.linkserviceapp.MesServices;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.HomeActivity;
import com.imane.linkserviceapp.R;

import java.io.Serializable;

public class MesServicesActivity extends AppCompatActivity implements Serializable {

    private TabLayout tabLayout ;
    private ViewPager viewPager ;
    private ViewPagerAdapter adapter ;
    private User userConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_services);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new EffectuerFragment(userConnected), "Effectué(s)");
        adapter.AddFragment(new ProposerFragment(userConnected), "Proposé(s)");




        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_done_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_list_24dp);

        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setElevation(0);


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
