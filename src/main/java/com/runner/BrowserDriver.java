package com.runner;

import com.context.TestExecutionContext;
import com.entities.Browser;
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
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

class BrowserDriver {
    private final static Logger LOGGER = LogManager.getLogger(BrowserDriver.class);

    static Driver createWebDriver(TestExecutionContext context) {
        WebDriver innerDriver;
        Browser browser = Browser.valueOf(TestRunner.getConfig(TEST_CONTEXT.BROWSER).toLowerCase());
        if (Boolean.valueOf(TestRunner.getConfig(TEST_CONTEXT.RUN_IN_CI))) {
            innerDriver = getRemoteWebDriverFor(browser);
        } else {
            innerDriver = getWebDriverFor(browser);
        }
        Driver driver = new Driver(innerDriver);
        context.addTestState(TEST_CONTEXT.INNER_DRIVER, innerDriver);
        context.addTestState(TEST_CONTEXT.DRIVER, driver);
        LOGGER.info("Driver created for test - " + context.getTestName());
        driver.launchUrl();
        return driver;
    }

    private static WebDriver getWebDriverFor(Browser browser) {
        switch (browser) {
            case chrome:
                return new ChromeDriver(getChromeOptions());
            case firefox:
                return new FirefoxDriver(getFirefoxOptions());
            case edge:
                return new EdgeDriver(getEdgeOptions());
        }
        throw new AutomationException("Incorrect browser name provided as " + browser);
    }

    private static WebDriver getRemoteWebDriverFor(Browser browser) {
        String remoteUrl = "http://localhost:4444/wd/hub";
        try {
            switch (browser) {
                case chrome:
                    return new RemoteWebDriver(new URL(remoteUrl), getChromeOptions());
                case firefox:
                    return new RemoteWebDriver(new URL(remoteUrl), getFirefoxOptions());
                case edge:
                    return new RemoteWebDriver(new URL(remoteUrl), getEdgeOptions());
            }
        } catch (MalformedURLException exception) {
            throw new AutomationException("Unable to initialized RemoteWebDriver with url " + remoteUrl, exception);
        }
        throw new AutomationException("Incorrect browser name provided as " + browser);
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        for (Object arg : getWebArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        for (Object arg : getWebArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        for (Object arg : getWebArguments()) {
            options.addArguments(arg.toString());
        }
        return options;
    }

    static void quitDriver(TestExecutionContext context) {
        Driver driver = (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
        try {
            if (TestRunner.getConfig(TEST_CONTEXT.CAPTURE_LOGS).contains("true"))
                driver.captureBrowserLogs();
        } catch (Exception exception) {
            throw new AutomationException(exception.getMessage(), exception);
        } finally {
            driver.quitDriver();
        }
    }

    private static JSONArray getWebArguments() {
        String capabilitiesFiles = TestRunner.getConfig(TEST_CONTEXT.CAPABILITIES);
        String platform = TestRunner.getPlatform().toString();
        try {
            return new JSONObject(new String(Files.readAllBytes(Paths.get(capabilitiesFiles))))
                    .getJSONObject(platform)
                    .getJSONArray("arguments");
        } catch (IOException e) {
            throw new InvalidTestDataException("Unable to read arguments from capabilities file at " + capabilitiesFiles);
        }
    }
}
