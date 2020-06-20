package com.imane.linkserviceapp.MesServices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.R;

import java.util.List;

public class MesServicesRV extends RecyclerView.Adapter<MesServicesRV.MyViewHolder>{

    Context context ;
    List<Service> data ;

    public MesServicesRV(Context context, List<Service> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v ;
        v = LayoutInflater.from(context).inflate(R.layout.messervices_item,parent,false);
        MyViewHolder holder = new MyViewHolder(v) ;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(data.get(position).getName());
        holder.desc.setText(data.get(position).getDescription());
        holder.imageView.setImageResource(data.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name, desc;
        private ImageView imageView ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nom) ;
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.imageItem);
        }
    }
}
