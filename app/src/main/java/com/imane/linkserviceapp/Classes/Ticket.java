package Classes;


import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Ticket {
    private int id;
    private String date;
    private String description;
    private int id_user_creator;
    private int statut;

    public Ticket(int idTicket, String da, String de, int userC, String uA, int s) {
        id = idTicket;
        date = da.substring(0,10);
        description = de;
        statut = s;
        id_user_creator = userC;


    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getStatut() {
        return statut;
    }

    public void createTicket() throws IOException, InterruptedException {
        updateInDatabase("create");
    }

    public void updateTicket(String newDescription, String newUserAssigned, String newStatus) throws IOException, InterruptedException {
        date = date.substring(0, 10);
        description = newDescription;
        statut = Integer.parseInt(newStatus);

        updateInDatabase("update");
    }

    public void closeTicket() throws IOException, InterruptedException {
        date = date.substring(0,10);
        statut = 1;
        updateInDatabase("update");
    }

    private void updateInDatabase(String action) throws IOException, InterruptedException {
        //Gson gson = new Gson();
        HashMap<String, String> inputValues = new HashMap<>();
        if (action.equals("create") || action.equals("update")) {
            inputValues.put("date", date);
            inputValues.put("description", description);
            inputValues.put("statut", Integer.toString(statut));
            inputValues.put("id_user_creator", Integer.toString(id_user_creator));

        }

        if (action.equals("update")) {
            inputValues.put("id", Integer.toString(id));
        }

        HashMap<String, Object> inputData = new HashMap<>();
        inputData.put("table", "ticket");
        inputData.put("values", inputValues);
        //String inputJson = gson.toJson(inputData);

        //var response = API.sendRequest(inputJson, action);

        //TODO: if API is disconnected
    }
}
