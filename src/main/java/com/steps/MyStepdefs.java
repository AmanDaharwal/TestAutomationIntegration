package com.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepdefs {

    private final TestExecutionContext testExecutionContext;

    public MyStepdefs(){
        testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());
    }

    @Given("I launch Indigo application")
    public void iLaunchIndigoApplication() {
        testExecutionContext.addTestState(TEST_CONTEXT.BROWSER, "chrome");
    }


    @When("I search for {string} way ticket from {string} to {string} for {string} passenger")
    public void iSearchForWayTicketFromToForPassenger(String arg0, String arg1, String arg2, String arg3) {

        System.out.println("*******************************************");
        System.out.println(testExecutionContext.getTestStateAsString(TEST_CONTEXT.BROWSER));
    }

    @Then("I should get all available flight options")
    public void iShouldGetAllAvailableFlightOptions() {
    }
}
