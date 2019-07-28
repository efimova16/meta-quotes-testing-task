package com.meta_quotes.ta.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.meta_quotes.ta.driver.CustomChromeDriver;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class CalendarContentPanel extends PageObject {

    public CalendarContentPanel(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".ec-table__item")
    List<WebElement> eventsTr;

    public String clickOnEventWithNumberOf(int eventNumber) {
        eventsTr.get(eventNumber - 1).findElement(By.tagName("a")).click();
        return CustomChromeDriver.getDriver().getCurrentUrl();
    }

    public int getEventsQnt() {
        int eventQnt = 0;
        try {
            eventQnt = eventsTr.size();
        } catch (NoSuchElementException ex) {}
        return eventQnt;
    }

}
