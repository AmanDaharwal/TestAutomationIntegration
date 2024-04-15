package com.runner;

import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.exceptions.AutomationException;
import com.exceptions.InvalidTestDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class BrowserDriver {
    private final static Logger LOGGER = LogManager.getLogger(BrowserDriver.class);

    static Driver createWebDriver(TestExecutionContext context) {
        WebDriver innerDriver = null;
        switch (TestRunner.getConfig("browser")) {
            case "chrome":
                innerDriver = new ChromeDriver(getChromeOptions());
                break;
            case "firefox":
                innerDriver = new FirefoxDriver(getFirefoxOptions());
                break;
            case "edge":
                innerDriver = new EdgeDriver(getEdgeOptions());
                break;
        }
        Driver driver = new Driver(innerDriver);
        context.addTestState(TEST_CONTEXT.INNER_DRIVER, innerDriver);
        context.addTestState(TEST_CONTEXT.DRIVER, driver);
        LOGGER.info("Driver created for test - " + context.getTestName());
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        for (Object arg : getArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        for (Object arg : getArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        for (Object arg : getArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    static void startApplication(TestExecutionContext context) {
        Driver driver = (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
        driver.launchUrl();
    }

    static void quitDriver(TestExecutionContext context) {
        Driver driver = (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
        try {
            if (TestRunner.getConfig(TEST_CONTEXT.CAPTURE_LOGS).contains("true"))
                driver.captureBrowserLogs();
        } catch (Exception exception) {
            throw new AutomationException(exception.getMessage(), exception);
        } finally {
            driver.closeBrowser();
        }
    }

    private static JSONArray getArguments() {
        String capabilitiesFiles = TestRunner.getConfig(TEST_CONTEXT.CAPABILITIES);
        String platform = TestRunner.getPlatform();
        try {
            return new JSONObject(new String(Files.readAllBytes(Paths.get(capabilitiesFiles))))
                    .getJSONObject(platform)
                    .getJSONArray("arguments");
        } catch (IOException e) {
            throw new InvalidTestDataException("Unable to read arguments from capabilities file at " + capabilitiesFiles);
        }
    }
}
