package com.hayward.spring.email.LocationBased;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.ApiStatus;

import java.sql.*;

/**
 * This gets all events but location based
 * and uses the Event class in the package. Miles.mission goes through each event
 */
@org.springframework.stereotype.Service
@AllArgsConstructor
@Deprecated
@ApiStatus.ScheduledForRemoval()
public class Service {
    private final Miles miles;

    public void runService() {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://20.94.243.188:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "root", "234324324");
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM users WHERE notifications=1");
            while (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setLat(resultSet.getDouble("latitude"));
                user.setLongitude(resultSet.getDouble("longitude"));
                user.setName(resultSet.getString("firstname"), resultSet.getString("lastname"));
                user.setId(resultSet.getInt("id"));
                user.setMiles(resultSet.getInt("miles"));
                miles.mission(user);
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
}
