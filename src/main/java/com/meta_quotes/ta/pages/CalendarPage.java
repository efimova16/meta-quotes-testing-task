package com.meta_quotes.ta.pages;

import org.openqa.selenium.WebDriver;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.mql5.com/en/economic-calendar")
@At("https://www.mql5.com/en/economic-calendar")
public class CalendarPage extends PageObject {

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

}
