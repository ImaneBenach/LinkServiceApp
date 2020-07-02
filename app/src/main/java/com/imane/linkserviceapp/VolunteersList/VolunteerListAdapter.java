package com.imane.linkserviceapp.VolunteersList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServiceInfos.ServiceInfosActivity;
import com.imane.linkserviceapp.ServicesList.ServicesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class VolunteerListAdapter extends RecyclerView.Adapter<VolunteerListAdapter.Holderview> {

    private List<User> Users;
    private Context context;
    private User userConnected;

    public VolunteerListAdapter(List<User> Users, Context context, User user) {
        this.userConnected = user;
        this.Users = Users;
        this.context = context;
    }

    @NonNull
    @Override
    public VolunteerListAdapter.Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer,parent, false);
        return new VolunteerListAdapter.Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerListAdapter.Holderview holder, final int position) {
        holder.v_name.setText(Users.get(position).getName());
        //holder.v_image.setImageResource(Users.get(position).getImage());
        //holder.v_desc.setText(Users.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return Users.size();
    }

    public void setfilter(List<User> listservices){
        Users = new ArrayList<>();
        Users.addAll(listservices);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder {
        //ImageView v_image;
        TextView v_name;
        //TextView v_desc;

        Holderview(View itemview){
            super(itemview);
            //v_image = (ImageView) itemview.findViewById(R.id.service_image);
            v_name = (TextView) itemview.findViewById(R.id.volunteer_name);
            //v_desc = (TextView) itemview.findViewById(R.id.desc);
        }
    }
}
