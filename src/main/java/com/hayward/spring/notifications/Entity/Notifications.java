package com.hayward.spring.notifications.Entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Notifications implements Comparable<Notifications> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long eventid;
    private long userid;
    private long sent;

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", eventid=" + eventid +
                ", userid=" + userid +
                ", sent=" + sent +
                '}';
    }

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

    @Override
    public int compareTo(@NotNull Notifications o) {
        return 0;
    }
}
