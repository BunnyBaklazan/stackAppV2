package com.example.stackapp.model;

import java.sql.Date;

public class BoxData {

    private long boxId;
    private long clientId; // client that owns a box
    private String date_from; // date start
    private String date_end; // date end
    private String fulfillment; // date when it was placed
    private String status; // the last time box was disturbed
    private String weight; // weight of box
    private String shelfId; // the name of the shelf where the box keeps
    private String info_note; // addition info about box


    public BoxData(long boxId, long clientId, String date_from, String date_end, String fulfillment, String status, String info_note, String weight, String shelfId) {
        this.boxId = boxId;
        this.clientId = clientId;
        this.date_from = date_from;
        this.date_end = date_end;
        this.fulfillment = fulfillment;
        this.status = status;
        this.weight = weight;
        this.shelfId = shelfId;
        this.info_note = info_note;

    }

    public long getBox_id() { return boxId; }

    public void setBox_id(long box_id) { this.boxId = box_id; }


    public long getClient_id() { return clientId; }

    public void setClient_id(long client_id) { this.clientId = client_id; }


    public String getDate_from() { return date_from; }

    public void setDate_from(String date_from) { this.date_from = date_from; }


    public String getDate_end() { return date_end; }

    public void setDate_end(String date_end) { this.date_end = date_end; }


    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }


    public String getShelfId() { return shelfId; }

    public void setShelfId(String shelfId) { this.shelfId = shelfId; }


    public String getInfo_note() { return info_note; }

    public void setInfo_note(String info_note) { this.info_note = info_note; }


    public String getFulfillment() { return fulfillment; }

    public void setFulfillment(String fulfillment) { this.fulfillment = fulfillment; }


    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
