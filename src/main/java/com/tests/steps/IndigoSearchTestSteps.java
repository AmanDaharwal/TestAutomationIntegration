package com.tests.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.TEST_CONTEXT;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.bl.IndigoSearchBL;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IndigoSearchTestSteps {

    private final TestExecutionContext testExecutionContext;

    public IndigoSearchTestSteps(){
        testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());
    }

    @Given("I launch Indigo application")
    public void iLaunchIndigoApplication() {
        testExecutionContext.addTestState(TEST_CONTEXT.BROWSER, "chrome");
        Drivers.createDriverFor();
        Drivers.launchUrl();
    }


    @When("I search for {string} way ticket from {string} to {string} for {int} passenger")
    public void iSearchForWayTicketFromToForPassenger(String way, String from, String to, int numberOfPassenger) {

        System.out.println("*******************************************");
        System.out.println(testExecutionContext.getTestStateAsString(TEST_CONTEXT.BROWSER));
        new IndigoSearchBL().searchForFlightTickets(way, from, to , numberOfPassenger);
    }

    @Then("I should get all available flight options for {string} to {string}")
    public void iShouldGetAllAvailableFlightOptionsForTo(String arg0, String arg1) {
        Drivers.closeBrowser();
    }
}
