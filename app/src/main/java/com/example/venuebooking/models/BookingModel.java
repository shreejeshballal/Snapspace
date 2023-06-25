package com.example.venuebooking.models;


public class BookingModel {
    private String name;
    private String cost;
    private String date;
    private String slot;
    private String title;
    private String userId;
    private String userCnt;
    private String venueId;
    private boolean cleaning,food,payment,status;
    private String venueName;
    private String phone;
    private String dateId;
    private String documentId;

    public BookingModel(String name, String cost, String date, String slot, String title, String userId, String userCnt, String venueId, boolean cleaning, boolean food, boolean payment, boolean status, String venueName, String phone,String dateId,String documentId) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.slot = slot;
        this.title = title;
        this.userId = userId;
        this.userCnt = userCnt;
        this.venueId = venueId;
        this.cleaning = cleaning;
        this.food = food;
        this.payment = payment;
        this.status = status;
        this.venueName = venueName;
        this.phone = phone;
        this.dateId = dateId;
        this.documentId = documentId;
    }


    public BookingModel()
    {
        
    }


    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(String userCnt) {
        this.userCnt = userCnt;
    }

    public boolean isCleaning() {
        return cleaning;
    }

    public void setCleaning(boolean cleaning) {
        this.cleaning = cleaning;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
