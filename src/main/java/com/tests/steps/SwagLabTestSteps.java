package com.tests.steps;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.SWAGLAB_TEST_CONTEXT;

import com.runner.Drivers;
import com.runner.TestData;
import com.tests.bl.SwagLabBL;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SwagLabTestSteps {

    TestExecutionContext testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());

    @Given("I launch swag lab application")
    public void iLaunchSwagLabApplication() {
        Drivers.createDriverFor();
        Drivers.launchUrl();
    }

    @When("I shop for multiple items")
    public void iShopForMultipleItems() {
        int numberOfItems = (int) TestData.getTestData(SWAGLAB_TEST_CONTEXT.NUMBER_OF_ITEMS);
        testExecutionContext.addTestState(SWAGLAB_TEST_CONTEXT.NUMBER_OF_ITEMS, TestData.getTestData(SWAGLAB_TEST_CONTEXT.NUMBER_OF_ITEMS));
        testExecutionContext.addTestState(SWAGLAB_TEST_CONTEXT.FIRST_NAME, TestData.getTestDataAsString(SWAGLAB_TEST_CONTEXT.FIRST_NAME));
        testExecutionContext.addTestState(SWAGLAB_TEST_CONTEXT.LAST_NAME, TestData.getTestDataAsString(SWAGLAB_TEST_CONTEXT.LAST_NAME));
        testExecutionContext.addTestState(SWAGLAB_TEST_CONTEXT.PIN_CODE, TestData.getTestDataAsString(SWAGLAB_TEST_CONTEXT.PIN_CODE));
        new SwagLabBL().addItemsInCart(numberOfItems)
                .verifyItemsInCart()
                .addCheckoutInformation()
                .verifyCheckoutOverview();
    }

    @Then("I should able to place order successfully")
    public void iShouldAbleToPlaceOrderSuccessfully() {
        new SwagLabBL().placeOrder();
    }

    @And("login as standard user")
    public void loginAsStandardUser() {
        String username = TestData.getTestDataAsString(SWAGLAB_TEST_CONTEXT.USERNAME);
        String password = TestData.getTestDataAsString(SWAGLAB_TEST_CONTEXT.PASSWORD);
        new SwagLabBL().login(username, password);
    }
}
