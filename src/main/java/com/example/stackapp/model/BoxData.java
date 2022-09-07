package com.example.stackapp.model;

import java.sql.Date;

public class BoxData {

    private long box_id;
    private long client_id; // client that owns a box
    private Date date_from; // date start
    private Date date_end; // date end
    private String fulfillment; // date when it was placed
    private String status; // the last time box was disturbed
    private String weight; // weight of box
    private String shelf_id; // the name of the shelf where the box keeps
    private String info_note; // addition info about box


    public BoxData(long box_id, long client_id, Date date_from, Date date_end, String fulfillment, String status, String info_note, String weight, String shelf_id) {
        this.box_id = box_id;
        this.client_id = client_id;
        this.date_from = date_from;
        this.date_end = date_end;
        this.fulfillment = fulfillment;
        this.status = status;
        this.weight = weight;
        this.shelf_id = shelf_id;
        this.info_note = info_note;

    }

    public long getBox_id() { return box_id; }

    public void setBox_id(long box_id) { this.box_id = box_id; }


    public long getClient_id() { return client_id; }

    public void setClient_id(long client_id) { this.client_id = client_id; }


    public Date getDate_from() { return date_from; }

    public void setDate_from(Date date_from) { this.date_from = date_from; }


    public Date getDate_end() { return date_end; }

    public void setDate_end(Date date_end) { this.date_end = date_end; }


    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }


    public String getShelf_id() { return shelf_id; }

    public void setShelf_id(String shelf_id) { this.shelf_id = shelf_id; }


    public String getInfo_note() { return info_note; }

    public void setInfo_note(String info_note) { this.info_note = info_note; }


    public String getFulfillment() { return fulfillment; }

    public void setFulfillment(String fulfillment) { this.fulfillment = fulfillment; }


    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
