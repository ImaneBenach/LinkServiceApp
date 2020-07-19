package com.imane.linkserviceapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopUpModifProfil extends AppCompatDialogFragment {

    private EditText editNom, editPrenom, editEmail, editMdp ;
    User userConnected;

    Retrofit retrofit = ConfigAPI.getRetrofitClient();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        editNom = view.findViewById(R.id.edit_nom) ;
        editPrenom = view.findViewById(R.id.edit_prenom) ;
        editMdp = view.findViewById(R.id.edit_mdp);


        builder.setView(view).setTitle("Modifier mon profil").setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
            .setPositiveButton("Appliquer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User user = new User();
                    //final String mdp = editMdp.getText().toString();
                    final String nom = editNom.getText().toString();
                    final String prenom = editPrenom.getText().toString();

                    Intent intent = getActivity().getIntent();
                    userConnected = (User) intent.getSerializableExtra("userConnected");

                    userConnected.setName(nom);
                    userConnected.setSurname(prenom);
                    userConnected.setBirthdate(userConnected.getBirthdate().substring(0,10));

                    UserAPI userAPI = retrofit.create(UserAPI.class);
                    Call callUser = userAPI.updateUser(userConnected.getId(), userConnected);

                    callUser.enqueue(
                            new Callback<List<User>>() {
                                @Override
                                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                    if(response.code()==200){

                                    }
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {

                                }
                            }
                    );
                }
            });
        return builder.create();

    }
}
