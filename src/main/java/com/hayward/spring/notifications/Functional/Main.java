/*
 */

package com.hayward.spring.notifications.Functional;

import com.hayward.spring.notifications.Entity.Notifications;
import com.hayward.spring.notifications.Entity.Upcomingevents;
import com.hayward.spring.notifications.Entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class Main {

    Session session;

    public Main() {
    }
    public Date getDate(String date){

        return new Date() ;
    }
    public Main(SessionFactory session) {
        this.session = session.openSession();
    }

    //get all the upcoming events that
    public ArrayList<Upcomingevents> getUpcomingEvents() {
        session.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        ArrayList<Upcomingevents> upcomingevents = new ArrayList<>(session.createQuery("FROM Upcomingevents ").setMaxResults(1000).getResultList());
        t.commit();
        return upcomingevents;
    }

    public ArrayList<Users> getUsers() {
        session.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        ArrayList<Users> upcomingevents = new ArrayList<>(session.createQuery("FROM Users ").setMaxResults(1000).getResultList());
        t.commit();
        return upcomingevents;
    }

    public PriorityQueue<Notifications> getNotifications() {
        PriorityQueue<Notifications> upcomingevents = new PriorityQueue<>();
        session.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        upcomingevents.addAll(session.createQuery("FROM Notifications ").setMaxResults(1000).getResultList());
        t.commit();
        return upcomingevents;
    }



}
