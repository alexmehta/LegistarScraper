package com.hayward.spring.notifications.Entity;

import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Table(name = "upcomingevents")
public class Upcomingevents implements Comparable<Upcomingevents>{

  private String zoomlink;
  private String url;
  private Double lat;
  private Double longitude;
  private long deleteable;
  private String tag;
  private String pdf;
  private String location;
  private String time;
  private String date;
  private String name;
  @Id()
  @GeneratedValue(strategy= GenerationType.AUTO)
  private long id;


  public String getZoomlink() {
    return zoomlink;
  }

  public void setZoomlink(String zoomlink) {
    this.zoomlink = zoomlink;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }


  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }


  public long getDeleteable() {
    return deleteable;
  }

  public void setDeleteable(long deleteable) {
    this.deleteable = deleteable;
  }


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }


  public String getPdf() {
    return pdf;
  }

  public void setPdf(String pdf) {
    this.pdf = pdf;
  }


  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Upcomingevents{" +
            "zoomlink='" + zoomlink + '\'' +
            ", url='" + url + '\'' +
            ", lat=" + lat +
            ", longitude=" + longitude +
            ", deleteable=" + deleteable +
            ", tag='" + tag + '\'' +
            ", pdf='" + pdf + '\'' +
            ", location='" + location + '\'' +
            ", time='" + time + '\'' +
            ", date='" + date + '\'' +
            ", name='" + name + '\'' +
            ", id=" + id +
            '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public int compareTo(@NotNull Upcomingevents o) {
    return 0;
  }
}
