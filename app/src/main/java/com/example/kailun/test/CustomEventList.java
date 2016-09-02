package com.example.kailun.test;

/**
 * Created by kailun on 2016/5/27.
 */
public class CustomEventList {
    private String key,event_name,event_location,event_date;

    public CustomEventList(String key,String event_name,String event_location,String event_date){
        this.key = key;
        this.event_name = event_name;
        this.event_location = event_location;
        this.event_date = event_date;
    }
    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getKey() {
        return key;
    }
}
