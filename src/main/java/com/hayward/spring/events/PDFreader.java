package com.hayward.spring.events;
//the point of this class is to find pdf files and get the zoom link with hayward.zoom.us
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PDFreader {
    private static byte[] readFileAsBytes(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        return IOUtils.toByteArray(inputStream);
    }

    public static String convertPDFToTxt(String filePath) throws IOException {
        byte[] thePDFFileBytes = readFileAsBytes(filePath);
        PDDocument pddDoc = PDDocument.load(thePDFFileBytes);
        PDFTextStripper reader = new PDFTextStripper();
        String pageText = reader.getText(pddDoc);
        pddDoc.close();
        return pageText;
    }

    public static String getPDF(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
            stmt = conn.createStatement();
            String sql = "SELECT * FROM upcomingevents WHERE id = %s";
            sql = String.format(sql, id);
            System.out.println(sql);
            ResultSet statement = stmt.executeQuery(sql);
            while (statement.next()) {
                FileUtils.copyURLToFile(new URL("https://hayward.legistar.com/" + statement.getString("pdf")), new File("pdf.pdf"));
                return convertPDFToTxt("pdf.pdf");
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

    //go through pdf and try to find mentions of hayward.zoom.us
    public static String parsePDF(int id) {
        String text = getPDF(id);
        List<String> splitStr = Arrays.stream(text.split(" "))
                .map(String::trim)
                .collect(Collectors.toList());
        for (String s : splitStr) {
            if (s.contains("hayward.zoom.us")) {
                return s;
            }
        }
        return "";
    }

    //update the sql row for zoom link
    public static void Update(String url, int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cityofhayward", "devuser", "devpass");

        ) {

            PreparedStatement Statement = conn.prepareStatement("UPDATE upcomingevents SET zoomlink=? WHERE id=?");
            Statement.setString(1, url);
            Statement.setInt(2, id);
            Statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //main runner class
    public void attemptZoomLink(int id) {
        Update(parsePDF(id), id);
    }

}
