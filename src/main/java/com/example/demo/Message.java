package com.example.demo;

import javafx.scene.Node;

public class Message extends Node {
    public String name;
    public String message;
    public   int idnum;

    public Message(int id, String nam, String me){
        this.name =nam;
        this.message =me;
        this.idnum=id;


    }

}
