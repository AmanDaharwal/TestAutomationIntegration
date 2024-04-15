package com.runner;

import com.context.TestExecutionContext;
import com.entities.Platform;
import com.entities.TEST_CONTEXT;
import com.exceptions.InvalidTestDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Drivers {
    private final static Logger LOGGER = LogManager.getLogger(Drivers.class);

    private Drivers(){

    }

    public static Driver createDriverFor(TestExecutionContext context){
        return createDriverFor(TestRunner.getPlatform(), context);
    }

    private static Driver createDriverFor(Platform platform, TestExecutionContext context){
        switch (platform){
            case web:
                return BrowserDriver.createWebDriver(context);
            case android:
            case ios:
                return MobileDriver.createMobileDriver(context);
        }
        throw new InvalidTestDataException(platform+ " does not exist");
    }

    public static void startApplication(TestExecutionContext context){
        Platform platform = TestRunner.getPlatform();
        switch (platform){
            case web:
                BrowserDriver.startApplication(context);
        }
    }

    public static void quitApplication(TestExecutionContext context){
        Platform platform = TestRunner.getPlatform();
        switch (platform){
            case web:
                BrowserDriver.quitDriver(context);
            case android:
            case ios:
                MobileDriver.stopAppiumServer();
        }
    }

    public static Driver getCurrentDriver(long threadId){
        TestExecutionContext context = TestRunner.getTestExecutionContext(threadId);
        return  (Driver) context.getTestState(TEST_CONTEXT.DRIVER);
    }
}
