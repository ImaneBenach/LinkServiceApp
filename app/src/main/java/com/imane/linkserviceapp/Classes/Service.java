package com.imane.linkserviceapp.Classes;

import android.sax.ElementListener;
import android.util.Log;

import com.google.gson.Gson;
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
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        final Gson gson = new Gson();
        String executor = "";

        try {
            jsonParamValues.put("where"," INNER JOIN USER WHERE execute=2 AND id_user=id AND id_service="+id);

            jsonParam.put("table", "apply");
            jsonParam.put("values", jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            executor = API.sendRequest(jsonParam.toString(), "readWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!executor.equals("")) {
            if (executor.startsWith("i", 2)) {
                executorUser = gson.fromJson(executor, User.class);
            }
        } else {
            executorUser = null;
        }

    }

    public void delete(){
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        deleteApply();

        try {
            jsonParamValues.put("id", id);

            jsonParam.put("table", "service");
            jsonParam.put("values", jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            API.sendRequest(jsonParam.toString(), "deleteOne");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void apply(int userId) {
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("id_user", userId);

            jsonParam.put("table", "apply");
            jsonParam.put("values", jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            API.sendRequest(jsonParam.toString(), "create");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<User> getVolunteers(){
        HashMap<String, User> hashMapVolunteers = new HashMap<>();
        List<User> ListVolunteers = new ArrayList<>();
        String volunteers = "";
        int counter;

        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        final Gson gson = new Gson();

        try {
            jsonParamValues.put("where", " inner JOIN USER WHERE id_user=id AND id_service="+id);

            jsonParam.put("table", "apply");
            jsonParam.put("values", jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            volunteers = API.sendRequest(jsonParam.toString(), "readWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!volunteers.equals("")) {
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
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("id_service", id);
            jsonParamValues.put("id_user", userId);
            jsonParamValues.put("execute", 2);

            jsonParam.put("table", "apply");
            jsonParam.put("values", jsonParamValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            API.sendRequest(jsonParam.toString(), "update");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        unvalidateOtherVolunteers(userId, listVolunteers);
    }

    private void unvalidateOtherVolunteers(int userId, List<User> listVolunteers){
        int counter;

        for (counter = 0; counter < listVolunteers.size(); counter++){
            JSONObject jsonParam = new JSONObject();
            JSONObject jsonParamValues = new JSONObject();
            if(listVolunteers.get(counter).getId() != userId){
                try {
                    jsonParamValues.put("id_service", id);
                    jsonParamValues.put("id_user", listVolunteers.get(counter).getId());
                    jsonParamValues.put("execute", 1);

                    jsonParam.put("table", "apply");
                    jsonParam.put("values", jsonParamValues);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    API.sendRequest(jsonParam.toString(), "update");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void deleteApply(){
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();

        try {
            jsonParamValues.put("where", " WHERE id_service="+id);

            jsonParam.put("table", "apply");
            jsonParam.put("values", jsonParamValues);
            Log.i("json",jsonParam.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            API.sendRequest(jsonParam.toString(), "deleteWithFilter");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

