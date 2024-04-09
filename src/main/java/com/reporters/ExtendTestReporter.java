package com.reporters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;

public class ExtendTestReporter {
    private static ExtendTestReporter extendTestReporter;
    private final ExtentReports extentReports;
    private ExtentSparkReporter extentSparkReporter;

    private ExtendTestReporter() {
        extentReports = new ExtentReports();
    }

    public static ExtendTestReporter getExtendTestReporter() {
        if (extendTestReporter == null) {
            extendTestReporter = new ExtendTestReporter();
        }
        return extendTestReporter;
    }

    public void loadReporter(String pathOfOutputDirectory) {
        extentSparkReporter = new ExtentSparkReporter(pathOfOutputDirectory + "/ExtendReport/ExtendReport.html");
        extentReports.attachReporter(extentSparkReporter);
    }

    public void addTest(String testCaseName, TestExecutionContext context) {
        context.addTestState(TEST_CONTEXT.EXTEND_TEST, extentReports.createTest(testCaseName));
    }

    public void logTestExecutionStatus(Status status, String testCaseName) {
        ExtendTestLogger.logStatusMessage(status, "Test Case Finished: " + testCaseName);
    }

    public void writeReport() {
        extentReports.flush();
    }
}
