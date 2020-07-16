package com.imane.linkserviceapp.Classes;

import com.imane.linkserviceapp.API.ConfigAPI;
import com.imane.linkserviceapp.API.UserAPI;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class User implements Serializable {
    private  int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String birthdate;
    private int points;
    private String adress;
    private String city;
    private String type;

    public User(int idUser){
        id = idUser;
    }

    public User(String emailUser, String pwd) {
        email = emailUser;
        password = pwd;
    }

    public User(){

    }

    public User(int idUser, String e, String n, String s, String b, int p, String a, String c, String t){
        id = idUser;
        email = e;
        name = n;
        surname = s;
        birthdate = b.substring(0,10);
        points = p;
        adress = a;
        city = c;
        type = t;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getEmail() { return email; }

    public String getBirthdate() { return birthdate; }

    public String getAdress() { return adress; }

    public String getCity() { return city; }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }


    public static HashMap register(String email, String password, String name, String surname, String birthdate,String type) throws NoSuchAlgorithmException, IOException, InterruptedException {
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password", API.passwordHash(password));
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("birthdate",birthdate);
        inputValues.put("points", "10");
        inputValues.put("category", "course");
        inputValues.put("type",type);
        return inputValues;
    }

    public boolean buyService(int cost){
        if(points - cost >= 0){
            points -= cost;
            Retrofit retrofit = ConfigAPI.getRetrofitClient();
            UserAPI userAPI = retrofit.create(UserAPI.class);
            Call callType = userAPI.updateUser(id, this);

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

            return true;
        }
        return false;
    }
}
