package com.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class IndigoSearchSteps {

    @Given("I launch Indigo application")
    public void iLaunchIndigoApplication() {
        System.out.println("I launch Indigo application");
    }

    @When("I search for {string} way ticket from {string} to {string} for {string} passenger")
    public void iSearchForWayTicketFromToForPassenger(String way, String from, String to, String numberOfPassengers) {
        System.out.println(String.format("I search for {%s} way ticket from {%s} to {%s} for {%s} passenger",
                way, from, to, numberOfPassengers));
    }

    @Then("I should get all available flight options")
    public void iShouldGetAllAvailableFlightOptions() {
        System.out.println("I should get all available flight options");
    }
}
