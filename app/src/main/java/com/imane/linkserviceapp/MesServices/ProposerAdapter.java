package com.imane.linkserviceapp.MesServices;

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
import com.imane.linkserviceapp.ServiceInfos.ServiceInfosActivityCreator;

import java.util.List;

public class ProposerAdapter extends RecyclerView.Adapter<ProposerAdapter.MyViewHolder> {

    Context context ;
    List<Service> data ;
    User userConnected;

    public ProposerAdapter(Context context, List<Service> data, User user) {
        this.context = context;
        this.data = data;
        this.userConnected = user;
    }

    @NonNull
    @Override
    public ProposerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v ;
        v = LayoutInflater.from(context).inflate(R.layout.customservices,parent,false);
        ProposerAdapter.MyViewHolder holder = new ProposerAdapter.MyViewHolder(v) ;

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProposerAdapter.MyViewHolder holder, final int position) {

        holder.name.setText(data.get(position).getName());
        holder.desc.setText(data.get(position).getDescription());
        holder.imageView.setImageResource(data.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ServiceInfosActivityCreator.class);
                intent.putExtra("Service",data.get(position));
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("from","MesServicesActivity");
                context.startActivity(intent);

            }
        });

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

            name = itemView.findViewById(R.id.service_title) ;
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.service_image);
        }
    }
}
