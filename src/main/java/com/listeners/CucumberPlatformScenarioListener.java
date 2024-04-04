package com.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.context.TestExecutionContext;
import com.runner.Drivers;
import io.cucumber.plugin.ConcurrentEventListener;
import com.aventstack.extentreports.Status;
import io.cucumber.plugin.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CucumberPlatformScenarioListener implements ConcurrentEventListener {

    private final static Logger logger = LogManager.getLogger(CucumberPlatformScenarioListener.class);
    private ExtentReports extent;
    private ExtentSparkReporter spark;
    private ExtentTest scenario;

    public CucumberPlatformScenarioListener(){
        logger.info("CucumberPlatformScenarioListener");
        extent = new ExtentReports();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        logger.info("set Event Publisher");
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this:: testCaseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this:: testCaseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this:: testRunFinishedHandler);
    }

    private void testRunStartedHandler(TestRunStarted event){
        logger.info("Test Run Started");
        String pathOfOutputDirectory  = System.getProperty("OUTPUT_DIRECTORY");
        spark = new ExtentSparkReporter(pathOfOutputDirectory + "/ExtendReport/ExtendReport.html");
        extent.attachReporter(spark);
    }

    private void testCaseStartedHandler(TestCaseStarted event){
        String testCaseName = event.getTestCase().getName();
        logger.info("Test Case Started - "+testCaseName);
        TestExecutionContext testExecutionContext = new TestExecutionContext(testCaseName);
        scenario = extent.createTest(testCaseName);
    }

    private void testCaseFinishedHandler(TestCaseFinished event){
        Drivers.closeBrowser();
        String testCaseName = event.getTestCase().getName();
        Status status = Status.PASS;
        if (!event.getResult().getStatus().isOk()) {
            status = Status.FAIL;
        }
        logger.info(status+ "Test Case Finished: " + testCaseName);
        scenario.log(status, "Test Case Finished: " + testCaseName);
    }

    private void testRunFinishedHandler(TestRunFinished event){
        extent.flush();
        logger.info("Test Run Finished Handler");
    }
}
