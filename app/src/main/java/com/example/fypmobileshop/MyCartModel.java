package com.example.fypmobileshop;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String phoneImage;
    String phoneName;
    int phonePrice;
    String phoneDiscount;
    String currentDate;
    String CurrentTime;
    int totalQuantity;
    int totalPrice;

    String documentId;

    public MyCartModel() {
    }

    public MyCartModel(String phoneImage, String phoneName, int phonePrice, String phoneDiscount, String currentDate, String currentTime, int totalQuantity, int totalPrice) {
        this.phoneImage = phoneImage;
        this.phoneName = phoneName;
        this.phonePrice = phonePrice;
        this.phoneDiscount = phoneDiscount;
        this.currentDate = currentDate;
        CurrentTime = currentTime;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(String phoneImage) {
        this.phoneImage = phoneImage;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public int getPhonePrice() {
        return phonePrice;
    }

    public void setPhonePrice(int phonePrice) {
        this.phonePrice = phonePrice;
    }

    public String getPhoneDiscount() {
        return phoneDiscount;
    }

    public void setPhoneDiscount(String phoneDiscount) {
        this.phoneDiscount = phoneDiscount;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}

