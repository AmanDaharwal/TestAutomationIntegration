package com.listeners;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.reporters.ExtendTestReporter;
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
    private ExtendTestReporter extendTestReporter;
    private final Map<String, Integer> scenarioRunCounts = new HashMap<>();

    public CucumberPlatformScenarioListener() {
        LOGGER.info("CucumberPlatformScenarioListener");
        extendTestReporter = ExtendTestReporter.getExtendTestReporter();
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
        String pathOfOutputDirectory = System.getProperty("OUTPUT_DIRECTORY");
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
        Drivers.closeBrowser(context);
        String testCaseName = event.getTestCase().getName();
        Status status = Status.PASS;
        if (!event.getResult().getStatus().isOk()) {
            status = Status.FAIL;
        }
        LOGGER.info(status + "Test Case Finished: " + testCaseName);
        extendTestReporter.logTestExecutionStatus(status, testCaseName);
        SessionContext.removeContext(threadId);
    }

    private void testRunFinishedHandler(TestRunFinished event) {
        extendTestReporter.writeReport();
        LOGGER.info("Test Run Finished Handler");
    }

    private Integer getScenarioRunCount(String scenarioName) {
        scenarioRunCounts.put(scenarioName, scenarioRunCounts.getOrDefault(scenarioName, 1));
        return scenarioRunCounts.get(scenarioName);
    }
}
