package com.imane.linkserviceapp.MesServices;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.ServiceAPI;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProposerFragment extends Fragment {

    View v ;
    private RecyclerView recyclerView ;
    private List<Service> services = new ArrayList<>();
    private User userConnected;
    private final Gson gson = new Gson();

    public ProposerFragment(User user) {
        userConnected = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call callType = serviceAPI.getServicesByCreator(userConnected.getId());

        callType.enqueue(
                new Callback<List<Service>>() {
                    @Override
                    public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                        if(response.code() == 200){
                            List<Service> listService = response.body();
                            if(listService != null){
                                services = listService;

                                recyclerView = v.findViewById(R.id.recyclerView) ;
                                ProposerAdapter recyclerViewAdapter;
                                if(!services.isEmpty()){
                                    recyclerViewAdapter = new ProposerAdapter(getContext(), services, userConnected);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    recyclerView.setAdapter(recyclerViewAdapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                }
        );


        v = inflater.inflate(R.layout.proposer_fragment,container,false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
