/*
 */

package com.hayward.spring.events.EventScraper;

import java.sql.*;

/**
 * Implements SQL tools contract in order to access database
 * All it does is delete all events from upcoming events (no more truncation)
 */
public class CleanDB implements SQLTools {
    Connection connection;

    public CleanDB() throws SQLException {
        connection = setConnection();
    }

    public void cleanDB() throws SQLException {
        Connection conn = connection;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = conn.createStatement();
            String query1 = "DELETE FROM upcomingevents WHERE deleteable=1";
            PreparedStatement ps = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            query1 = "DELETE FROM upcomingevents WHERE deleteable=1";
            stmt = conn.createStatement();
            stmt.executeUpdate(query1);
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

    @Override
    public Connection setConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cityofhayward";
        Connection conn = DriverManager.getConnection(url, "devuser", "devpass");
        return conn;
    }
}
