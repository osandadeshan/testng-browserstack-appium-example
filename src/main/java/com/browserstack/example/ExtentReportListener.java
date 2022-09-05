package com.browserstack.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.browserstack.example.Driver.getDriver;
import static com.browserstack.example.PropertyReader.getProperty;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 12:14 pm
 * Description     :
 **/

public class ExtentReportListener implements ITestListener {

    private static final String extentReportDirectory = System.getProperty("user.dir") + "/reports/html-report";
    private static final String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    private ExtentReports extent;
    private ExtentTest test;

    private static String takeScreenshot(String screenshotName) {
        TakesScreenshot takesScreenshot = getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = extentReportDirectory + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
        String relativePath = "screenshots/" + screenshotName + "_" + timestamp + ".png";
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativePath;
    }

    private static String getExtentReportProperty(String propertyName) {
        return getProperty("/src/test/resources/extent.properties", propertyName);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentReportDirectory +
                "/Execution_Results_" + timestamp + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        try {
            if (getExtentReportProperty("extent_reporter_theme").equalsIgnoreCase("dark"))
                htmlReporter.config().setTheme(Theme.DARK);
            else
                htmlReporter.config().setTheme(Theme.STANDARD);

            htmlReporter.config().setDocumentTitle(getExtentReportProperty("extent_document_title"));
            htmlReporter.config().setReportName(getExtentReportProperty("extent_reporter_name"));

            extent.setSystemInfo("Application Name", getExtentReportProperty("application_name"));
            extent.setSystemInfo("Environment", getExtentReportProperty("environment"));
            extent.setSystemInfo("Browser", getExtentReportProperty("browser"));
            extent.setSystemInfo("Operating System", getExtentReportProperty("operating_system"));
            extent.setSystemInfo("Test Developer", getExtentReportProperty("test_developer"));
        } catch (Exception ex) {
            htmlReporter.config().setTheme(Theme.DARK);
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = extent.createTest(iTestResult.getName(), iTestResult.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        test.log(Status.PASS, MarkupHelper.createLabel(iTestResult.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        test.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getName(), ExtentColor.RED));
        test.log(Status.FAIL, MarkupHelper.createLabel(iTestResult.getThrowable().getMessage(), ExtentColor.RED));
        test.log(Status.FAIL, MarkupHelper.createLabel(Arrays.toString(iTestResult.getThrowable().getStackTrace()), ExtentColor.RED));

        if (Boolean.parseBoolean(getExtentReportProperty("capture_screenshot_on_failure"))) {
            try {
                test.fail("Screenshot at the failed moment is below.");
                test.addScreenCaptureFromPath(takeScreenshot(iTestResult.getName()), iTestResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        test.log(Status.SKIP, MarkupHelper.createLabel(iTestResult.getName(), ExtentColor.GREY));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extent.flush();
    }
}
