package com.hayward.spring.old;

import com.hayward.spring.events.ClassificationRunner;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ReaderClass {

    public int RW(File file) throws IOException, SQLException {
        long lines = Files.lines(file.toPath()).count();
        Scanner s = new Scanner(file);
        int number = (int) (lines / 7);
        System.out.println("NUMBER:");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        System.out.println(number);
        System.out.println(number * 7);
        String host = "jdbc:sqlserver://server;databaseName=db";
        String username = "devuser";
        String password = "devpass";
        ClassificationRunner runner = new ClassificationRunner();

        PrintWriter printWriter = new PrintWriter(new File("src/main/tmp/test.txt"));
        for (int i = 0; i < number; i++) {
            ArrayList<String> strings = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                strings.add(br.readLine());
            }
            Connection conn = null;
            Statement stmt = null;
            try {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (Exception e) {
                    System.out.println(e);
                }
                String url = "jdbc:mysql://localhost:3306/cityofhayward";
                conn = DriverManager.getConnection(url, "devuser", "devpass");
                System.out.println("Connection is created successfully:");
                stmt =conn.createStatement();
                String tag = runner.tag(strings.get(0));
                System.out.println(tag);
                String query1 = "INSERT INTO upcomingevents (name,date,time,location,presentations,documents,officalmin,tag) " + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s')";

                query1 = String.format(query1, strings.get(0),strings.get(1),strings.get(2),strings.get(3),strings.get(4),strings.get(5),strings.get(6),tag);
                System.out.println(query1);
                stmt.executeUpdate(query1);

                conn.close();
                System.out.println("Record is inserted in the table successfully..................");
            } catch (SQLException excep) {
                excep.printStackTrace();
            } catch (Exception excep) {
                excep.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        conn.close();
                } catch (SQLException se) {}
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }

        printWriter.close();
        return number;
    }
}
