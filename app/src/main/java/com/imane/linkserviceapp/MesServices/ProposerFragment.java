package com.imane.linkserviceapp.MesServices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        v = inflater.inflate(R.layout.proposer_fragment,container,false);
        recyclerView = v.findViewById(R.id.recyclerView) ;
        MesServicesRV recyclerViewAdapter;
        if(!services.isEmpty()){
            recyclerViewAdapter = new MesServicesRV(getContext(), services);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HashMap<String, Service> ServicesData = new HashMap<>();
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        String ServicesList = "";
        int counter;

        try {
            jsonParamValues.put("where"," WHERE id_creator="+userConnected.getId());

            jsonParam.put("table", "service");
            jsonParam.put("values",jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            ServicesList = API.sendRequest(jsonParam.toString(), "readWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        if (!ServicesList.equals("") && !ServicesList.equals("null")) {
            if (ServicesList.startsWith("i", 2)) {
                ServicesData.put("0", gson.fromJson(ServicesList, Service.class));
            } else {
                ServicesData = API.decodeResponseMultipleAsService(ServicesList);
            }

            for (counter = 0; counter < ServicesData.size(); counter++){
                Service type = ServicesData.get(Integer.toString(counter));
                services.add(type);
            }

        }
    }
}
