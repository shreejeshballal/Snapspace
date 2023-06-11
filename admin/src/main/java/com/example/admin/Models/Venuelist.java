package com.example.admin.Models;

import com.example.admin.adapters.Venuelistadapter;

public class Venuelist {
    public String name;

    public Venuelist(){}
    public Venuelist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

