package com.tests.steps;

import com.runner.Drivers;
import com.tests.bl.SwagLabBL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SwagLabTestSteps {
    @Given("I launch swag lab application")
    public void iLaunchSwagLabApplication() {
        Drivers.createDriverFor();
        Drivers.launchUrl();
    }

    @When("I shop for multiple items")
    public void iShopForMultipleItems() {
        new SwagLabBL().addItemsInCart()
                .addCheckoutInformation()
                .verifyCheckoutOverview();
    }

    @Then("I should able to place order successfully")
    public void iShouldAbleToPlaceOrderSuccessfully() {
        new SwagLabBL().placeOrder();
    }

    @And("login as standard user")
    public void loginAsStandardUser() {
        new SwagLabBL().login("standard_user", "secret_sauce");
    }
}
