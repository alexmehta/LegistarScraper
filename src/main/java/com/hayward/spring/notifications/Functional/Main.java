/*
 */

package com.hayward.spring.notifications.Functional;

import com.hayward.spring.notifications.Entity.Notifications;
import com.hayward.spring.notifications.Entity.Upcomingevents;
import com.hayward.spring.notifications.Entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.PriorityQueue;

public class Main {

    Session session;

    public Main() {
    }

    public Main(SessionFactory session) {
        this.session = session.openSession();
    }

    //get all the upcoming events that
    public PriorityQueue<Upcomingevents> getUpcomingEvents() {
        PriorityQueue<Upcomingevents> upcomingevents = new PriorityQueue<>();
        session.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        upcomingevents.addAll(session.createQuery("FROM Upcomingevents").setMaxResults(1000).getResultList());
        t.commit();
        return upcomingevents;
    }

    public PriorityQueue<Users> getUsers() {
        PriorityQueue<Users> upcomingevents = new PriorityQueue<>();
        session.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        upcomingevents.addAll(session.createQuery("FROM Users ").setMaxResults(1000).getResultList());
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
