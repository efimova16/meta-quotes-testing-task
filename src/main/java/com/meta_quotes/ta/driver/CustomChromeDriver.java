package com.meta_quotes.ta.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CustomChromeDriver {

    private final static ChromeDriver CUSTOM_CHROME_DRIVER;

    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--user-agent=Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
        CUSTOM_CHROME_DRIVER = new ChromeDriver(options);
    }

    public static ChromeDriver getDriver() {
        return CUSTOM_CHROME_DRIVER;
    }

}
