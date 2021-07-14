package com.hayward.spring;

import com.hayward.spring.email.GetNotifications;
import com.hayward.spring.email.LocationBased.Service;
import com.hayward.spring.email.updates.GetIntrestingEvents;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.*;

@SpringBootApplication
@AllArgsConstructor
public class EventsBackend {
    private final GetNotifications getNotifications;
    private final GetIntrestingEvents getIntrestingEvents;
    private final Service inArea;

    public static void main(String[] args) {
        SpringApplication.run(EventsBackend.class, args);
    }

    //real time once per week for intresting events
    @Scheduled(fixedRate = 604800 * 1000)
    void sendEmails() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
            stmt = conn.createStatement();
            ResultSet statement = stmt.executeQuery("SELECT * FROM users");
            while (statement.next()) {
                getIntrestingEvents.GenerateEmail(statement.getInt("id"));
            }
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //checks every 15 minutes for events to end an email about
    @Scheduled(fixedRate = 1800 * 1000)
    void checkupdates() {
        getNotifications.getEvents();
    }

    @Scheduled(fixedRate = 5 * 1000)
        //every 5 minutes
    void checkingArea() {
        inArea.runService();
    }

    @EnableScheduling
    class SchedulingConfiguration {

    }
}
