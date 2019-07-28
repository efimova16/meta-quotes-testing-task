package com.meta_quotes.ta.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class EventContentPanel extends PageObject {

    public EventContentPanel(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#eventContentPanel>.economic-calendar__event .event-table__importance")
    WebElement eventImportanceTr;

    @FindBy(xpath = "//span[contains(.,'Country:')]/following-sibling::div/a")
    WebElement eventCountryTr;

    @FindBy(xpath = "//*[@id='calendar-tabs']/li[contains(.,'History')]")
    WebElement historyTab;

    @FindBy(css = ".paginatorEx")
    WebElement paginator;

    public String getEventImportance() {
        return eventImportanceTr.getText();
    }

    public String getEventCountry() {
        return eventCountryTr.getText();
    }

    public void clickOnHistoryTab() {
        historyTab.click();
    }

    public String getHistoryDataFrom(Calendar startDate) throws ParseException {
        StringBuilder log = new StringBuilder("############## Event history log ##############" + System.lineSeparator());
        String date;
        String actual;
        String forecast;
        String previous;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Calendar dateAsCal = Calendar.getInstance();

        evaluateJavascript("arguments[0].scrollIntoView();", paginator);
        List<WebElement> pages = paginator.findElements(By.tagName("a"));
        int i;
        for (i = 0; i < pages.size(); i++) {
            for (WebElementFacade item : findAll(".event-table-history__item")) {
                date = item.then(".event-table-history__date").getText().trim();
                dateAsCal.setTime(sdf.parse(date));
                if (startDate.before(dateAsCal)) {
                    actual = item.then(".event-table-history__actual").getText().trim();
                    forecast = item.then(".event-table-history__forecast").getText().trim();
                    previous = item.then(".event-table-history__previous").getText().trim();
                    log.append("| Date | Actual | Forecast | Previous |" + System.lineSeparator()
                            + "| " + date + " | " + actual + " | " + forecast + " | " + previous
                            + " |" + System.lineSeparator());
                }
            }
            i++;
            if (i != pages.size()) {
                pages.get(i).click();
            }
        }
        return log.toString();
    }

}
