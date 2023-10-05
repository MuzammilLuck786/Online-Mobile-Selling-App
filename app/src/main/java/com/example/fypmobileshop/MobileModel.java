package com.example.fypmobileshop;

import java.io.Serializable;

public class MobileModel implements Serializable {
    String Phoneimage;
    String Phonename;
    int Phoneprice;
    String Phonediscount;
    String Storage;
    String Screen;
    String Battery;
    String Allcameras;
    String Frontcamera;



    public MobileModel() {

    }

    public MobileModel(String phoneimage, String phonename, int phoneprice, String phonediscount, String storage, String screen, String battery, String allcameras, String frontcamera) {
        Phoneimage = phoneimage;
        Phonename = phonename;
        Phoneprice = phoneprice;
        Phonediscount = phonediscount;
        Storage = storage;
        Screen = screen;
        Battery = battery;
        Allcameras = allcameras;
        Frontcamera = frontcamera;
    }

    public String getPhoneimage() {
        return Phoneimage;
    }

    public void setPhoneimage(String phoneimage) {
        Phoneimage = phoneimage;
    }

    public String getPhonename() {
        return Phonename;
    }

    public void setPhonename(String phonename) {
        Phonename = phonename;
    }

    public int getPhoneprice() {
        return Phoneprice;
    }

    public void setPhoneprice(int phoneprice) {
        Phoneprice = phoneprice;
    }

    public String getPhonediscount() {
        return Phonediscount;
    }

    public void setPhonediscount(String phonediscount) {
        Phonediscount = phonediscount;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String storage) {
        Storage = storage;
    }

    public String getScreen() {
        return Screen;
    }

    public void setScreen(String screen) {
        Screen = screen;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getAllcameras() {
        return Allcameras;
    }

    public void setAllcameras(String allcameras) {
        Allcameras = allcameras;
    }

    public String getFrontcamera() {
        return Frontcamera;
    }

    public void setFrontcamera(String frontcamera) {
        Frontcamera = frontcamera;
    }
}

