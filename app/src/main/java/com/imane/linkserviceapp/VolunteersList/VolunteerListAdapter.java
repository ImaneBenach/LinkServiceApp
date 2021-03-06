package com.imane.linkserviceapp.VolunteersList;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;
import com.imane.linkserviceapp.ServiceInfos.ServiceInfosActivityCreator;
import com.imane.linkserviceapp.VolunteerInfos.DetailsExecutorActivity;

import java.util.ArrayList;
import java.util.List;

public class VolunteerListAdapter extends RecyclerView.Adapter<VolunteerListAdapter.Holderview> {

    private List<User> Users;
    private Context context;
    private User userConnected;
    private Service service;

    public VolunteerListAdapter(List<User> Users, Service service, Context context, User user) {
        this.userConnected = user;
        this.service = service;
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
    public void onBindViewHolder(@NonNull final VolunteerListAdapter.Holderview holder, final int position) {
        holder.v_name.setText(Users.get(position).getName());
        //holder.v_image.setImageResource(Users.get(position).getImage());
        //holder.v_desc.setText(Users.get(position).getDescription());

        holder.btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsExecutorActivity.class);
                intent.putExtra("volunteerID", Users.get(position).getId());
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("service", service);
                context.startActivity(intent);
            }
        });

        holder.btnValidateVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_confirmation, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(holder.itemView1, Gravity.CENTER, 0, 0);

                Button btnValidate = popupView.findViewById(R.id.btn_validVolunteer);
                Button btnCancel = popupView.findViewById(R.id.btn_cancelVolunteer);

                btnValidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.validateVolunteer(Users.get(position).getId(), Users);
                        popupWindow.dismiss();
                        Intent intent = new Intent(context, ServiceInfosActivityCreator.class);
                        intent.putExtra("userConnected", userConnected);
                        intent.putExtra("Service", service);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return Users.size();
    }

    class Holderview extends RecyclerView.ViewHolder {
        //ImageView v_image;
        TextView v_name;
        //TextView v_desc;
        Button btnValidateVolunteer;
        Button btnUserDetails;
        View itemView1;

        Holderview(View itemview){
            super(itemview);
            //v_image = (ImageView) itemview.findViewById(R.id.service_image);
            v_name = (TextView) itemview.findViewById(R.id.volunteer_name);
            //v_desc = (TextView) itemview.findViewById(R.id.desc);
            btnValidateVolunteer = (Button) itemview.findViewById(R.id.btnValidateVolunteer);
            btnUserDetails = (Button) itemview.findViewById(R.id.btnUserDetails);
            itemView1 = itemview;
        }
    }
}
