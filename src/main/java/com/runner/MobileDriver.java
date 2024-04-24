package com.runner;

import com.context.TestExecutionContext;
import com.entities.Platform;
import com.entities.TEST_CONTEXT;
import com.exceptions.AutomationException;
import com.exceptions.TestExecutionFailedException;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

class MobileDriver {
    private final static Logger LOGGER = LogManager.getLogger(MobileDriver.class);
    private static AppiumDriverLocalService appiumDriverLocalService;
    private final static JSONObject MOBILE_CAPS = getMobileCapabilities();

    static Driver createMobileDriver(TestExecutionContext context) {
        WebDriver innerDriver;
        Platform platform = TestRunner.getPlatform();
        if (Boolean.parseBoolean(TestRunner.getConfig(TEST_CONTEXT.RUN_IN_CI))) {
            innerDriver = setUpRemoteDriverFor(platform);
        } else {
            innerDriver = setUpDriverFor(platform);
        }
        Driver driver = new Driver(innerDriver);
        context.addTestState(TEST_CONTEXT.INNER_DRIVER, innerDriver);
        context.addTestState(TEST_CONTEXT.DRIVER, driver);
        LOGGER.info(platform + " Driver created for test - " + context.getTestName());
        return driver;
    }

    private static WebDriver setUpRemoteDriverFor(Platform platform) {
        switch (platform) {
            case android:
                return setUpRemoteAndroidDriver();
            case ios:
                return setUpRemoteIosDriverFor();
        }
        throw new AutomationException("Incorrect platform name for mobile provided as " + platform);
    }

    private static WebDriver setUpRemoteIosDriverFor() {
        try {
            return new IOSDriver(new URL(Drivers.getRemoteUrl()), getRemoteIosOptions());
        } catch (MalformedURLException e) {
            throw new AutomationException("Unable to start Remote iOS Driver with exception " + e.getMessage());
        }
    }

    private static XCUITestOptions getRemoteIosOptions() {
        XCUITestOptions options = new XCUITestOptions();
        String platformName = options.getPlatformName().toString().toLowerCase();
        JSONObject browserStackCaps = MOBILE_CAPS.getJSONObject(platformName).getJSONObject(TEST_CONTEXT.BROWSER_STACK);
        options.setApp(browserStackCaps.getString(TEST_CONTEXT.APP));
        options.setDeviceName(browserStackCaps.getString(TEST_CONTEXT.DEVICE_NAME));
        options.setPlatformName(browserStackCaps.getString(TEST_CONTEXT.PLATFORM_NAME));
        options.setCapability(TEST_CONTEXT.PROJECT_NAME, browserStackCaps.getString(TEST_CONTEXT.PROJECT_NAME));
        options.setCapability(TEST_CONTEXT.BUILD_NAME, browserStackCaps.getString(TEST_CONTEXT.BUILD_NAME));
        options.setCapability(TEST_CONTEXT.APPIUM_VERSION, browserStackCaps.getString(TEST_CONTEXT.APPIUM_VERSION));
        return options;
    }

    private static WebDriver setUpRemoteAndroidDriver() {
        try {
            return new AndroidDriver(new URL(Drivers.getRemoteUrl()), getRemoteAndroidOptions());
        } catch (MalformedURLException e) {
            throw new AutomationException("Unable to start Remote Android Driver with exception " + e.getMessage());
        }
    }

