package com.imane.linkserviceapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PopUpNewsletter extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Newsletter").setMessage("Voulez-vous recevoir des emails vous informant de nouveautés?").setPositiveButton("Oui",null).setNegativeButton("Non", null);

        return builder.create();
    }
}
