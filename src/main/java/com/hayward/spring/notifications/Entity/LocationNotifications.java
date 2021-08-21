package com.hayward.spring.notifications.Entity;


public class LocationNotifications {

  private long id;
  private long sent;
  private long eventId;
  private java.sql.Timestamp timeSent;
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getSent() {
    return sent;
  }

  public void setSent(long sent) {
    this.sent = sent;
  }


  public long getEventId() {
    return eventId;
  }

  public void setEventId(long eventId) {
    this.eventId = eventId;
  }


  public java.sql.Timestamp getTimeSent() {
    return timeSent;
  }

  public void setTimeSent(java.sql.Timestamp timeSent) {
    this.timeSent = timeSent;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
