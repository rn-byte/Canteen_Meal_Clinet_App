package com.inim.canteenmeal;

public class DynamicRVModel {

    String name;
    int image;


    public DynamicRVModel(int image,String name) {
        this.image=image;
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public int getImage(){
        return image;
    }


}
