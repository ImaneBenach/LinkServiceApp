package com.imane.linkserviceapp.Classes;

public class Apply {
    private int id_user;
    private int id_service;
    private int execute;
    private int note;
    private String commentaire;

    public Apply(int user, int service, int exec){
        this.id_user = user;
        this.id_service = service;
        this.execute = exec;
    }

    public int getId_service() {
        return id_service;
    }

    public int getId_user() {
        return id_user;
    }

    public int getExecute() {
        return execute;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setExecute(int execute) {
        this.execute = execute;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}