package com.imane.linkserviceapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;
import com.imane.linkserviceapp.Classes.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PopUpModifProfil extends AppCompatDialogFragment {

    private EditText editNom, editPrenom, editEmail, editMdp ;
    User userConnected;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        editNom = view.findViewById(R.id.edit_nom) ;
        editPrenom = view.findViewById(R.id.edit_prenom) ;
        editEmail = view.findViewById(R.id.edit_email);
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
                        final String email = editEmail.getText().toString();
                        final String mdp = editMdp.getText().toString();
                        final String nom = editNom.getText().toString();
                        final String prenom = editPrenom.getText().toString();

                        Intent intent = getActivity().getIntent();
                        userConnected = (User) intent.getSerializableExtra("userConnected");


                        try {
                            String table =  "user";

                            HashMap<String,String> update = user.updateUser(nom, prenom,mdp,userConnected.getId(), email);
                            HashMap<String, Object> userValue = new HashMap<>();

                            userValue.put("table", "user");
                            userValue.put("values", update);
                            Gson gson = new Gson();
                            String json = gson.toJson(userValue);
                            System.out.println(json);

                            API api = new API();
                            api.sendRequest(json, "update");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });





        return builder.create();

    }
}
