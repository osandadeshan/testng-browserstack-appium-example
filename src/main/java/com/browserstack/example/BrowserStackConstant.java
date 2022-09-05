package com.browserstack.example;

import static com.browserstack.example.PropertyReader.getProperty;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 5:38 pm
 * Description     :
 **/

public class BrowserStackConstant {

    public static final String BROWSERSTACK_USERNAME = getBrowserStackProperty("username");
    public static final String BROWSERSTACK_ACCESS_KEY = getBrowserStackProperty("access_key");

    private static String getBrowserStackProperty(String propertyName) {
        return getProperty("/src/test/resources/browserstack/browserstack_credentials.properties", propertyName);
    }
}
