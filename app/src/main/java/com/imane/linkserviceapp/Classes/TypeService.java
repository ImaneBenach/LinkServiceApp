package com.imane.linkserviceapp.Classes;


import com.imane.linkserviceapp.R;

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

    public int getImage() { return R.drawable.services_logo; }

    public int getActive() { return active; }

}
