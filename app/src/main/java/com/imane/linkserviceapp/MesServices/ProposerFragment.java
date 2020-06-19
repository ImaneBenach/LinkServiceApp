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

import com.imane.linkserviceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProposerFragment extends Fragment {


    View v ;
    private RecyclerView recyclerView ;
    private List<MyServices> services ;

    public ProposerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.proposer_fragment,container,false);
        recyclerView = v.findViewById(R.id.recyclerView) ;
        MesServicesRV recyclerViewAdapter = new MesServicesRV(getContext(), services) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        services = new ArrayList<>();
        services.add(new MyServices("Test", "ceci est un test recycler", R.drawable.services_logo));

        services.add(new MyServices("Test", "ceci est un test recycler", R.drawable.services_logo));

        services.add(new MyServices("Test", "ceci est un test recycler", R.drawable.services_logo));

        services.add(new MyServices("Test", "ceci est un test recycler", R.drawable.services_logo));
    }
}
