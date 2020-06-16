package com.imane.linkserviceapp.Classes;

public class Service {
    private int id;
    private String name;
    private String date;
    private int id_type;
    private int id_creator;

    public Service(int idService, String n, String d, int type, int creator){
        id = idService;
        name = n;
        date = d;
        id_type = type;
        id_creator = creator;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getId_creator() { return id_creator; }

    public int getId_type() { return id_type; }

    public String getDate() { return date; }
}

