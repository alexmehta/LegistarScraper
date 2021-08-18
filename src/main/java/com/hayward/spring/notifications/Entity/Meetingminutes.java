package com.hayward.spring.notifications.Entity;


public class Meetingminutes {

  private long id;
  private String name;
  private String tag;
  private long event;


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


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }


  public long getEvent() {
    return event;
  }

  public void setEvent(long event) {
    this.event = event;
  }

}
