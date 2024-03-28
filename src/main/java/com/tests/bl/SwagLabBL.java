package com.tests.bl;

import com.tests.screens.*;

public class SwagLabBL {

    public SwagLabBL addItemsInCart() {
        ProductsScreen.get().addProductToCart(2).goToCart();
        return this;
    }

    public SwagLabBL addCheckoutInformation() {
        CheckOutInformationScreen.get().enterFirstName("test")
                .enterLastName("test").enterPinCode("400000").clickContinue();
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
