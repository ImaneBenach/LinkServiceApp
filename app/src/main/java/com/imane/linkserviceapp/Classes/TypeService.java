package com.imane.linkserviceapp.Classes;


import com.google.gson.Gson;
import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.TypeAPI;
import com.imane.linkserviceapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TypeService implements Serializable {
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

    public int getImage() {
        switch (picture){
            case "cuisine.png":
                return R.drawable.cuisine;
            case "menage.png":
                return R.drawable.menage;
            case "babysitting.jpg":
                return R.drawable.babysitting;
            case "travaux.png":
                return R.drawable.travaux;
            case "course.png":
                return R.drawable.course;
            case "aide.png":
                return R.drawable.aide;
            default:
                return R.drawable.services_logo;
        }
    }

    public int getActive() { return active; }

    public static String getNameTypeServiceById(int idType){
        Retrofit retrofit = ConfigAPI.getRetrofitClient();
        TypeAPI typeAPI = retrofit.create(TypeAPI.class);
        Call callType = typeAPI.getTypesActives(idType);

        callType.enqueue(
            new Callback<List<TypeService>>() {
                @Override
                public void onResponse(Call<List<TypeService>> call, Response<List<TypeService>> response) {
                    if(response.code()==200){
                        List<TypeService> users = response.body();
                        if(users != null && users.size() > 0){
                            TypeService type = users.get(0);

                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            }
        );



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
