package com.imane.linkserviceapp.Classes;

import com.imane.linkserviceapp.R;

public class Service {
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

    public String getDate() { return date; }

    public String getDescription() { return description; }

    public int getCost() { return cost; }

    public int getProfit() { return profit; }

    public String getAdress() { return adress; }

    public String getAccess() { return access; }

    public int getImage() { return R.drawable.services_logo; }
}

