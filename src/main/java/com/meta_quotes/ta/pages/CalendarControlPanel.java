package com.meta_quotes.ta.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class CalendarControlPanel extends PageObject {

    public CalendarControlPanel(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='economicCalendarFilterImportance']/li/label")
    List<WebElement> eventImportancesList;

    @FindBy(css = "#economicCalendarFilterImportance input[type=checkbox]:checked + label")
    List<WebElement> checkedEventImportancesList;

    @FindBy(css = "#economicCalendarFilterImportance input[type=checkbox]:not(:checked) + label")
    List<WebElement> uncheckedEventImportancesList;

    @FindBy(xpath = "//*[@id='economicCalendarFilterCurrency']/li/label")
    List<WebElement> currenciesList;

    @FindBy(id = "economicCalendarFilterCurrency")
    WebElement currenciesFilter;

    @FindBy(css = "#economicCalendarFilterCurrency input[type=checkbox]:checked + label")
    List<WebElement> checkedCurrenciesList;

    @FindBy(css = "#economicCalendarFilterCurrency input[type=checkbox]:not(:checked) + label")
    List<WebElement> uncheckedCurrenciesList;

    @FindBy(id = "economicCalendarFilterDate")
    WebElement periodsList;

    public void selectPeriodValue(String period) {
            periodsList.findElement(By.xpath(".//li[contains(.,'" + period + "')]")).click();
    }

    public void selectCurrenciesValue(String... currencies) {
        ArrayList<String> currenciesAsList = new ArrayList<String>(Arrays.asList(currencies));
        uncheckedCurrenciesList.stream().filter(el -> currenciesAsList.contains(el.getText()))
                .forEach(el -> el.click());
        checkedCurrenciesList.stream().filter(el -> !currenciesAsList.contains(el.getText()))
                .forEach(el -> el.click());
    }

    public void selectEventImportancesValue(String... eventImportances) {
        ArrayList<String> eventImportancesAsList = new ArrayList<String>(Arrays.asList(eventImportances));
        uncheckedEventImportancesList.stream()
                .filter(el -> eventImportancesAsList.contains(el.getText()))
                .forEach(el -> el.click());
        checkedEventImportancesList.stream()
                .filter(el -> !eventImportancesAsList.contains(el.getText()))
                .forEach(el -> el.click());
    }

    public List<WebElement> getEventImportancesList() {
        return eventImportancesList;
    }

    public List<WebElement> getCurrenciesList() {
        evaluateJavascript("arguments[0].scrollIntoView();", currenciesFilter);
        return currenciesList;
    }

}
