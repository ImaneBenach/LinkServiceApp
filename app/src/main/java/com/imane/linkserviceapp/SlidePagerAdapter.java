package com.imane.linkserviceapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class SlidePagerAdapter extends PagerAdapter {

    Context context;
    List<ContentSlide> contenuList;

    public SlidePagerAdapter(Context context, List<ContentSlide> contenuList) {
        this.context = context;
        this.contenuList = contenuList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_content, null) ;

        TextView title = layoutScreen.findViewById(R.id.tv_title);
        TextView description = layoutScreen.findViewById(R.id.tv_description);
        ImageView image = layoutScreen.findViewById(R.id.imageProfil);

        title.setText(contenuList.get(position).getTitle());
        description.setText(contenuList.get(position).getDescription());
        image.setImageResource(contenuList.get(position).getImage());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount(){
        return contenuList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }
}
