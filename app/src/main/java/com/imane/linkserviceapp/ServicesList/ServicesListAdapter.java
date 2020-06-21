package com.imane.linkserviceapp.ServicesList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServiceInfos.ServiceInfosActivity;
import com.imane.linkserviceapp.TypesService.ServicesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.Holderview>{

    private List<Service> Services;
    private Context context;
    private User userConnected;

    public ServicesListAdapter(List<Service> Services, Context context, User user) {
        this.userConnected = user;
        this.Services = Services;
        this.context = context;
    }

    @NonNull
    @Override
    public ServicesListAdapter.Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customservices,parent, false);
        return new ServicesListAdapter.Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesListAdapter.Holderview holder, final int position) {
        holder.v_name.setText(Services.get(position).getName());
        holder.v_image.setImageResource(Services.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ServiceInfosActivity.class);
                intent.putExtra("Service",Services.get(position));
                intent.putExtra("userConnected", userConnected);
                context.startActivity(intent);

                Toast.makeText(context,"click on " + Services.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return Services.size();
    }

    public void setfilter(List<Service> listservices){
        Services = new ArrayList<>();
        Services.addAll(listservices);
        notifyDataSetChanged();
    }

    class Holderview extends RecyclerView.ViewHolder {
        ImageView v_image ;
        TextView v_name ;

        Holderview(View itemview){
            super(itemview);
            v_image = (ImageView) itemview.findViewById(R.id.service_image);
            v_name = (TextView) itemview.findViewById(R.id.service_title);
        }
    }
}
