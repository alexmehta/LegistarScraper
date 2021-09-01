/*
 */

package com.hayward.spring.notifications;

import com.hayward.spring.notifications.Emails.EmailService;
import com.hayward.spring.notifications.Entity.Notifications;
import com.hayward.spring.notifications.Entity.Upcomingevents;
import com.hayward.spring.notifications.Entity.Users;
import com.hayward.spring.notifications.Functional.Main;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.PriorityQueue;

@RestController
@AllArgsConstructor
public class RestAPI {
    private EntityManagerFactory entityManagerFactory;

    private EmailService emailService;

    @PostMapping("notifications/test")
    public String getEvents() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Main main = new Main(sessionFactory);
        System.out.println();
        return Arrays.toString(main.getUpcomingEvents().toArray());
    }

    @PostMapping("notifications/test2")
    public String getUsers() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Main main = new Main(sessionFactory);
        return Arrays.toString(main.getUsers().toArray());
    }

    @PostMapping("notifications/test3")
    public String getNotifications() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Main main = new Main(sessionFactory);
        return Arrays.toString(main.getNotifications().toArray());
    }


    @SneakyThrows
    @PostMapping("notifications")
    public String getNotificationsToSend() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Main main = new Main(sessionFactory);
        ArrayList<Users> users = main.getUsers();
        ArrayList<Upcomingevents> upcomingevents = main.getUpcomingEvents();
        PriorityQueue<Notifications> Queue = main.getNotifications();
        //queue of all notifications
        System.out.println(Queue);
        while (!Queue.isEmpty()) {
            //process a notification and find the upcoming event
            Notifications notifications = Queue.poll();
            if (notifications.getSent()==1){
                continue;
            }
            for (int i = 0; i < upcomingevents.size(); i++) {
                if (upcomingevents.get(i).getId() == notifications.getEventid()) {

                    System.out.println("passed id check");
                    System.out.println(upcomingevents.get(i));
                    long time = System.currentTimeMillis() / (24 * 3600 * 1000) * (24 * 3600 * 1000);
                    long time2 = time + (24 * 3600 * 1000);
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(upcomingevents.get(i).getDate());
                    long eventTime = date.getTime();
                    System.out.println(eventTime);
                    System.out.println(time2);
                    if (notifications.getSent() == 0 && eventTime >= time && eventTime <= time2) {
                        System.out.println("passed time check");
                        //get user details
                        for (int j = 0; j < users.size(); j++) {
                            if (users.get(j).getId() == notifications.getUserid()) {
                                //send email with user details
                                emailService.send(users.get(j), upcomingevents.get(i).getName() + " at " + upcomingevents.get(i).getLocation() + " soon", upcomingevents.get(i));
                                break;
                            }
                        }
                        Session session = sessionFactory.openSession();
                        //say notification is sent
                        notifications.setSent(1);
                        session.save(notifications);
                        session.close();
                    }
                }
            }

        }
        return "Done";
    }

}
