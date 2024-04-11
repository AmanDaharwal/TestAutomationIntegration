package com.runner;

import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.exceptions.TestExecutionFailedException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

class MobileDriver {
    private final static Logger LOGGER = LogManager.getLogger(MobileDriver.class);
    private static AppiumDriverLocalService appiumDriverLocalService;

    static Driver createAndroidDriver(TestExecutionContext context) {
        WebDriver innerDriver = setUpDriverFor(TestRunner.getPlatform());
        Driver driver = new Driver(innerDriver);
        context.addTestState(TEST_CONTEXT.INNER_DRIVER, innerDriver);
        context.addTestState(TEST_CONTEXT.DRIVER, driver);
        LOGGER.info("Driver created for test - " + context.getTestName());
        return driver;
    }

    private static WebDriver setUpDriverFor(String platform) {
        startAppiumServer();
        UiAutomator2Options options = getOptions();
        try {
            return new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } catch (MalformedURLException e) {
            throw new TestExecutionFailedException("Unable to start Appium Driver with exception " + e.getMessage());
        }
    }

    private static UiAutomator2Options getOptions() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("sdk_gphone_arm64");
        options.setApp(System.getProperty("user.dir") + "/src/main/resources/apps/Android_SwagLab.apk");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");
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

    static void stopAppiumServer() {
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
}
