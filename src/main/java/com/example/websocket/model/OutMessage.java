package com.example.websocket.model;


public class OutMessage {

    public String time;

    public int online;

    public String from;

    public int msgType;

    public String msg;

    public int width;

    public int height;

    public OutMessage(String time,int online ,String from, int msgType, String msg, int width, int height) {
        this.time = time;
        this.online = online;
        this.from = from;
        this.msgType = msgType;
        this.msg = msg;
        this.width = width;
        this.height = height;
    }
}