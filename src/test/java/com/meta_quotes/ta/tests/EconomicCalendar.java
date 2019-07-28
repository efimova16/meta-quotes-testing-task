package com.meta_quotes.ta.tests;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.meta_quotes.ta.driver.CustomChromeDriver;
import com.meta_quotes.ta.steps.RestSteps;
import com.meta_quotes.ta.steps.UserSteps;

@RunWith(SerenityRunner.class)
public class EconomicCalendar {

    @Steps
    public UserSteps user;

    @Steps
    public RestSteps rest;

    @Test
    public void shouldSeeEventBasedOnChosenCriteriaThenShortensEventLink() throws ParseException {
        String eventImportance = "Medium";
        user.opens_economic_calendar_page();
        user.filters_results_by_period("Current month");
        user.filters_results_by_importances(eventImportance);
        user.filters_results_by_currencies("CHF - Swiss frank");
        String eventUrl = user.chooses_event_with_number_of(1);
        user.should_see_event_importance_as(eventImportance);
        user.should_see_event_country_as("Switzerland");
        user.gets_event_history_log_for_the_last_12_months();

        Map<String, Object> params = new HashMap<>();
        params.put("type", "link");
        params.put("url", eventUrl);
        params.put("utm", "utm_campaign=test_task");
        Map<String, Object> deeplinkParams = new HashMap<>();
        Map<String, Object> deeplinkIosParams = new HashMap<>();
        deeplinkIosParams.put("deeplink_url", "app://www.example.com");
        deeplinkIosParams.put("fallback_url", "itms-apps://www.example.com");
        deeplinkParams.put("ios", deeplinkIosParams);
        params.put("deeplink", deeplinkParams);
        String shortLink = rest.shortens_link(params);
        rest.short_link_should_be_available(shortLink);
        rest.deletes_short_link(shortLink);
        rest.short_link_should_not_be_available(shortLink);
    }

    @AfterClass
    public static void tearDown() {
        CustomChromeDriver.getDriver().close();
    }

}
