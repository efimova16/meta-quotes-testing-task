package com.meta_quotes.ta.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import static org.hamcrest.Matchers.*;

public class RestSteps extends ScenarioSteps {

    private final static String BASE_URI = "https://lst.to";
    private final static Map<String, String> HEADERS = new HashMap<String, String>();

    private static final Logger LOGGER = LoggerFactory.getLogger(RestSteps.class);

    static {
        HEADERS.put("Content-Type", "application/json");
        HEADERS.put("X-AUTH-TOKEN", "3540c52ddd56911600f5454e");
        HEADERS.put("user-agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
    }

    @Step
    public String shortens_link(Map<String, Object> params) {
        Map<String, Object> bodyAsMap = new HashMap<>();
        bodyAsMap.put("data", params);
        ValidatableResponse response = SerenityRest.given().headers(HEADERS).baseUri(BASE_URI)
                                                   .basePath("api/v1/link").body(bodyAsMap)
                                                   .when().post().then().log().all().statusCode(200)
                                                   .and().contentType(ContentType.JSON);
        String shortLink = response.extract().path("data.short");
        Serenity.recordReportData().withTitle("Short link").andContents(shortLink);
        LOGGER.info("#####Short link '" + shortLink + "'#####");
        Pattern p = Pattern.compile(BASE_URI + "/(.+)");
        Matcher m = p.matcher(shortLink);
        if (m.find()) {
            shortLink = m.group(1);
        } else {
            throw new AssertionError("There is incorrect format of short link '" + shortLink +
                                     "' in response!");
        }
        return shortLink;
    }

    @Step
    public void deletes_short_link(String shortLink) {
        SerenityRest.given().headers(HEADERS).baseUri(BASE_URI).when()
                    .delete("api/v1/link/{shortLink}", shortLink).then().log().all().statusCode(200);
    }

    @Step
    public void short_link_should_be_available(String shortLink) {
        SerenityRest.given().headers(HEADERS).baseUri(BASE_URI).when()
                    .get("api/v1/link/{shortLink}", shortLink).then().log().all().statusCode(200);
    }

    @Step
    public void short_link_should_not_be_available(String shortLink) {
        SerenityRest.given().headers(HEADERS).baseUri(BASE_URI).when().get("api/v1/link")
                    .then().log().all().statusCode(200).and().contentType(ContentType.JSON)
                    .body("data.short", not(contains(shortLink)));
    }

}
