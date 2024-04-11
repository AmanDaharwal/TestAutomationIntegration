package com.reporters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.exceptions.TestExecutionFailedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtendTestReporter {
    private static ExtendTestReporter extendTestReporter;
    private final ExtentReports extentReports;
    private ExtentSparkReporter extentSparkReporter;
    private final String extentReportDirAndName;

    private ExtendTestReporter() {
        extentReports = new ExtentReports();
        extentReportDirAndName = "/ExtendReport/ExtendReport.html";
    }

    public static ExtendTestReporter getExtendTestReporter() {
        if (extendTestReporter == null) {
            extendTestReporter = new ExtendTestReporter();
        }
        return extendTestReporter;
    }

    public void loadReporter(String pathOfOutputDirectory) {
        Path extendReportConfigPath = Paths.get("src/main/resources/extend_report_config.json");
        extentSparkReporter = new ExtentSparkReporter(pathOfOutputDirectory + extentReportDirAndName);
        extentSparkReporter.viewConfigurer().viewOrder()
                .as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST})
                .apply();
        try {
            extentSparkReporter.loadJSONConfig(new File(String.valueOf(extendReportConfigPath)));
        } catch (IOException e) {
            throw new TestExecutionFailedException("Unable to load extend report config file at "
                    + extendReportConfigPath);
        }
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

    public String getExtentReportPath(){
        return extentReportDirAndName;
    }
}
