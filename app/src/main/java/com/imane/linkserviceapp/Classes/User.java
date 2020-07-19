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
    private String postcode;
    private String type;
    private String password;

    public User(int idUser){
        id = idUser;
    }

    public User(String emailUser, String pwd) {
        email = emailUser;
        password = pwd;
    }

    public User(){

    }

    public User(String email, String password, String name, String surname, String birthdate, String type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.type = type;
    }

    public User(String email, String password, String name, String surname, String birthdate, int points, String adress, String city, String postcode, String type) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate.substring(0,10);;
        this.points = points;
        this.adress = adress;
        this.city = city;
        this.postcode = postcode;
        this.type = type;
    }

    public User(int idUser, String e, String n, String s, String b, int p, String a, String c, String t, String pass){
        id = idUser;
        email = e;
        name = n;
        surname = s;
        birthdate = b.substring(0,10);
        points = p;
        adress = a;
        city = c;
        type = t;
        password = pass;
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

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

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
            birthdate = birthdate.substring(0,10);
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



    public HashMap updateUser(String newName, String newSurname, String password, int id, String email) throws IOException, InterruptedException {
        name = newName;
        surname = newSurname;


        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("password",password);
        inputValues.put("id",Integer.toString(id));
        inputValues.put("email",email);

        return inputValues;
    }
/*
    public static void createUser(String email, String password, String name, String surname, String birthdate, String type) throws NoSuchAlgorithmException, IOException, InterruptedException {
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password", API.passwordHash(password));
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("birthdate",birthdate);
        inputValues.put("type",type);
        updateInDatabase(inputValues, "create");

    }

    public void modifyPassword(String pwd) throws NoSuchAlgorithmException, IOException, InterruptedException {
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("password",API.passwordHash(pwd));
        inputValues.put("id", Integer.toString(id));
        updateInDatabase(inputValues, "update");
    }


    private static void updateInDatabase(HashMap<String, String> inputValues, String action) throws IOException, InterruptedException {
        //Gson gson = new Gson ();

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table","user");
        inputData.put("values", inputValues);
        //String inputJson = gson.toJson(inputData);

        //var response = API.sendRequest(inputJson, action);

        //TODO: if API is disconnected
    }

     */

}
