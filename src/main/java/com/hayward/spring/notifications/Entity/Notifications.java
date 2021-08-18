package com.hayward.spring.notifications.Entity;


public class Notifications {

  private long id;
  private long eventid;
  private long userid;
  private long sent;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getEventid() {
    return eventid;
  }

  public void setEventid(long eventid) {
    this.eventid = eventid;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public long getSent() {
    return sent;
  }

  public void setSent(long sent) {
    this.sent = sent;
  }

}
