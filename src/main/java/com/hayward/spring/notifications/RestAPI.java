/*
 */

package com.hayward.spring.notifications;

import com.hayward.spring.notifications.Functional.Main;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

@RestController
@AllArgsConstructor
public class RestAPI {
    private EntityManagerFactory entityManagerFactory;

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
    public String getNotifications(){
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Main main = new Main(sessionFactory);
        return Arrays.toString(main.getNotifications().toArray());
    }

}
