package com.runner;

import com.context.TestExecutionContext;
import com.entities.Platform;
import com.entities.TEST_CONTEXT;
import com.exceptions.AutomationException;
import com.exceptions.InvalidTestDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Drivers {
    private final static Logger LOGGER = LogManager.getLogger(Drivers.class);

    private Drivers() {

    }

    static String getRemoteUrl() {
        if (!Boolean.parseBoolean(TestRunner.getConfig(TEST_CONTEXT.RUN_IN_COULD_PLATFORM))) {
            return "http://localhost:4444/wd/hub";
        }
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        boolean flag = (username == null || username.isEmpty() || accessKey == null || accessKey.isEmpty());
        if (flag) {
            throw new AutomationException("Cannot create remote session. " +
                    "BROWSERSTACK username or access key is null/empty");
        }
        return "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
    }

    public static Driver createDriverFor(TestExecutionContext context) {
        return createDriverFor(TestRunner.getPlatform(), context);
    }

    private static Driver createDriverFor(Platform platform, TestExecutionContext context) {
        LOGGER.info("Create Driver for " + platform + " platform");
        switch (platform) {
            case web:
                return BrowserDriver.createWebDriver(context);
            case android:
            case ios:
                return MobileDriver.createMobileDriver(context);
        }
        throw new InvalidTestDataException(platform + " does not exist");
    }

    public static void quitApplication(TestExecutionContext context) {
        Platform platform = TestRunner.getPlatform();
        switch (platform) {
            case web:
                BrowserDriver.quitDriver(context);
            case android:
            case ios:
                MobileDriver.stopAppiumServer(context);
        }
    }

    public static Driver getCurrentDriver(long threadId) {
        TestExecutionContext context = TestRunner.getTestExecutionContext(threadId);
        return (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
    }
}
