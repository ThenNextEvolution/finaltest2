package com.example.demo;

import javafx.scene.Node;

import java.io.Serializable;

public class Message extends Node implements Serializable {
    public String name;
    public String message;
    public   int idnum;

    public Message(int id, String nam, String me){
        this.name =nam;
        this.message =me;
        this.idnum=id;


    }

}