    private static UiAutomator2Options getRemoteAndroidOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        String platformName = options.getPlatformName().toString().toLowerCase();
        JSONObject browserStackCaps = MOBILE_CAPS.getJSONObject(platformName).getJSONObject(TEST_CONTEXT.BROWSER_STACK);
        options.setApp(browserStackCaps.getString(TEST_CONTEXT.APP));
        options.setDeviceName(browserStackCaps.getString(TEST_CONTEXT.DEVICE_NAME));
        options.setPlatformName(browserStackCaps.getString(TEST_CONTEXT.PLATFORM_NAME));
        options.setCapability(TEST_CONTEXT.PROJECT_NAME, browserStackCaps.getString(TEST_CONTEXT.PROJECT_NAME));
        options.setCapability(TEST_CONTEXT.BUILD_NAME, browserStackCaps.getString(TEST_CONTEXT.BUILD_NAME));
        options.setCapability(TEST_CONTEXT.APPIUM_VERSION, browserStackCaps.getString(TEST_CONTEXT.APPIUM_VERSION));
        return options;
    }

    private static WebDriver setUpDriverFor(Platform platform) {
        startAppiumServer();
        switch (platform) {
            case android:
                return setUpAndroidDriver();
            case ios:
                return setUpiOSDriver();
        }
        throw new AutomationException("Incorrect platform name for mobile provided as " + platform);
    }

    private static WebDriver setUpiOSDriver() {
        try {
            return new IOSDriver(new URL(MOBILE_CAPS.getString(TEST_CONTEXT.REMOTE_ADDRESS)), getIosOptions());
        } catch (MalformedURLException e) {
            throw new AutomationException("Unable to start iOS Driver with exception " + e.getMessage());
        }
    }

    private static XCUITestOptions getIosOptions() {
        XCUITestOptions options = new XCUITestOptions();
        String platformName = options.getPlatformName().toString().toLowerCase();
        JSONObject iosCaps = MOBILE_CAPS.getJSONObject(platformName);
        options.setDeviceName(iosCaps.getString(TEST_CONTEXT.DEVICE_NAME));
        options.setApp(System.getProperty("user.dir") + iosCaps.getString(TEST_CONTEXT.APP_PATH));
        return options;
    }

    private static WebDriver setUpAndroidDriver() {
        try {
            return new AndroidDriver(new URL(MOBILE_CAPS.getString(TEST_CONTEXT.REMOTE_ADDRESS)), getAndroidOptions());
        } catch (MalformedURLException e) {
            throw new AutomationException("Unable to start Android Driver with exception " + e.getMessage());
        }
    }

    private static UiAutomator2Options getAndroidOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        String platformName = options.getPlatformName().toString().toLowerCase();
        JSONObject androidCaps = MOBILE_CAPS.getJSONObject(platformName);
        options.setDeviceName(androidCaps.getString(TEST_CONTEXT.DEVICE_NAME));
        options.setApp(System.getProperty("user.dir") + androidCaps.getString(TEST_CONTEXT.APP_PATH));
        options.setAppActivity(androidCaps.getString(TEST_CONTEXT.APP_PACKAGE));
        return options;
    }

    private static void startAppiumServer() {
        String appiumServerPath = getAppiumServerPath();
        if (appiumServerPath != null) {
            AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder().withAppiumJS(new File(appiumServerPath));
            appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
            appiumDriverLocalService.start();
            LOGGER.info("Appium server started.");
        } else {
            LOGGER.error("Appium server executable not found. Unable to start Appium server.");
        }
    }

    static void stopAppiumServer(TestExecutionContext context) {
        Driver driver = (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
        driver.quitDriver();
        if (appiumDriverLocalService != null && appiumDriverLocalService.isRunning()) {
            appiumDriverLocalService.stop();
        }
    }

    private static String getAppiumServerPath() {
        String command = "which appium";
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            command = "where appium";
        }
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                return line.trim();
            }
        } catch (IOException e) {
            throw new TestExecutionFailedException("Exception in getting Appium server path with message " + e.getMessage());
        }
        throw new TestExecutionFailedException("Unable to retrieve Appium Server Path");
    }

    private static JSONObject getMobileCapabilities() {
        String capabilitiesFilePath = TestRunner.getConfig(TEST_CONTEXT.CAPABILITIES);
        try {
            return new JSONObject(new String(Files.readAllBytes(Paths.get(capabilitiesFilePath))))
                    .getJSONObject("mobile");
        } catch (IOException e) {
            throw new AutomationException("Unable to get read mobile capabilities with exception " + e);
        }
    }
}
