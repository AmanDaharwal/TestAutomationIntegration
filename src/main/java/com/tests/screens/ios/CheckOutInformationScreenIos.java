package com.tests.screens.ios;

import com.runner.Driver;
import com.tests.screens.CheckOutInformationScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;

public class CheckOutInformationScreenIos extends CheckOutInformationScreen {
    private final Driver driver;
    private By byFirstNameTxtBoxXpath = By.xpath("//XCUIElementTypeTextField[@name='test-First Name']");
    private By byLastNameTxtBoxXpath = By.xpath("//XCUIElementTypeTextField[@name='test-Last Name']");
    private By byPinCodeTxtBoxXpath = By.xpath("//XCUIElementTypeTextField[@name='test-Zip/Postal Code']");
    private By byContinueBtnXpath = By.xpath("//XCUIElementTypeOther[@name='test-CONTINUE']");
    private By byCheckoutInfoPageTitleXpath = By.xpath("//XCUIElementTypeStaticText[@name='CHECKOUT: INFORMATION']");

    public CheckOutInformationScreenIos(Driver driver) {
        this.driver = driver;
    }

    @Override
    public CheckOutInformationScreen enterFirstName(String firstName) {
        driver.enterText(byFirstNameTxtBoxXpath, firstName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterLastName(String lastName) {
        driver.enterText(byLastNameTxtBoxXpath, lastName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterPinCode(String pinCode) {
        driver.enterText(byPinCodeTxtBoxXpath, pinCode);
        return this;
    }

    @Override
    public String getPageTitle() {
        return driver.waitForElementToBePresent(byCheckoutInfoPageTitleXpath).getText();
    }

    @Override
    public CheckOutOverviewScreen clickContinue() {
        driver.click(byContinueBtnXpath);
        return CheckOutOverviewScreen.get();
    }
}