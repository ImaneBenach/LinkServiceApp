package com.imane.linkserviceapp.Classes;


import com.google.gson.Gson;
import com.imane.linkserviceapp.Classes.API;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User {
    private  int id;
    private String email;
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


    public static String signin(String email, String password) throws NoSuchAlgorithmException, IOException, InterruptedException {
        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("email",email);
        inputValues.put("password", API.passwordHash(password));

        Gson gson = new Gson();
        String json = gson.toJson(inputValues);
        return  json;
    }

    /*

    public void updateUser(String newName, String newSurname, String newBirthdate, String newAdress, String newCity) throws IOException, InterruptedException {
        name = newName;
        surname = newSurname;
        birthdate = newBirthdate.substring(0,10);
        adress = newAdress;
        city = newCity;

        HashMap<String, String> inputValues = new HashMap<>();
        inputValues.put("name",name);
        inputValues.put("surname",surname);
        inputValues.put("birthdate",birthdate);
        inputValues.put("adress",adress);
        inputValues.put("city",city);
        inputValues.put("id", Integer.toString(id));

        updateInDatabase(inputValues, "update");
    }

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
