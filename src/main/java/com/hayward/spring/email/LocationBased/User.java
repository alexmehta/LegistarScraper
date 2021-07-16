package com.hayward.spring.email.LocationBased;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

/**
 * Similar to the Event class, which you can see some details for
 *
 * @see Event events class (similar, but for events)
 */

public class User {
    private double longitude;
    private double lat;
    private String email;
    private String name;
    private int id;
    private double miles;

    public User(double longitude, double lat, String email, String name, int id, double miles) {
        this.longitude = longitude;
        this.lat = lat;
        this.email = email;
        this.name = name;
        this.id = id;
        this.miles = miles;
    }

    public User(double longitude, double lat, String email, String name, double miles) {
        this.longitude = longitude;
        this.lat = lat;
        this.email = email;
        this.name = name;
        this.miles = miles;
    }

    public User() {
    }

    public User(double longitude, double lat, String email, String name) {
        this.longitude = longitude;
        this.lat = lat;
        this.email = email;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        EmailValidator emailValidator = new EmailValidator();
        if (emailValidator.isValid(email, null)) {
            this.email = email;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName(String first, String last) {
        this.name = first + " " + last;
    }
}
