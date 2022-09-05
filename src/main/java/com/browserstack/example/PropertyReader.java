package com.browserstack.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Project Name    : testng-browserstack-example
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 5/9/22
 * Time            : 5:32 pm
 * Description     :
 **/

public class PropertyReader {

    public static String getProperty(String filePath, String propertyName) {
        String propertyValue = null;

        try (InputStream input = Files.newInputStream(Paths.get(System.getProperty("user.dir") + filePath))) {
            Properties prop = new Properties();
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return propertyValue;
    }
}
