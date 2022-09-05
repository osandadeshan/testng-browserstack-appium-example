package com.browserstack.example;

import io.restassured.http.ContentType;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestResult;

import java.util.HashMap;

import static com.browserstack.example.BrowserStackConstant.BROWSERSTACK_ACCESS_KEY;
import static com.browserstack.example.BrowserStackConstant.BROWSERSTACK_USERNAME;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 4:11 pm
 * Description     :
 **/

public class BrowserStackService {

    public static void updateTestStatus(SessionId sessionId, ITestResult iTestResult) {
        HashMap<String, String> map = new HashMap<>();

        if (iTestResult.getStatus() == ITestResult.SUCCESS) {
            map.put("status", "Passed");
            map.put("reason", "All expected conditions are met.");
        } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
            map.put("status", "Failed");
            map.put("reason", "At least one of the expected conditions is not matched.");
        }

        given()
                .auth()
                .basic(BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(map)
                .put("https://api-cloud.browserstack.com/app-automate/sessions/" + sessionId)
                .then()
                .statusCode(SC_OK);
    }
}
