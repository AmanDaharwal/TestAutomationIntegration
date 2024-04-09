package com.reporters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.entities.TEST_CONTEXT;
import com.runner.TestRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtendTestLogger {
    private final static Logger LOGGER = LogManager.getLogger(ExtendTestLogger.class.getSimpleName());

    public static void logInfoMessage(String message) {
        LOGGER.info(message);
        getExtendTest().info(message);

    }

    public static void logWarningMessage(String message) {
        LOGGER.warn(message);
        getExtendTest().warning(message);
    }

    public static void logFailMessage(String message) {
        getExtendTest().fail(message);
    }

    static void logStatusMessage(Status status, String message) {
        getExtendTest().log(status, message);
    }

    private static ExtentTest getExtendTest() {
        return ((ExtentTest) (TestRunner.getTestExecutionContext(Thread.currentThread().getId()).getTestState(TEST_CONTEXT.EXTEND_TEST)));
    }
}
