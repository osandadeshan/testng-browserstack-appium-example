package com.browserstack.example;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 11:51 am
 * Description     :
 **/

public class SearchTest extends BaseTest {

    @Test
    public void testWikipediaGivesResultsForBrowserStack() {
        AndroidElement searchElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
        searchElement.click();

        AndroidElement insertTextElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
        insertTextElement.sendKeys("BrowserStack");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<AndroidElement> allProductsName = driver.findElementsByClassName("android.widget.TextView");
        Assert.assertTrue(allProductsName.size() > 0);
    }
}
