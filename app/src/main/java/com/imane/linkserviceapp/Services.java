package com.imane.linkserviceapp;

public class Services {

    private String title;
    private int photo;

    public Services(String name, int photo) {
        this.title = name;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
