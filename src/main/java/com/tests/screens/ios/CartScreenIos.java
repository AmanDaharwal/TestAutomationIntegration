package com.tests.screens.ios;

import com.runner.Driver;
import com.tests.screens.CartScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;

public class CartScreenIos extends CartScreen {
    private final Driver driver;
    private By byListOfCartItemXpath = By.xpath("//XCUIElementTypeOther[@name='test-REMOVE']");
    private By byCheckoutBtnXpath = By.xpath("//XCUIElementTypeOther[@name='test-Cart']/XCUIElementTypeOther");
    private By byCartPageTitleXpath = By.xpath("//XCUIElementTypeStaticText[@name='YOUR CART']");

    public CartScreenIos(Driver driver) {
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

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byCartPageTitleXpath).getText();
    }
}
