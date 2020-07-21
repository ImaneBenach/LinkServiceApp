package com.imane.linkserviceapp.Classes;

public class MessageChat {
    private int id;
    private String content;
    private String date;
    private int id_sender;
    private int id_dest;


    public MessageChat(int id, String content, String date, int id_sender, int id_dest) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.id_sender = id_sender;
        this.id_dest = id_dest;
    }
}
