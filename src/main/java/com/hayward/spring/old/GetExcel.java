package com.hayward.spring.old;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.ApiStatus;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Deprecated
@ApiStatus.ScheduledForRemoval
public class GetExcel {
    @Deprecated
    @ApiStatus.ScheduledForRemoval()
    public static void main(String[] args) throws InterruptedException, IOException, SQLException {
        FileUtils.cleanDirectory(new File("src/main/tmp"));
        GetExcel getExcel1 = new GetExcel();
        getExcel1.DownloadXLS();
        getExcel1.readInCSVFormat(new File("src\\main\\tmp\\Export.html"));
        ReaderClass reader = new ReaderClass();
        int f = reader.RW(new File("src\\main\\tmp\\File.txt"));
    }
    @Deprecated
    @ApiStatus.ScheduledForRemoval()
    public static void santaize() throws IOException {
        String name = "src/main/tmp/File.txt";
        List<String> lines = FileUtils.readLines(new File(name));
        lines.removeIf(line -> line.trim().isEmpty());
        FileUtils.writeLines(new File(name), lines);
        FileUtils.forceDelete(new File("src/main/tmp/Export.html"));
    }
    @Deprecated
    @ApiStatus.ScheduledForRemoval()
    public void readInCSVFormat(File file) throws IOException {
        PrintWriter fout = new PrintWriter("src/main/tmp/File.txt");
        Document doc = Jsoup.parse(file, null);
        Element table = doc.body();
        Elements rows = table.select("tr");
        Elements ths = rows.select("td");
        String thstr = "";

        for (Element row : rows) {
            Elements tds = row.select("td");
            for (Element td : tds) {
                fout.println(td.text());  // --> This will print them
                // individually
            }
            // in the row
        }
        fout.close();
        santaize();

    }
    @Deprecated
    @ApiStatus.ScheduledForRemoval()
    public void DownloadXLS() throws InterruptedException {
        //https://hayward.legistar.com/Calendar.aspx
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
        System.out.println(options.getExperimentalOption("prefs"));
        // Launching browser with desired capabilities
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAssumeUntrustedCertificateIssuer(false);
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("https://hayward.legistar.com/Calendar.aspx");
        String downloadFilepath = "C:\\Users\\alex\\Documents\\Programming\\pilotcity-first\\java\\src\\main\\tmp";
        profile.setPreference("browser.download.dir", downloadFilepath);
        profile.setPreference("browser.helperapps.neverAsk.saveToDisk", "application/xls");
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[3]/div[6]/div/div/div[6]/div[1]/div/table[3]/tbody/tr/td/div/ul/li[3]"))).click();
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[3]/div[6]/div/div/div[6]/div[1]/div/table[3]/tbody/tr/td/div/ul/li[3]/div/ul/li[1]/a"))).click();
        TimeUnit.SECONDS.sleep(2);
        driver.close();
        File export = new File("src/main/tmp/Export.xls");
        File newname = new File("src/main/tmp/Export.html");

        if (export.renameTo(newname)) {
            System.out.println("renamed");
        } else {
            System.out.println("error");
        }
    }
}
