package com.hayward.spring.events;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.hayward.spring.events.GetMeetingMinutes.cleanDB;
import static com.hayward.spring.events.GetMeetingMinutes.parseTable;
//rest controller
@RestController
public class Rest {
    //clean database by trunication, get calendar, parse all of the events and
    @PostMapping("/update")
    public String getvalues() throws IOException, InterruptedException, SQLException, ParseException {
        cleanDB();
        System.out.println("Cleaned DB");
        parseTable(8, "https://hayward.legistar.com/Calendar.aspx");
        FileUtils.cleanDirectory(new File("src/main/tmp"));
        //this is the worst way to do this, but it must be done to get this part over with
        for (int i = 0; i < 10000; i++) {
            PDFreader pdFreader = new PDFreader();
            pdFreader.attemptZoomLink(i);
        }
        return "Complete: inserted values";
    }
    @PostMapping("/update/zoom")
    public String getZoom(){
        //this is the worst way to do this, but it must be done to get this part over with
        //what it ends up having to do is parsing every id up to 10000, in the future, it is better to do this in any other way
        for (int i = 0; i < 10000; i++) {
            PDFreader pdFreader = new PDFreader();
            pdFreader.attemptZoomLink(i);
        }
        return "done";
    }
    @PostMapping("/test")
    public String testing() throws IOException, InterruptedException, SQLException, ParseException {
        GetMeetingMinutes getMeetingMinutes = new GetMeetingMinutes();
        return getMeetingMinutes.test();
    }
    @PostMapping("/test2")
    public String test2(){
        PDFreader pdFreader = new PDFreader();
        pdFreader.attemptZoomLink(52);

        return "52";
    }

}
