package com.imane.linkserviceapp.Classes;


import java.io.IOException;
import java.util.HashMap;

public class TypeService {
    private final int id;
    private String name;
    private String description;
    private String picture;
    private int active;

    public TypeService(int idSection, String n, String d, String i, int a) {
        id = idSection;
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

    public String getImage() { return picture; }

    public int getActive() { return active; }

    public void createType() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateType(String newName, String newDescription, String newPicture, int newStatusActive) throws IOException, InterruptedException {
        name = newName;
        description = newDescription;
        picture = newPicture;
        active = newStatusActive;

        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        //Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if(action.equals("create") || action.equals("update")){
            inputValues.put("name", name);
            inputValues.put("description", description);
            inputValues.put("picture", picture);
            inputValues.put("active", Integer.toString(active));
        }
        if (action.equals("update")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "type_service");
        inputData.put("values", inputValues);
        //String inputJson = gson.toJson(inputData);

        //var response = API.sendRequest(inputJson, action);
        //TODO: if API is disconnected
    }
}
