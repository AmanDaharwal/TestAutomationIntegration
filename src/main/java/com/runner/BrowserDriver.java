package com.runner;

import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.exceptions.AutomationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
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
}
