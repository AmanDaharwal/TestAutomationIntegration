package com.tests.bl;

import com.tests.screens.LoginScreen;

public class SwagLabBL {


    public SwagLabBL addItemsInCart() {
        return null;
    }

    public SwagLabBL addCheckoutInformation() {
        return null;
    }

    public SwagLabBL verifyCheckoutOverview() {
        return null;
    }

    public SwagLabBL placeOrder() {
        return null;
    }

    public LoginScreen login(String username, String password) {
        return LoginScreen.get().loginToSwagLab(username, password);
    }
}
