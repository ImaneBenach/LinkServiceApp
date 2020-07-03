package com.imane.linkserviceapp.ServiceInfos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.MessagesActivity;
import com.imane.linkserviceapp.R;

import java.io.Serializable;

public class ServiceEvaluationActivity extends AppCompatActivity implements Serializable {

    Service service;
    User userConnected;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_evaluation);

        userConnected = (User) getIntent().getSerializableExtra("userConnected");

        service = (Service) getIntent().getSerializableExtra("Service");

        Button btnEvaluationValidation = findViewById(R.id.btnEvaluationValidation);

        btnEvaluationValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeekBar EvaluationNote = findViewById(R.id.EvaluationNote);
                EditText EvaluationComment = findViewById(R.id.EvaluationComment);

                String comment = EvaluationComment.getText().toString();
                int note = EvaluationNote.getProgress();

                if(checkEvaluation(comment, note)){
                    displayConfirmationPopup(view, comment, note);
                }
            }
        });


        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ServiceInfosActivityCreator.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("service", service);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkEvaluation(String comment, int note){
        if(!comment.equals("")){
            return true;
        }
        EditText EvaluationComment = findViewById(R.id.EvaluationComment);
        EvaluationComment.setBackgroundResource(R.drawable.field_border_error);
        return false;
    }

    private void displayConfirmationPopup(View view, final String comment, final int note){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_confirmation, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        Button btnValidate = popupView.findViewById(R.id.btn_validVolunteer);
        Button btnCancel = popupView.findViewById(R.id.btn_cancelVolunteer);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.setEvaluation(note, comment);
                Intent intent = new Intent(ServiceEvaluationActivity.this, MesServicesActivity.class);
                intent.putExtra("userConnected", userConnected);
                intent.putExtra("service", service);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
}
