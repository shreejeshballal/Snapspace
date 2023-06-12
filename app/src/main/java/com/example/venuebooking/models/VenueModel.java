package com.example.venuebooking.models;

import java.util.ArrayList;

public class VenueModel {

    String name;
    String img_url;
    String desc;

    public VenueModel() {
    }


    public VenueModel(String name, String img_url,String desc) {
        this.name = name;
        this.img_url = img_url;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
