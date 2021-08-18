package com.hayward.spring.notifications.Entity;


public class Messages {

  private long id;
  private long user;
  private long event;
  private long meetingEvent;
  private String subject;
  private String message;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUser() {
    return user;
  }

  public void setUser(long user) {
    this.user = user;
  }


  public long getEvent() {
    return event;
  }

  public void setEvent(long event) {
    this.event = event;
  }


  public long getMeetingEvent() {
    return meetingEvent;
  }

  public void setMeetingEvent(long meetingEvent) {
    this.meetingEvent = meetingEvent;
  }


  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
