/*
 */

package com.hayward.spring.events.EventScraper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

//these are the default options for our scraper
public class DefaultOptions implements ChromeOptions {
    //sets the chrome settings and implements the ChromeOptions contract, which requires a option return
    @Override
    public org.openqa.selenium.chrome.ChromeOptions setOptions() {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Use File.separator as it will work on any OS
        prefs.put("download.default_directory",
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "tmp");
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
        return options;
    }
}
