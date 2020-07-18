package com.imane.linkserviceapp.ServiceInfos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.imane.linkserviceapp.API.ApplyAPI;
import com.imane.linkserviceapp.API.BadgeAPI;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.WinAPI;
import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.Badge;
import com.imane.linkserviceapp.Classes.Note;
import com.imane.linkserviceapp.Classes.Service;
import com.imane.linkserviceapp.Classes.User;
import com.imane.linkserviceapp.Classes.Win;
import com.imane.linkserviceapp.MesServices.MesServicesActivity;
import com.imane.linkserviceapp.R;

import java.io.Serializable;
import java.util.List;

public class ServiceEvaluationActivity extends AppCompatActivity implements Serializable {

    Service service;
    User userConnected;

    private int ServiceID;
    private int UserID;
    private int UserPoints;

    private List<Badge> listUserBadges;
    private List<Badge> listServiceBadge;

    Retrofit retrofit = ConfigAPI.getRetrofitClient();
    BadgeAPI badgeAPI = retrofit.create(BadgeAPI.class);

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

                if(checkEvaluation(comment)){

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

    private boolean checkEvaluation(String comment){
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
        boolean focusable = true; // tap outside = dissmiss
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        Button btnValidate = popupView.findViewById(R.id.btn_validVolunteer);
        Button btnCancel = popupView.findViewById(R.id.btn_cancelVolunteer);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.normalizeBirthdate();
                service.setEvaluation(note, comment);

                ServiceID = service.getId_type();
                Log.d("Service ID", String.valueOf(ServiceID));
                UserID = service.getExecutorUser().getId();
                getPointsUserByService();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void saveEval(int note, String comment){
        Apply evalApply = new Apply(service.getExecutorUser().getId(), service.getId(), 2, note, comment);

        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.updateApply(evalApply);

        callType.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200){
                            service.givePointToExecutor();

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );

    }

    public void getPointsUserByService(){
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call<List<Note>> callPoint = applyAPI.getNoteUserByService(this.UserID, this.ServiceID);

        callPoint.enqueue(
                new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                        Log.d("Reponse code ", String.valueOf(response.code()));
                        if (response.code() == 200){
                            List<Note> points = response.body();
                            Log.d( "Points ", String.valueOf(points.get(0).getNote()));
                            getBadgeUser(points.get(0).getNote());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {

                    }
                }
        );
    }

    public void getBadgeUser(int points){
        this.UserPoints = points;
        Log.d("getBadgeUser ", String.valueOf(points));
        Call<List<Badge>> callBadgeUser = badgeAPI.getBagdeByUserAndService(this.UserID, this.ServiceID);

        callBadgeUser.enqueue(
                new Callback<List<Badge>>() {
                    @Override
                    public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                        List<Badge> listUserBadges = response.body();
                        Log.d("Reponse code ", String.valueOf(response.code()));
                        if (response.code() == 200 && listUserBadges != null){
                            Log.d("Badge user ", String.valueOf(listUserBadges));
                            getAllBadgeToAttribute(listUserBadges);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Badge>> call, Throwable t) {

                    }
                }
        );
    }

    public void getAllBadgeToAttribute(List<Badge> list){
        this.listUserBadges = list;
        Log.d("getAllBadgeToAttribute ", String.valueOf(10000));
        Call<List<Badge>> callBadgeService = badgeAPI.getAllBadgeByService(this.ServiceID);

        callBadgeService.enqueue(
                new Callback<List<Badge>>() {
                    @Override
                    public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                        Log.d("Reponse code ", String.valueOf(response.code()));
                        List<Badge> listServiceBadge = response.body();
                        if (response.code() == 200 && listServiceBadge != null){
                            Log.d("Badge service ", String.valueOf(listServiceBadge));
                            compareBadge(listServiceBadge);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Badge>> call, Throwable t) {

                    }
                }
        );
    }

    public void compareBadge(List<Badge> list){
        Log.d("compareBadge ", String.valueOf(999));

        this.listServiceBadge = list;
        boolean had_badge = false;

        int i = 0;
        int j = 0;

        for (i = 0; i< this.listServiceBadge.size() ; i++){
            for (j = 0; j< this.listUserBadges.size() ; j++){
                if (this.listServiceBadge.get(i).equals(this.listUserBadges.get(j))){
                    had_badge = true;
                }else{
                    had_badge = false ;
                }
            }
            if (this.listServiceBadge.get(i).getPointsLimit() <= this.UserPoints && !had_badge){
                Log.d("egal", "false");
                giveBadge(this.listServiceBadge.get(i));
            }
        }

        Intent intent = new Intent(ServiceEvaluationActivity.this, MesServicesActivity.class);
        intent.putExtra("userConnected", userConnected);
        intent.putExtra("service", service);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void giveBadge(Badge badge){

        Win win = new Win(badge.getId(), this.UserID);

        WinAPI winAPI = retrofit.create(WinAPI.class);

        Call callWin = winAPI.create(win);

        callWin.enqueue(
                new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.d("Win insert code", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                }
        );
    }


}

