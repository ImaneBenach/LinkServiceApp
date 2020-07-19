package com.imane.linkserviceapp.Classes;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ApplyAPI;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.ServiceAPI;
import com.imane.linkserviceapp.API.UserAPI;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Service implements Serializable {
    private int id;
    private String name;
    private String description;
    private String date;
    private String deadline;
    private int cost;
    private int profit;
    private String adress;
    private String city;
    private int postcode;
    private String access;
    private int id_type;
    private int id_creator;
    private int Statut;
    private User executorUser;

    public Service(int idService, String n, String desc, String da, String dl, int cost, int profit, String adress, String city, int pc, String access, int type, int creator, int statut){
        id = idService;
        name = n;
        description = desc;
        date = da;
        deadline = dl;
        this.cost = cost;
        this.profit = profit;
        this.adress = adress;
        this.city = city;
        postcode = pc;
        this.access = access;
        id_type = type;
        id_creator = creator;
        Statut = statut;
    }

    public void normalizeBirthdate(){
        executorUser.setBirthdate(executorUser.getBirthdate().substring(0,10));
    }

    public int getStatut() {
        return Statut;
    }

    public void setStatut(int statut) {
        Statut = statut;
    }

    public void saveFinishService(){
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);

        Call<Void> callService = serviceAPI.updateStatut(this.getId(), this);
        callService.enqueue(
                new Callback<Void>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("Responde code update statut ", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                }
        );
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getId_creator() { return id_creator; }

    public int getId_type() { return id_type; }

    public String getDate() { return date.substring(0, 10); }

    public String getDescription() { return description; }

    public int getCost() { return cost; }

    public int getProfit() { return profit; }

    public String getAdress() { return adress; }

    public String getAccess() { return access; }

    public int getImage() { return R.drawable.services_logo; }

    public User getExecutorUser() { return executorUser; }

    public void setExecutorIfExist(){
        JSONObject jsonParamValues = new JSONObject();
        final Gson gson = new Gson();
        String executor = "";

        try {
            jsonParamValues.put("where"," INNER JOIN USER WHERE execute=2 AND id_user=id AND id_service="+id);

            executor = sendToAPI(jsonParamValues, "apply", "readWithFilter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (executor != null && !executor.equals("null")) {
            if (executor.startsWith("i", 2)) {
                executorUser = gson.fromJson(executor, User.class);
                executorUser.setBirthdate(executorUser.getBirthdate().substring(0, 10));
            }
        } else {
            executorUser = null;
        }
    }

    public void delete(){
        deleteApply();
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call callType = serviceAPI.updateService(this);

        callType.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                }
        );

    }

    public void apply(int userId) {
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        Apply apply = new Apply(userId, id, 1);
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.setApply(apply);

        callType.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                }
        );
    }

    public List<User> getVolunteers(){
        HashMap<String, User> hashMapVolunteers = new HashMap<>();
        List<User> ListVolunteers = new ArrayList<>();
        String volunteers = "";
        int counter;

        JSONObject jsonParamValues = new JSONObject();
        final Gson gson = new Gson();

        try {
            jsonParamValues.put("where", " inner JOIN user WHERE id_user=id AND id_service="+id);

            volunteers = sendToAPI(jsonParamValues, "apply", "readWithFilter");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (volunteers != null && !volunteers.equals("null")) {
            if (volunteers.startsWith("i", 2)) {
                hashMapVolunteers.put("0",gson.fromJson(volunteers, User.class));
            } else {
                hashMapVolunteers = API.decodeResponseMultipleAsUser(volunteers);
            }
            for (counter = 0; counter < hashMapVolunteers.size(); counter++){
                User type = hashMapVolunteers.get(Integer.toString(counter));
                ListVolunteers.add(type);
            }
        }
        return ListVolunteers;
    }

    public void validateVolunteer(int userId, List<User> listVolunteers){
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        Apply apply = new Apply(userId, id, 2);
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.updateApply(apply);

        callType.enqueue(
            new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200){
                        unvalidateOtherVolunteers(userId, listVolunteers);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            }
        );

    }

    private void unvalidateOtherVolunteers(int userId, List<User> listVolunteers){
        int counter;
        for (counter = 0; counter < listVolunteers.size(); counter++){
            int volunteerid = listVolunteers.get(counter).getId();
            if(volunteerid != userId){
                Retrofit retrofit = ConfigAPI.getRetrofitClient();
                Apply apply = new Apply(volunteerid, id, 0);
                ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
                Call callType = applyAPI.updateApply(apply);

                callType.enqueue(
                    new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                        }
                    }
                );
            }
        }
    }

    public void deleteApply(){
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        Apply apply = new Apply(executorUser.getId(), id, 0);
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.updateApply(apply);

        callType.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );
    }

    public void setEvaluation(int note, String comment){
        Apply apply = new Apply(executorUser.getId(), id, 2);
        apply.setNote(note);
        apply.setCommentaire(comment);
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        ApplyAPI applyAPI = retrofit.create(ApplyAPI.class);
        Call callType = applyAPI.updateApply(apply);

        callType.enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );

        givePointToExecutor();
    }

    public void givePointToExecutor(){
        int executorNewAmountPoints = executorUser.getPoints() + profit;
        executorUser.setPoints(executorNewAmountPoints);
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call callType = userAPI.updateUser(executorUser.getId(), executorUser);

        callType.enqueue(
            new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                }
            }
        );
    }

    public void signalement(int idUser, String description){
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_user_creator", Integer.toString(idUser));
            jsonParamValues.put("description", description);
            jsonParamValues.put("id_service",id);
            jsonParamValues.put("statut","0");

            sendToAPI(jsonParamValues,"ticket", "create");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String sendToAPI(JSONObject values, String table, String action){
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("table", table);
            jsonParam.put("values", values);
            return API.sendRequest(jsonParam.toString(), action);
        } catch (JSONException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}