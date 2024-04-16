package com.tests.bl;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.PAGE_TITLE;
import com.entities.Platform;
import com.entities.SWAGLAB_TEST_CONTEXT;
import com.runner.TestRunner;
import com.tests.screens.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.assertj.core.api.Assertions.*;

public class SwagLabBL {
    private final static Logger logger = LogManager.getLogger(SwagLabBL.class);

    TestExecutionContext testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());

    public SwagLabBL addItemsInCart(int numberOfItems) {
        ProductsScreen.get().addProductToCart(numberOfItems).goToCart();
        assertThat(CartScreen.get().getPageTitle().toUpperCase()).isEqualTo(PAGE_TITLE.CART);
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

    public SwagLabBL verifyCheckoutOverview(int numberOfItems) {
        logger.info("Verify Checkout Overview");
        CheckOutOverviewScreen checkOutOverviewScreen = CheckOutOverviewScreen.get();
        assertThat(checkOutOverviewScreen.getPageTitle().toUpperCase()).isEqualTo(PAGE_TITLE.CHECKOUT_OVERVIEW);
        assertThat(checkOutOverviewScreen.getNumberOfCartItems())
                .as("Mismatch in number of items added in checkout overview").isEqualTo(numberOfItems);
        checkOutOverviewScreen.clickFinish();
        if (TestRunner.getPlatform().equals(Platform.web)) {
            assertThat(FinishScreen.get().getPageTitle().toUpperCase()).as("Not in Finish Screen")
                    .isEqualTo(PAGE_TITLE.FINISH);
        } else {
            assertThat(FinishScreen.get().getPageTitle().toUpperCase()).as("Not in Finish Screen")
                    .isEqualTo(PAGE_TITLE.CHECKOUT_COMPLETE);
        }
        return this;
    }

    public SwagLabBL placeOrder() {
        logger.info("Place and Verify Order");
        assertThat(FinishScreen.get().isOrderSuccessful())
                .as("Order is not Successful").isTrue();
        return this;
    }

    public SwagLabBL login(String username, String password) {
        logger.info("Login to Swag Lab");
        LoginScreen.get().loginToSwagLab(username, password);
        assertThat(ProductsScreen.get().getPageTitle().toUpperCase()).isEqualTo(PAGE_TITLE.PRODUCTS);
        return this;
    }

    public SwagLabBL verifyItemsInCart(int numberOfItems) {
        logger.info("Verify Items in cart");
        CartScreen cartScreen = CartScreen.get();
        assertThat(cartScreen.getNumberOfCartItems())
                .as("Mismatch in number of items added in cart").isEqualTo(numberOfItems);
        cartScreen.clickCheckout();
        if (TestRunner.getPlatform().equals(Platform.web)) {
            assertThat(CheckOutInformationScreen.get().getPageTitle().toUpperCase()).isEqualTo(PAGE_TITLE.CHECKOUT_YOUR_INFO);
        } else {
            assertThat(CheckOutInformationScreen.get().getPageTitle().toUpperCase()).isEqualTo(PAGE_TITLE.CHECKOUT_INFO);
        }
        return this;
    }
}
