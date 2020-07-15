package com.imane.linkserviceapp.Classes;

import android.sax.ElementListener;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private User executorUser;


    public Service(int idService, String n, String desc, String da, String dl, int cost, int profit, String adress, String city, int pc, String access, int type, int creator){
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
            }
        } else {
            executorUser = null;
        }
    }

    public void delete(){
        JSONObject jsonParamValues = new JSONObject();

        deleteApply();

        try {
            jsonParamValues.put("id", id);
            jsonParamValues.put("Statut",0);

            sendToAPI(jsonParamValues, "service", "update");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void apply(int userId) {
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("id_user", userId);
            jsonParamValues.put("execute", 1);

            sendToAPI(jsonParamValues, "apply", "create");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<User> getVolunteers(){
        HashMap<String, User> hashMapVolunteers = new HashMap<>();
        List<User> ListVolunteers = new ArrayList<>();
        String volunteers = "";
        int counter;

        JSONObject jsonParamValues = new JSONObject();
        final Gson gson = new Gson();

        try {
            jsonParamValues.put("where", " inner JOIN USER WHERE id_user=id AND id_service="+id);

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
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("id_user", userId);
            jsonParamValues.put("execute", 2);

            sendToAPI(jsonParamValues, "apply", "update");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        unvalidateOtherVolunteers(userId, listVolunteers);
    }

    private void unvalidateOtherVolunteers(int userId, List<User> listVolunteers){
        int counter;

        for (counter = 0; counter < listVolunteers.size(); counter++){
            JSONObject jsonParamValues = new JSONObject();
            if(listVolunteers.get(counter).getId() != userId){
                try {
                    jsonParamValues.put("id_service", id);
                    jsonParamValues.put("id_user", listVolunteers.get(counter).getId());
                    jsonParamValues.put("execute", 0);

                    sendToAPI(jsonParamValues, "apply", "update");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteApply(){
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("execute",0);

            sendToAPI(jsonParamValues, "apply", "deleteWithFilter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setEvaluation(int note, String comment){
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("id_user", executorUser.getId());
            jsonParamValues.put("note",Integer.toString(note));
            jsonParamValues.put("commentaire", comment);

            sendToAPI(jsonParamValues, "apply", "update");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        givePointToExecutor();
    }

    private void givePointToExecutor(){
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id", executorUser.getId());
            jsonParamValues.put("points", executorUser.getPoints()+profit);

            sendToAPI(jsonParamValues,"user", "update");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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