package com.example.stackapp.model;

public class BoxData {

    private long id;
    private long clientId; // client that owns a box
    private String dateFrom; // date start
    private String dateEnd; // date end
    private String fulfillment; // date when it was placed
    private String status; // the last time box was disturbed
    private String weight; // weight of box
    private String shelfId; // the name of the shelf where the box keeps
    private String infoNote; // addition info about box


    public BoxData(
            long id,
            long clientId,
            String dateFrom,
            String dateEnd,
            String fulfillment,
            String status,
            String infoNote,
            String weight,
            String shelfId) {

        this.id = id;
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateEnd = dateEnd;
        this.fulfillment = fulfillment;
        this.status = status;
        this.weight = weight;
        this.shelfId = shelfId;
        this.infoNote = infoNote;

    }

    public long getId() { return id; }

    public void setId(long boxId) { this.id = boxId; }


    public long getClient_id() { return clientId; }

    public void setClientId(long clientId) { this.clientId = clientId; }


    public String getDateFrom() { return dateFrom; }

    public void setDateFrom(String dateFrom) { this.dateFrom = dateFrom; }


    public String getDateEnd() { return dateEnd; }

    public void setDateEnd(String dateEnd) { this.dateEnd = dateEnd; }


    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }


    public String getShelfId() { return shelfId; }

    public void setShelfId(String shelfId) { this.shelfId = shelfId; }


    public String getInfoNote() { return infoNote; }

    public void setInfoNote(String infoNote) { this.infoNote = infoNote; }


    public String getFulfillment() { return fulfillment; }

    public void setFulfillment(String fulfillment) { this.fulfillment = fulfillment; }


    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
