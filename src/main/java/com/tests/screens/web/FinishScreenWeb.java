package com.tests.screens.web;

import com.runner.Driver;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class FinishScreenWeb extends FinishScreen {
    private final Driver driver;
    private By byCompleteOrderTxtClass = By.className("complete-header");
    private By byFinishTitleClass = By.className("subheader");

    public FinishScreenWeb(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean isOrderSuccessful() {
        String orderSuccessfulText = "THANK YOU FOR YOUR ORDER";
        return driver.getText(byCompleteOrderTxtClass).equalsIgnoreCase(orderSuccessfulText);
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byFinishTitleClass).getText();
    }
}
