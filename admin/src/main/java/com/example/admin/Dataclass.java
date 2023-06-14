package com.example.admin;

public class Dataclass {
    private String img_ul,desc,name;

    public Dataclass() {

    }
    public Dataclass(String img_ul, String desc, String name) {
        this.img_ul = img_ul;
        this.desc = desc;
        this.name = name;
    }
    public String getImg_ul() {
        return img_ul;
    }

    public void setImg_ul(String img_ul) {
        this.img_ul = img_ul;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
