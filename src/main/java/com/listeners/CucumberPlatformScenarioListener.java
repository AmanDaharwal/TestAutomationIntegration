package com.listeners;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.reporters.ExtendTestLogger;
import com.reporters.ExtendTestReporter;
import com.reporters.ReportPortalReporter;
import com.runner.Drivers;
import io.cucumber.plugin.ConcurrentEventListener;
import com.aventstack.extentreports.Status;
import io.cucumber.plugin.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CucumberPlatformScenarioListener implements ConcurrentEventListener {
    private final static Logger LOGGER = LogManager.getLogger(CucumberPlatformScenarioListener.class);
    private final ExtendTestReporter extendTestReporter;
    private final ReportPortalReporter reportPortalReporter;
    private final Map<String, Integer> scenarioRunCounts = new HashMap<>();
    private final String pathOfOutputDirectory = System.getProperty("OUTPUT_DIRECTORY");

    public CucumberPlatformScenarioListener() {
        LOGGER.info("CucumberPlatformScenarioListener");
        extendTestReporter = ExtendTestReporter.getExtendTestReporter();
        reportPortalReporter = ReportPortalReporter.getReportPortalReporter();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        LOGGER.info("set Event Publisher");
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::testCaseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::testCaseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::testRunFinishedHandler);
    }

    private void testRunStartedHandler(TestRunStarted event) {
        LOGGER.info("Test Run Started");
        extendTestReporter.loadReporter(pathOfOutputDirectory);
    }

    private void testCaseStartedHandler(TestCaseStarted event) {
        String testCaseName = event.getTestCase().getName();
        Integer scenarioRunCount = getScenarioRunCount(testCaseName);
        LOGGER.info("Test Case Started - " + testCaseName);
        TestExecutionContext testExecutionContext = new TestExecutionContext(
                scenarioRunCount + "-" + testCaseName);
        extendTestReporter.addTest(testCaseName, testExecutionContext);
        LOGGER.info(testExecutionContext.getTestName());
    }

    private void testCaseFinishedHandler(TestCaseFinished event) {
        long threadId = Thread.currentThread().getId();
        TestExecutionContext context = SessionContext.getTestExecutionContext(threadId);
        Drivers.quitApplication(context);
        String testCaseName = event.getTestCase().getName();
        Status status = Status.PASS;
        if (!event.getResult().getStatus().isOk()) {
            status = Status.FAIL;
            ExtendTestLogger.logFailMessage(event.getResult().toString());
        }
        LOGGER.info(status + "Test Case Finished: " + testCaseName);
        extendTestReporter.logTestExecutionStatus(status, testCaseName);
        SessionContext.removeContext(threadId);
    }

    private void testRunFinishedHandler(TestRunFinished event) {
        extendTestReporter.writeReport();
        LOGGER.info("Test Run Finished Handler");
        LOGGER.info(System.getProperty("user.dir"));
        LOGGER.info("Test Report Available at : " +
                "file://" + System.getProperty("user.dir") + "//"
                + pathOfOutputDirectory.substring(2)
                + extendTestReporter.getExtentReportPath());
        reportPortalReporter.printReportPortalUrl();
    }

    private Integer getScenarioRunCount(String scenarioName) {
        scenarioRunCounts.put(scenarioName, scenarioRunCounts.getOrDefault(scenarioName, 1));
        return scenarioRunCounts.get(scenarioName);
    }


}
