package com.tests.screens.android;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;

public class CartScreenAndroid extends CartScreen {
    private final Driver driver;
    private By byListOfCartItemXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private By byCheckoutBtnXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-CHECKOUT']");

    public CartScreenAndroid(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.getInnerDriver().findElements(byListOfCartItemXpath).size();
    }

    @Override
    public CheckOutOverviewScreen clickCheckout() {
        driver.scrollToElementInDevice(byCheckoutBtnXpath).click();
        return CheckOutOverviewScreen.get();
    }
}
