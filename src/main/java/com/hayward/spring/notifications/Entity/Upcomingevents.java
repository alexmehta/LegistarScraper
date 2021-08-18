package com.hayward.spring.notifications.Entity;


public class Upcomingevents {

  private long id;
  private String name;
  private String date;
  private String time;
  private String location;
  private String pdf;
  private String tag;
  private long deleteable;
  private double long;
  private double lat;
  private String url;
  private String zoomlink;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getPdf() {
    return pdf;
  }

  public void setPdf(String pdf) {
    this.pdf = pdf;
  }


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }


  public long getDeleteable() {
    return deleteable;
  }

  public void setDeleteable(long deleteable) {
    this.deleteable = deleteable;
  }


  public double getLong() {
    return long;
  }

  public void setLong(double long) {
    this.long = long;
  }


  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getZoomlink() {
    return zoomlink;
  }

  public void setZoomlink(String zoomlink) {
    this.zoomlink = zoomlink;
  }

}
