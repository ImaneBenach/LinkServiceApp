package com.imane.linkserviceapp.MesServices;

public class MyServices {

    private String nom ;
    private String description ;
    private int photo ;

    public MyServices() {
    }

    public MyServices(String nom, String description, int photo) {
        this.nom = nom;
        this.description = description;
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
