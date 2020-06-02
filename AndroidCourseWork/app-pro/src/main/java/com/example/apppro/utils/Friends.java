package com.example.apppro.utils;

public class Friends {
    private String name;
    private int imageId;

    public Friends(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }
}
