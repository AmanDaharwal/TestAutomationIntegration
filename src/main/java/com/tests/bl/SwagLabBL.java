package com.tests.bl;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.SWAGLAB_TEST_CONTEXT;
import com.tests.screens.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwagLabBL {
    private final static Logger logger = LogManager.getLogger(SwagLabBL.class);

    TestExecutionContext testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());

    public SwagLabBL addItemsInCart(int numberOfItems) {
        ProductsScreen.get().addProductToCart(numberOfItems).goToCart();
        return this;
    }

    public SwagLabBL addCheckoutInformation() {
        logger.info("Add Checkout Information");
        CheckOutInformationScreen.get()
                .enterFirstName(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.FIRST_NAME))
                .enterLastName(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.LAST_NAME))
                .enterPinCode(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.PIN_CODE)).clickContinue();
        return this;
    }

    public SwagLabBL verifyCheckoutOverview() {
        logger.info("Verify Checkout Overview");
        CheckOutOverviewScreen checkOutOverviewScreen = CheckOutOverviewScreen.get();
        checkOutOverviewScreen.getNumberOfCartItems(); // assert here

        checkOutOverviewScreen.clickFinish();
        return this;
    }

    public SwagLabBL placeOrder() {
        logger.info("Place and Verify Order");
        FinishScreen.get().isOrderSuccessful(); // assert here
        return this;
    }

    public SwagLabBL login(String username, String password) {
        logger.info("Login to Swag Lab");
        LoginScreen.get().loginToSwagLab(username, password);
        return this;
    }

    public SwagLabBL verifyItemsInCart() {
        logger.info("Verify Items in cart");
        CartScreen cartScreen = CartScreen.get();
        cartScreen.getNumberOfCartItems(); // assert here
        cartScreen.clickCheckout();
        return this;
    }
}
