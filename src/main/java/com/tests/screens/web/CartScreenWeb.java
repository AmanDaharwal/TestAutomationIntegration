package com.tests.screens.web;

import com.tests.screens.CartScreen;
import com.tests.screens.CheckOutScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartScreenWeb extends CartScreen {

    private WebDriver driver;
    private By byListOfCartItemClass = By.className("cart_item");
    private By byCheckoutBtnXpath = By.xpath("//a[contains(@class,\"checkout_button\")]");

    public CartScreenWeb(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.findElements(byListOfCartItemClass).size();
    }

    @Override
    public CheckOutScreen clickCheckout() {
        driver.findElement(byCheckoutBtnXpath).click();
        return CheckOutScreen.get();
    }
}
