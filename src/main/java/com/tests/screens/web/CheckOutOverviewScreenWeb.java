package com.tests.screens.web;

import com.runner.Driver;
import com.tests.screens.CheckOutOverviewScreen;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class CheckOutOverviewScreenWeb extends CheckOutOverviewScreen {
    private final Driver driver;
    private By byListOfCartItemClass = By.className("cart_item");
    private By byFinishBtnXpath = By.xpath("//a[contains(@class,'cart_button')]");
    private By byCheckoutOverviewTitleClass = By.className("subheader");

    public CheckOutOverviewScreenWeb(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.getInnerDriver().findElements(byListOfCartItemClass).size();
    }

    @Override
    public FinishScreen clickFinish() {
        driver.click(byFinishBtnXpath);
        return FinishScreen.get();
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byCheckoutOverviewTitleClass).getText();
    }
}
