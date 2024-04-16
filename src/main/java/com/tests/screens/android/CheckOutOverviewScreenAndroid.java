package com.tests.screens.android;

import com.runner.Driver;
import com.tests.screens.CheckOutOverviewScreen;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class CheckOutOverviewScreenAndroid extends CheckOutOverviewScreen {
    private final Driver driver;
    private By byListOfCartItemXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private By byFinishBtnXpath = By.xpath("//android.view.ViewGroup[@content-desc='test-FINISH']");
    private By byCheckoutOverviewPageTitleXpath = By.xpath("//android.widget.TextView[@text='CHECKOUT: OVERVIEW']");

    public CheckOutOverviewScreenAndroid(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int getNumberOfCartItems() {
        return driver.getInnerDriver().findElements(byListOfCartItemXpath).size();
    }

    @Override
    public FinishScreen clickFinish() {
        driver.scrollToElementInDevice(byFinishBtnXpath).click();
        return FinishScreen.get();
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byCheckoutOverviewPageTitleXpath).getText();
    }
}
