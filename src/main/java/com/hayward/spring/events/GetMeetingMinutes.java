package com.hayward.spring.events;
//todo insert links

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GetMeetingMinutes {
    public static PrintWriter out;

    static {
        try {
            out = new PrintWriter("log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ParseException {
        cleanDB();
        parseTable(8, "https://hayward.legistar.com/Calendar.aspx");
        out.close();
    }

    public static void cleanDB() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
//                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
//            System.out.println("Connection is created successfully:");
            stmt = conn.createStatement();
            String query1 = "DELETE FROM upcomingevents WHERE deleteable=1";
            PreparedStatement ps = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
//            System.out.println(query1);
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


    static void getTable(String url) throws InterruptedException, IOException {
        //downloads table and renames to info.html
        //get excel doc
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe");
        // Setting new download directory path
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Use File.separator as it will work on any OS
        prefs.put("download.default_directory",
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "tmp");
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        // Printing set download directory
//        System.out.println(options.getExperimentalOption("prefs"));
        // Launching browser with desired capabilities
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAssumeUntrustedCertificateIssuer(false);
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://hayward.legistar.com/" + url);
        String downloadFilepath = "C:\\Users\\alex\\Documents\\Programming\\pilotcity-first\\java\\src\\main\\tmp";
        profile.setPreference("browser.download.dir", downloadFilepath);
        profile.setPreference("browser.helperapps.neverAsk.saveToDisk", "application/xls");
        try {
            FileUtils.cleanDirectory(new File("src/main/tmp"));
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_menuMain\"]/ul/li[2]/a"))).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_menuMain\"]/ul/li[2]/div/ul/li[2]/a"))).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_menuMain\"]/ul/li[3]/a"))).click();

            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_menuMain\"]/ul/li[3]/div/ul/li[1]/a"))).click();

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

        TimeUnit.SECONDS.sleep(2);
        driver.close();
        try {
            File source = new File("src/main/tmp/Export.xls");
            File target = new File("src/main/tmp/info.html");
            Files.move(source.toPath(), target.toPath());
        } catch (IOException e) {
            FileUtils.cleanDirectory(new File("src/main/tmp"));
            System.out.println("problem: file not found");
        }
    }
    //insert data into table
    static int insertData(String name, String date, String time, String location, String urls, String pdf) throws IOException, ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        int id = 0;
        ClassificationRunner runner = new ClassificationRunner();
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
//                    System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
//                System.out.println("Connection is created successfully:");
            stmt = conn.createStatement();
            String query1 = "INSERT INTO upcomingevents (name,date,time,location,tag,url,pdf) " + "VALUES ('%s','%s','%s','%s','%s','%s','%s')";
            query1 = String.format(query1, name, date, time, location, runner.tag(name), urls, pdf);
            PreparedStatement ps = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        parse(new File("src/main/tmp/info.html"), 0);
        return id;


    }

    static void insertevents(String name, String tag, int event) {
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
//            System.out.println("Connection is created successfully:");
            Statement statement = conn.createStatement();
            String query = "SELECT id FROM upcomingevents WHERE id=(SELECT MAX(id) FROM upcomingevents)";
            ResultSet resultSet = statement.executeQuery(query);
            long id = 0;
            while (resultSet.next()) {
                id = resultSet.getLong("id");
            }

            String query1 = "INSERT INTO meetingminutes (name,tag,event) " + "VALUES ('%s','%s','%s')";
            stmt = conn.createStatement();
            FileUtils.cleanDirectory(new File("src/main/tmp"));
            query1 = String.format(query1, name, tag, id);
            query1 = query1.replace("'", "'");

//            System.out.println(query1);
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

    static void parse(File file, int id) {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, null);
            Element table = doc.body();
            if (!doc.body().text().equals("NULL")) {
                Elements rows = table.select("tr");
                Elements ths = rows.select("td");
                ClassificationRunner runner = new ClassificationRunner();

                for (Element row : rows) {
                    Elements tds = row.select("td");
                    int i = 0;
                    String type = "";
                    for (Element td : tds) {
                        if (i == 3) {
                            type = td.text();
                            insertevents(type, runner.tag(type), id);
                            break;
                        }
                        i++;
                    }
                    // in the row
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void click(String url) throws IOException, InterruptedException, ParseException, SQLException {
        Document doc = Jsoup.connect("https://hayward.legistar.com/" + url).get();
        Element meetingitems = doc.getElementsByClass("rtsUL").get(1);
        System.out.println(meetingitems.text());
        if (!meetingitems.text().equals("Meeting Items (0)")) {
            getTable(url);
        } else {
            System.out.println("no meeting items, error thrown");
        }
        String name = doc.getElementById("ctl00_ContentPlaceHolder1_hypName").text();
        String date = doc.getElementById("ctl00_ContentPlaceHolder1_lblDate").text();
        String time = doc.getElementById("ctl00_ContentPlaceHolder1_lblTime").text();
        String location = doc.getElementById("ctl00_ContentPlaceHolder1_lblLocation").text();
        System.out.println(name);
        Element agenda = doc.getElementById("ctl00_ContentPlaceHolder1_hypAgenda");
        insertData(name, date, time, location, url, agenda.attr("href"));
    }

    static void insertPDF(String url, int id) throws FileNotFoundException, SQLException {
        String urle = "jdbc:mysql://localhost:3306/cityofhayward";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urle, "devuser", "devpass");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Statement statement = conn.createStatement();
        String query = "UPDATE upcomingevents SET url = '%s' WHERE id='%s'";
        query = String.format(query, url, id);
        System.out.println(query);
        out.println(query);
        try {
            System.out.println("pdf");
            conn = DriverManager.getConnection(urle, "devuser", "devpass");
            System.out.println("Connection is created successfully:");
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    static void insertEventNOTNULL(String name, String date, String time, String location) {
        ClassificationRunner runner = new ClassificationRunner();
        Connection conn = null;
        Statement stmt = null;
        if (time.equals("")) {
            time = "Time not set yet.";
        }
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
//                System.out.println(e);
            }
            String url = "jdbc:mysql://localhost:3306/cityofhayward";
            conn = DriverManager.getConnection(url, "devuser", "devpass");
//            System.out.println("Connection is created successfully:");
            stmt = conn.createStatement();
            String query1 = "INSERT INTO upcomingevents (name,date,time,location,tag) " + "VALUES ('%s','%s','%s','%s','%s')";

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            query1 = String.format(query1, name, date, time, location, runner.tag(name));
            System.out.println(query1);
            PreparedStatement ps = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
//            System.out.println(query1);
            ps.executeUpdate();
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
    //goes through table and gets all events
    public static void parseTable(int num, String url) throws IOException, InterruptedException, ParseException, SQLException {
        Document doc = Jsoup.connect(url).get();
        Element table = doc.getElementById("ctl00_ContentPlaceHolder1_gridCalendar_ctl00");
        Elements rows = table.select("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements columns = row.select("td");

            String name = null;
            String date = null;
            String time = null;
            String location = null;
            for (int j = 0; j < columns.size(); j++) {
                if (j == 0) name = columns.get(j).text();
                if (j == 1) date = columns.get(j).text();
                if (i == 2) time = columns.get(j).text();
                if (i == 4) location = columns.get(j).text();
                if (j == 5) {
                    //get link and then send to webdrivwer
//                    System.out.println(time);
                    Element atag = columns.get(j).select("a").first();
//                    System.out.println(atag.attr("href"));
                    String urle = atag.attr("href");
//                    System.out.println(url);
                    if (atag.attr("href").equals("") || !atag.attr("href").startsWith("MeetingDetail")) {
//                        //  System.out.println("no adj, defaulting to inserting regularly");
                        //inserts event if no meeting minutes
                        insertEventNOTNULL(columns.get(0).text(), columns.get(1).text(), columns.get(3).text(), columns.get(4).text());
                    } else {
                        //simulates click of the meeting minutes
                        click(atag.attr("href"));

                    }
                }

            }
        }
        System.err.println("System.out.printlkasdnklf;sadj;klfasdkl;jf;asldkjf;" +
                "sda" +
                "fasd" +
                "f" +
                "asdf" +
                "asd" +
                "fasd" +
                "asd" +
                "f" +
                "asd");
        doc = Jsoup.parse(getHTML());
        table = doc.getElementById("ctl00_ContentPlaceHolder1_gridCalendar_ctl00");
        rows = table.select("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements columns = row.select("td");

            String name = null;
            String date = null;
            String time = null;
            String location = null;
            for (int j = 0; j < columns.size(); j++) {
                if (j == 0) name = columns.get(j).text();
                if (j == 1) date = columns.get(j).text();
                if (i == 2) time = columns.get(j).text();
                if (i == 4) location = columns.get(j).text();
                if (j == 5) {
                    //get link and then send to webdrivwer
//                    System.out.println(time);

                    Element atag = columns.get(j).select("a").first();
//                    System.out.println(atag.attr("href"));
                    String urle = atag.attr("href");
//                    System.out.println(url);
                    if (atag.attr("href").equals("") || !atag.attr("href").startsWith("MeetingDetail")) {
//                        //  System.out.println("no adj, defaulting to inserting regularly");
                        insertEventNOTNULL(columns.get(0).text(), columns.get(1).text(), columns.get(3).text(), columns.get(4).text());
                    } else {
                        click(atag.attr("href"));
                    }
                }

            }
        }


    }

    private static String getHTML() throws InterruptedException {
        String html = "";

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe");
        // Setting new download directory path
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Use File.separator as it will work on any OS
        prefs.put("download.default_directory",
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "tmp");
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--ignore-certificate-errors");

        options.setExperimentalOption("prefs", prefs);
        // Launching browser with desired capabilities
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAssumeUntrustedCertificateIssuer(false);
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://hayward.legistar.com/");
        String downloadFilepath = "C:\\Users\\alex\\Documents\\Programming\\pilotcity-first\\java\\src\\main\\tmp";
        profile.setPreference("browser.download.dir", downloadFilepath);
        profile.setPreference("browser.helperapps.neverAsk.saveToDisk", "application/xls");
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_lstYears_Input\"]"))).click();
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[1]/div/div/ul/li[19]"))).click();
            html = driver.getPageSource();
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
        TimeUnit.SECONDS.sleep(2);
        driver.close();
        return html;
    }

    public String test() throws InterruptedException {
        return (getHTML());
    }
}
