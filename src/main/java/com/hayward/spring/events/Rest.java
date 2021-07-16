package com.hayward.spring.events;

import com.hayward.spring.events.EventScraper.CleanDB;
import com.hayward.spring.events.EventScraper.GetMeetingMinutes;
import com.hayward.spring.events.EventScraper.PDFreader;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.hayward.spring.events.EventScraper.GetMeetingMinutes.parseTable;
//rest controller

/**
 * @author Alexander Mehta
 */
@RestController
public class Rest {
    //clean database by truncation, get calendar, parse all of the events and

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
        CleanDB cleaner = new CleanDB();
        cleaner.cleanDB();
        System.err.println("Cleaned DB");
        String calendar = "https://hayward.legistar.com/Calendar.aspx";
        parseTable(calendar);
        FileUtils.cleanDirectory(new File("src/main/tmp"));
        //todo change this from looping 10,000 times to instead finding size of database and searching using that as I.
        for (int i = 0; i < 10000; i++) {
            PDFreader pdFreader = new PDFreader();
            pdFreader.attemptZoomLink(i);
        }
        return "Complete: inserted values";
    }

    /**
     * @return this function will just update zoom links and returns also a done callback
     * @throws PDFreader exceptions
     * @see PDFreader pdfreader class
     */
    @PostMapping("/update/zoom")
    public String getZoom() {
        //what it ends up having to do is parsing every id up to 10000, in the future, it is better to do this in any other way
        //refer to the getValues method comment
        for (int i = 0; i < 10000; i++) {
            PDFreader pdFreader = new PDFreader();
            pdFreader.attemptZoomLink(i);
        }
        return "done";
    }

    //
    @PostMapping("/test")
    public String testing() throws IOException, InterruptedException, SQLException, ParseException {
        GetMeetingMinutes getMeetingMinutes = new GetMeetingMinutes();
        return getMeetingMinutes.test();
    }

    @PostMapping("/test2")
    public String test2() {
        PDFreader pdFreader = new PDFreader();
        pdFreader.attemptZoomLink(52);
        return "52";
    }

}
