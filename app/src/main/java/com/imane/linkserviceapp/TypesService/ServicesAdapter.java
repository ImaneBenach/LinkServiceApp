package com.imane.linkserviceapp.TypesService;

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

import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServicesList.ServicesListActivity;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.Holderview> {

    private List<TypeService> typeServices;
    private Context context ;
    private User userConnected;

    public ServicesAdapter(List<TypeService> typeServices, Context context, User user) {
        this.userConnected = user;
        this.typeServices = typeServices;
        this.context = context;
    }

    @NonNull
    @Override
    public Holderview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.customservices,parent, false);
        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Holderview holder, final int position) {
        holder.v_name.setText(typeServices.get(position).getName());
        holder.v_image.setImageResource(typeServices.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServicesListActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("typeService",Integer.toString(typeServices.get(position).getId()));
                context.startActivity(intent);

                Toast.makeText(context,"click on " + typeServices.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return typeServices.size();
    }

    public void setfilter(List<TypeService> listservices){
        typeServices = new ArrayList<>();
        typeServices.addAll(listservices);
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
