package com.tests.screens.web;

import com.runner.Driver;
import com.tests.screens.CheckOutInformationScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;

public class CheckOutInformationScreenWeb extends CheckOutInformationScreen {
    private final Driver driver;
    private By byFirstNameTxtBoxId = By.id("first-name");
    private By byLastNameTxtBoxId = By.id("last-name");
    private By byPinCodeTxtBoxId = By.id("postal-code");
    private By byContinueBtnXpath = By.xpath("//input[contains(@class,\"cart_button\")]");
    private By byCheckoutInfoTitleClass = By.className("subheader");

    public CheckOutInformationScreenWeb(Driver driver) {
        this.driver = driver;
    }

    @Override
    public CheckOutInformationScreen enterFirstName(String firstName) {
        driver.enterText(byFirstNameTxtBoxId, firstName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterLastName(String lastName) {
        driver.enterText(byLastNameTxtBoxId, lastName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterPinCode(String pinCode) {
        driver.enterText(byPinCodeTxtBoxId, pinCode);
        return this;
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byCheckoutInfoTitleClass).getText();
    }

    @Override
    public CheckOutOverviewScreen clickContinue() {
        driver.click(byContinueBtnXpath);
        return CheckOutOverviewScreen.get();
    }
}
