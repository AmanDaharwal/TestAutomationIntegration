package com.tests.screens.android;

import com.runner.Driver;
import com.tests.screens.FinishScreen;
import org.openqa.selenium.By;

public class FinishScreenAndroid extends FinishScreen {
    private final Driver driver;
    private By byCompleteOrderTxtXpath = By.xpath("//android.widget.TextView[@text='THANK YOU FOR YOU ORDER']");
    private By byFinishPageTitleXpath = By.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']");

    public FinishScreenAndroid(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean isOrderSuccessful() {
        String orderSuccessfulText = "THANK YOU FOR YOUR ORDER";
        return driver.getText(byCompleteOrderTxtXpath).equalsIgnoreCase(orderSuccessfulText);
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byFinishPageTitleXpath).getText();
    }
}
