package com.imane.linkserviceapp.Classes;

import com.imane.linkserviceapp.API.ConfigAPI;

import java.io.Serializable;

import retrofit2.Retrofit;

public class Badge implements Serializable {
    private int id;
    private String name;
    private String image;
    private int pointsLimit;
    private int id_type;


    public Badge(int id, String name, String image, int pointsLimit, int id_type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.pointsLimit = pointsLimit;
        this.id_type = id_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPointsLimit() {
        return pointsLimit;
    }

    public void setPointsLimit(int pointsLimit) {
        this.pointsLimit = pointsLimit;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }


}
