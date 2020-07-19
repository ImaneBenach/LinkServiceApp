package com.imane.linkserviceapp.Classes;


import android.util.Log;

import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User implements Serializable {
    private  int id;
    private String email;
    private String name;
    private String surname;
    private String birthdate;
    private int points;
    private String adress;
    private String city;
    private String type;
    private String password;

    public User(int idUser){
        id = idUser;
    }

    public User(){

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

    public String getAdress() { return adress; }

    public String getCity() { return city; }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public static HashMap signin(String email, String password) throws NoSuchAlgorithmException, IOException, InterruptedException {
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password", API.passwordHash(password));

        return  inputValues;
    }

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
       // updateInDatabase(inputValues, "create");
        return inputValues;
    }

    public boolean buyService(int cost){
        if(points - cost >= 0){
            setPoints(points-cost);

            JSONObject jsonParam = new JSONObject();
            JSONObject jsonParamValues = new JSONObject();

            try {
                jsonParamValues.put("id", id);
                jsonParamValues.put("points", points);

                jsonParam.put("table", "user");
                jsonParam.put("values", jsonParamValues);
                Log.d("JSONPARAM",jsonParam.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                API.sendRequest(jsonParam.toString(), "update");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }



    public HashMap updateUser(String newName, String newSurname, String password, int id) throws IOException, InterruptedException, NoSuchAlgorithmException {
        name = newName;
        surname = newSurname;


        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("password",API.passwordHash(password));
        inputValues.put("id",Integer.toString(id));

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
