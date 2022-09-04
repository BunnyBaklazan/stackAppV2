package com.example.stackapp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class BoxData {

    private long box_id;
    private long client_id; // client that owns a box
    private Date date_from; // date start
    private Date date_end; // date end
    private Date placed; // date when it was placed
    private Timestamp edited_at; // the last time box was disturbed
    private String weight; // weight of box
    private long user_id; // worker that placed it or edited

    public BoxData(long box_id, long client_id, Date date_from, Date date_end, Date placed, Timestamp edited_at, String weight, long user_id) {
        this.box_id = box_id;
        this.client_id = client_id;
        this.date_from = date_from;
        this.date_end = date_end;
        this.placed = placed;
        this.edited_at = edited_at;
        this.weight = weight;
        this.user_id = user_id;
    }

    public long getBox_id() {
        return box_id;
    }

    public void setBox_id(long box_id) {
        this.box_id = box_id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public Date getPlaced() {
        return placed;
    }

    public void setPlaced(Date placed) {
        this.placed = placed;
    }

    public Timestamp getEdited_at() {
        return edited_at;
    }

    public void setEdited_at(Timestamp edited_at) {
        this.edited_at = edited_at;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
