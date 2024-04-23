package com.tests.screens.ios;

import com.runner.Driver;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class FinishScreenIos extends FinishScreen {
    private final Driver driver;
    private By byCompleteOrderTxtXpath = By.xpath("//XCUIElementTypeStaticText[starts-with(@name,'THANK')]");
    private By byFinishPageTitleXpath = By.xpath("//XCUIElementTypeStaticText[@name='CHECKOUT: COMPLETE!']");

    public FinishScreenIos(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean isOrderSuccessful() {
        String orderSuccessfulText = "THANK YOU FOR YOU ORDER";
        return driver.getText(byCompleteOrderTxtXpath).equalsIgnoreCase(orderSuccessfulText);
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byFinishPageTitleXpath).getText();
    }
}
