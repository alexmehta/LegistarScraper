package com.hayward.spring.email.LocationBased;

import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

//This class will check if an event is within a certian radius of the user
@org.springframework.stereotype.Service
@AllArgsConstructor
public class Miles {
    private final EmailsService sender;

    public double calculateDistanceInMeters(double lat1, double long1, double lat2,
                                            double long2) {
        return org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
    }

    public boolean notSent(int event, int user) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
            stmt = conn.createStatement();
            String sql = "SELECT Count(*) FROM location_notifications WHERE event_id = %s and user_id = %s";
            sql = String.format(sql, event, user);
            ResultSet rs = stmt.executeQuery(sql);
            int rowCount = 0;
            if (rs.next()) {
                rowCount = Integer.parseInt(rs.getString("count(*)"));
            }
            return rowCount == 0;
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
        return false;
    }

    public void insert(int user_id, int event_id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
            stmt = conn.createStatement();
            String sql = "INSERT INTO location_notifications(event_id,user_id) VALUES (%s,%s)";
            sql = String.format(sql, event_id, user_id);
            stmt.execute(sql);
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

    //checks if any of the events are viable
    public void mission(User user) throws MessagingException, IOException {
        ArrayList<Event> events = getTags();
        for (Event event : events) {
            if (checkDistance(event, user) && notSent(event.getId(), user.getId())) {
                //since this should be a notification, we will check if email has already been sent, if not, add to db then send
                sender.send(user.getEmail(), event.getName() + " is near your location", event.getName(), event.getDate(), event.getLocation());
                insert(user.getId(), event.getId());
            }
        }
    }

    //checks distance
    public boolean checkDistance(Event event, User user) {
        System.out.println(user.getMiles() * 1609.334);
        System.out.println(event.getLat() + " " + event.getLongitude());
        return calculateDistanceInMeters(event.getLat(), event.getLongitude(), user.getLat(), event.getLongitude()) <= user.getMiles() * 1609.334;
    }

    //gets all the events and creates an event class to iterate through
    public ArrayList<Event> getTags() {
        ArrayList<Event> events = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM upcomingevents where lat is not null");
            while (resultSet.next()) {
                String date = resultSet.getString("date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
                java.util.Date dateObject = simpleDateFormat.parse(date);
                //time right now
                long unixTimestamp = Instant.now().getEpochSecond();
                if (unixTimestamp < dateObject.getTime() / 1000) {
                    Event event = new Event();
                    event.setName(resultSet.getString("name"));
                    event.setId(resultSet.getInt("id"));
                    event.setLocation(resultSet.getString("location"));
                    event.setTime(resultSet.getString("time"));
                    event.setDate(resultSet.getString("date"));
                    event.setLat(resultSet.getDouble("lat"));
                    event.setLongitude(resultSet.getDouble("long"));
                    events.add(event);
                }
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
        return events;
    }

}
