package com.hayward.spring.events;

import com.hayward.spring.events.EventScraper.CleanDB;
import com.hayward.spring.events.EventScraper.MeetingMinutesFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
//rest controller

/**
 * @author Alexander Mehta
 */
@RestController
@AllArgsConstructor
public class Rest {

    /**
     * @return String
     * (returns a string to the post request,
     * but this is just a callback in order to prove that the
     * request was created and did not fail).What it really does is inserts
     * pdf data into database.
     * @throws IOException          throws an exception if pdfs can't be parsed
     * @throws InterruptedException this throws when selenium fails to click because it has been interrupted
     * @throws SQLException         throws sql exception if the statement fails (for example the database isn't turned on)
     * @throws ParseException       throws an exception if pdfs can't be parsed
     * @see CleanDB cleans the database by trunication
     */
    @PostMapping("/update")
    public String getvalues() throws InterruptedException, SQLException, ParseException, IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("properties.properties"));
        CleanDB cleaner = new CleanDB();
        cleaner.cleanDB();
        System.err.println("Cleaned DB");
        String calendar = p.getProperty("baseurl") + "/Calendar.aspx";
        MeetingMinutesFactory g = new MeetingMinutesFactory();
        g.parseTable(calendar);
        FileUtils.cleanDirectory(new File("src/main/tmp"));
        return "done";
    }


}
