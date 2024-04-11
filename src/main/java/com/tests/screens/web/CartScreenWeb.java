package com.tests.screens.web;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;

public class CartScreenWeb extends CartScreen {

    private Driver driver;
    private By byListOfCartItemClass = By.className("cart_item");
    private By byCheckoutBtnXpath = By.xpath("//a[contains(@class,\"checkout_button\")]");

    public CartScreenWeb(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.getInnerDriver().findElements(byListOfCartItemClass).size();
    }

    @Override
    public CheckOutOverviewScreen clickCheckout() {
        driver.click(byCheckoutBtnXpath);
        return CheckOutOverviewScreen.get();
    }
}
