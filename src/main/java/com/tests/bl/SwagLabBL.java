package com.tests.bl;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.entities.SWAGLAB_TEST_CONTEXT;
import com.tests.screens.*;

public class SwagLabBL {

    TestExecutionContext testExecutionContext = SessionContext.getTestExecutionContext(Thread.currentThread().getId());

    public SwagLabBL addItemsInCart(int numberOfItems) {
        ProductsScreen.get().addProductToCart(numberOfItems).goToCart();
        return this;
    }

    public SwagLabBL addCheckoutInformation() {
        CheckOutInformationScreen.get()
                .enterFirstName(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.FIRST_NAME))
                .enterLastName(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.LAST_NAME))
                .enterPinCode(testExecutionContext.getTestStateAsString(SWAGLAB_TEST_CONTEXT.PIN_CODE)).clickContinue();
        return this;
    }

    public SwagLabBL verifyCheckoutOverview() {
        CheckOutOverviewScreen checkOutOverviewScreen = CheckOutOverviewScreen.get();
        checkOutOverviewScreen.getNumberOfCartItems(); // assert here

        checkOutOverviewScreen.clickFinish();
        return this;
    }

    public SwagLabBL placeOrder() {
        FinishScreen.get().isOrderSuccessful(); // assert here
        return this;
    }

    public SwagLabBL login(String username, String password) {
        LoginScreen.get().loginToSwagLab(username, password);
        return this;
    }

    public SwagLabBL verifyItemsInCart() {
        CartScreen cartScreen = CartScreen.get();
        cartScreen.getNumberOfCartItems(); // assert here
        cartScreen.clickCheckout();
        return this;
    }
}
