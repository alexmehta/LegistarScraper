package com.hayward.spring.email.updates;

import com.hayward.spring.email.LocationBased.Event;
import com.hayward.spring.email.Secondemailservice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;

/**
 * What this does is it just finds events that a user has expressed prior interest in every week
 * (very basic newsletter)
 */
@Service
@Deprecated
@AllArgsConstructor
public class GetIntrestingEvents {

    private final Secondemailservice emailUpdateSender;

    public void GenerateEmail(int id) throws MessagingException, IOException {
        emailUpdateSender.send(getEmail(id), "weekly update", getTags(id), getUsername(id));
    }

    public String getUsername(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://20.94.243.188:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "root", "234324324");
            stmt = conn.createStatement();
            ResultSet statement = stmt.executeQuery("SELECT * FROM users WHERE id= " + id);
            while (statement.next()) {
                return statement.getString("firstname");
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
        return "not found";
    }

    public String getEmail(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://20.94.243.188:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "root", "234324324");
            stmt = conn.createStatement();
            ResultSet statement = stmt.executeQuery("SELECT * FROM users WHERE id= " + id);
            while (statement.next()) {
                return statement.getString("email");
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
        return "Not found";
    }

    public ArrayList<Event> getTags(int id) {
        ArrayList<Event> events = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://20.94.243.188:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "root", "234324324");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM upcomingevents WHERE tag in (SELECT tags FROM listtags WHERE listtags.id in (SELECT tag from following WHERE userid=%s))";
            sql = String.format(sql, id);
            ResultSet statement = stmt.executeQuery(sql);
            while (statement.next()) {
                String date = statement.getString("date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
                java.util.Date dateObject = simpleDateFormat.parse(date);
                //time + one day
                long unixTimestamp = Instant.now().getEpochSecond();
                if (unixTimestamp < dateObject.getTime() / 1000) {
                    Event event = new Event();
                    event.setName(statement.getString("name"));
                    event.setId(statement.getInt("id"));
                    event.setLocation(statement.getString("location"));
                    event.setTime(statement.getString("time"));
                    event.setDate(statement.getString("date"));
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
