package com.browserstack.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import static com.browserstack.example.BrowserStackService.updateTestStatus;
import static com.browserstack.example.Driver.getDriver;
import static com.browserstack.example.Driver.spinUpDriver;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 11:51 am
 * Description     :
 **/

@Listeners(ExtentReportListener.class)
public class BaseTest {
    public AndroidDriver<AndroidElement> driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters(value = {"deviceIndex"})
    public void setUp(String deviceIndex) {
        spinUpDriver(deviceIndex);
        driver = getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult iTestResult) {
        updateTestStatus(driver.getSessionId(), iTestResult);
        driver.quit();
    }
}
