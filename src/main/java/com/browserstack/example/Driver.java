package com.browserstack.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.browserstack.example.BrowserStackConstant.BROWSERSTACK_ACCESS_KEY;
import static com.browserstack.example.BrowserStackConstant.BROWSERSTACK_USERNAME;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 11:51 am
 * Description     :
 **/

public class Driver {
    private static AndroidDriver<AndroidElement> driver;

    public static void spinUpDriver(String deviceIndex) {
        JSONObject config;
        try {
            config = (JSONObject) new JSONParser()
                    .parse(new FileReader("src/test/resources/browserstack/browserstack_conf.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        for (Map.Entry<String, String> pair : ((Map<String, String>) ((JSONArray) config.get("environments"))
                .get(Integer.parseInt(deviceIndex)))
                .entrySet()) {
            capabilities.setCapability(pair.getKey(), pair.getValue());
        }

        for (Map.Entry<String, String> pair : ((Map<String, String>) config.get("capabilities"))
                .entrySet()) {
            if (capabilities.getCapability(pair.getKey()) == null) {
                capabilities.setCapability(pair.getKey(), pair.getValue());
            }
        }

        try {
            driver = new AndroidDriver<>(new URL("http://" + BROWSERSTACK_USERNAME + ":"
                    + BROWSERSTACK_ACCESS_KEY + "@" + config.get("server") + "/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static AndroidDriver<AndroidElement> getDriver() {
        return driver;
    }
}
