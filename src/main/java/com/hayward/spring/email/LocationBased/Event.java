package com.hayward.spring.email.LocationBased;

public class Event {
    private int id;
    private String name;
    private String location;
    private String date;
    private String time;
    private double longitude;
    private double lat;

    public Event(int id, String name, String location, String date, String time, double longitude, double lat) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.longitude = longitude;
        this.lat = lat;
    }

    public Event() {
    }

    public Event(int id, String name, String location, String date, String time) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
