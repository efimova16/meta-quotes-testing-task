package com.meta_quotes.ta.steps;

import static org.junit.Assume.*;
import static org.assertj.core.api.Assertions.*;

import java.text.ParseException;
import java.util.Calendar;

import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meta_quotes.ta.driver.CustomChromeDriver;
import com.meta_quotes.ta.pages.*;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class UserSteps extends ScenarioSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSteps.class);

    CalendarPage calendarPage = new CalendarPage(CustomChromeDriver.getDriver());
    CalendarControlPanel calendarControlPanel = new CalendarControlPanel(CustomChromeDriver.getDriver());
    CalendarContentPanel calendarContentPanel = new CalendarContentPanel(CustomChromeDriver.getDriver());
    EventContentPanel eventContentPanel = new EventContentPanel(CustomChromeDriver.getDriver());

    @Step
    public void opens_economic_calendar_page() {
        calendarPage.open();
        Serenity.takeScreenshot();
    }

    @Step
    public void filters_results_by_currencies(String... currencies) {
        for (String currency : currencies) {
            if (calendarControlPanel.getCurrenciesList().stream()
                    .filter(el -> el.getText().equals(currency)).findAny().orElse(null) == null) {
                throw new AssertionError("Currency with code value '" + currency
                        + "' isn't presented in filters!");
            }
        }
        calendarControlPanel.selectCurrenciesValue(currencies);
        waitABit(5000);
        Serenity.takeScreenshot();
    }

    @Step
    public void filters_results_by_period(String period) {
        try {
            calendarControlPanel.selectPeriodValue(period);
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Period with value '" + period + "' isn't presented in filters!");
        }
        Serenity.takeScreenshot();
    }

    @Step
    public void filters_results_by_importances(String... eventImportances) {
        for (String importance : eventImportances) {
            if (calendarControlPanel.getEventImportancesList().stream()
                    .filter(el -> el.getText().equals(importance)).findAny().orElse(null) == null) {
                throw new AssertionError("Event importance with value '" + importance
                        + "' isn't presented in filters!");
            }
        }
        calendarControlPanel.selectEventImportancesValue(eventImportances);
        Serenity.takeScreenshot();
    }

    @Step
    public String chooses_event_with_number_of(int eventNumber) {
        // refresh page as WA cause bug:
        // calendar.03913d8aeed2ebdafe3f736d2a98f01f.js:69
        // get calendar error:TypeError: a.blockContent.appendChild is not a
        // function
        CustomChromeDriver.getDriver().navigate().refresh();
        waitABit(5000);
        Serenity.takeScreenshot();
        assumeTrue("Event with number of '" + eventNumber + "' isn't presented in result's page!",
                   calendarContentPanel.getEventsQnt() >= eventNumber);
        Serenity.takeScreenshot();
        return calendarContentPanel.clickOnEventWithNumberOf(eventNumber);
    }

    @Step
    public void should_see_event_importance_as(String importance) {
        Serenity.takeScreenshot();
        assertThat(eventContentPanel.getEventImportance()).isEqualToIgnoringCase(importance);
    }

    @Step
    public void should_see_event_country_as(String country) {
        Serenity.takeScreenshot();
        assertThat(eventContentPanel.getEventCountry()).isEqualTo(country);
    }

    @Step
    public void gets_event_history_log_for_the_last_12_months() throws ParseException {
        eventContentPanel.clickOnHistoryTab();
        Serenity.takeScreenshot();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);
        // paginator's bug:
        // calendar.03913d8aeed2ebdafe3f736d2a98f01f.js:48 get history error:
        // TypeError: Cannot read property 'querySelectorAll' of undefined
        String log = eventContentPanel.getHistoryDataFrom(startDate);
        Serenity.takeScreenshot();
        Serenity.recordReportData().withTitle("Event history log for the last 12 month").andContents(log);
        LOGGER.info(log);
    }

}
