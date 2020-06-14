package com.imane.linkserviceapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {

    private ViewPager viewPagerSlide;
    private SlidePagerAdapter slidePagerAdapter;
    private TabLayout tabIndicator;
    private LinearLayout linearLayout_Suiv, linearLayoutStart;
    private Button btnSuiv, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slide);

        btnSuiv = findViewById(R.id.btn_suiv);
        btnStart = findViewById(R.id.btn_start);
        linearLayout_Suiv = findViewById(R.id.linearLayout_suiv);
        linearLayoutStart = findViewById(R.id.linearLayoutStart);
        tabIndicator = findViewById(R.id.tabLayout_indicator);
        viewPagerSlide = findViewById(R.id.view_pager_slide);

        final List<ContentSlide> liste = new ArrayList<>();
        liste.add(new ContentSlide("Solidarité", "Propose et effectue des services en fonction de tes compétences et de tes capacités !", R.drawable.image_slide_solidarite));
        liste.add(new ContentSlide("Localisation", "Effectue n’importe quel service à n’importe quel moment !\n"+"Choisi les services selon ton emplacement.", R.drawable.image_slide_localisation));
        liste.add(new ContentSlide("Support", "Vous êtes accompagné !\n"+"En cas de problème, un support est mis en place afin de résoudre n’importe quel problème.", R.drawable.image_slide_support));

        slidePagerAdapter = new SlidePagerAdapter(this, liste);
        viewPagerSlide.setAdapter(slidePagerAdapter);
        tabIndicator.setupWithViewPager(viewPagerSlide);

        btnSuiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPagerSlide.setCurrentItem(viewPagerSlide.getCurrentItem()+1, true);
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==liste.size()-1){
                    lastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }

    private void lastScreen(){
        linearLayout_Suiv.setVisibility(View.INVISIBLE);
        linearLayoutStart.setVisibility(View.VISIBLE);
    }
}