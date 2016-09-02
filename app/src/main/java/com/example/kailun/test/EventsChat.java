package com.example.kailun.test;

/**
 * Created by kailun on 2016/5/28.
 */
public class EventsChat {
    private  String name,location,date,image;
    public EventsChat(){


    }
    public EventsChat(String name,String location, String date,String image){
        this.name = name;
        this.location = location;
        this.date = date;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
}
