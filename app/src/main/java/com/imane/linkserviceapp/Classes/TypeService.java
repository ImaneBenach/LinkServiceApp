package com.imane.linkserviceapp.Classes;


import android.util.Log;

import com.google.gson.Gson;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class TypeService {
    private final int id;
    private String name;
    private String description;
    private String picture;
    private int active;

    public TypeService(int idType, String n, String d, String i, int a) {
        id = idType;
        name = n;
        description = d;
        picture = i;
        active = a;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() { return R.drawable.services_logo; }

    public int getActive() { return active; }

    public static String getNameById(int idType){
        Gson gson = new Gson();
        JSONObject jsonParam = new JSONObject();
        JSONObject jsonParamValues = new JSONObject();
        TypeService typeService;
        String where = " WHERE id="+idType;

        try {
            jsonParamValues.put("where", where);
            jsonParam.put("table", "type_service");
            jsonParam.put("values",jsonParamValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            typeService = gson.fromJson(API.sendRequest(jsonParam.toString(), "readWithFilter"),TypeService.class);
            return typeService.getName();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
