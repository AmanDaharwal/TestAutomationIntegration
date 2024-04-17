package com.tests.screens.ios;

import com.runner.Driver;
import com.tests.screens.CheckOutOverviewScreen;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class CheckOutOverviewScreenIos extends CheckOutOverviewScreen {
    private final Driver driver;
    private By byListOfCartItemXpath = By.xpath("//XCUIElementTypeStaticText[starts-with(@name,'$')]");
    private By byFinishBtnXpath = By.xpath("//XCUIElementTypeOther[@name='test-FINISH']");
    private By byCheckoutOverviewPageTitleXpath = By.xpath("//XCUIElementTypeStaticText[@name='CHECKOUT: OVERVIEW']");

    public CheckOutOverviewScreenIos(Driver driver) {
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
