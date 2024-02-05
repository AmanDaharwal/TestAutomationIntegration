package com.listeners;

import com.context.TestExecutionContext;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class CucumberPlatformScenarioListener implements ConcurrentEventListener {

    public CucumberPlatformScenarioListener(){
        System.out.println("Inside CucumberPlatformScenarioListener constructor");
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        System.out.println("setEventPublisher");
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::testRunStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this:: testCaseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this:: testCaseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this:: testRunFinishedHandler);
    }

    private void testRunStartedHandler(TestRunStarted event){
        System.out.println("testRunStartedHandler");
    }

    private void testCaseStartedHandler(TestCaseStarted event){
        System.out.println("testCaseStartedHandler");
        String testCaseName = event.getTestCase().getName();
        TestExecutionContext testExecutionContext = new TestExecutionContext(testCaseName);
    }

    private void testCaseFinishedHandler(TestCaseFinished event){
        System.out.println("testCaseFinishedHandler");
    }

    private void testRunFinishedHandler(TestRunFinished event){
        System.out.println("testRunFinishedHandler");
    }
}
