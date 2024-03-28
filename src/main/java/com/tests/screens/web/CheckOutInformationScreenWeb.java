package com.tests.screens.web;

import com.tests.screens.CheckOutInformationScreen;
import com.tests.screens.CheckOutOverviewScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutInformationScreenWeb extends CheckOutInformationScreen {
    private WebDriver driver;
    private By byFirstNameTxtBoxId = By.id("first-name");
    private By byLastNameTxtBoxId = By.id("last-name");
    private By byPinCodeTxtBoxId = By.id("postal-code");
    private By byContinueBtnXpath = By.xpath("//input[contains(@class,\"cart_button\")]");

    public CheckOutInformationScreenWeb(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public CheckOutInformationScreen enterFirstName(String firstName) {
        driver.findElement(byFirstNameTxtBoxId).sendKeys(firstName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterLastName(String lastName) {
        driver.findElement(byLastNameTxtBoxId).sendKeys(lastName);
        return this;
    }

    @Override
    public CheckOutInformationScreen enterPinCode(String pinCode) {
        driver.findElement(byPinCodeTxtBoxId).sendKeys(pinCode);
        return this;
    }

    @Override
    public CheckOutOverviewScreen clickContinue() {
        driver.findElement(byContinueBtnXpath).click();
        return CheckOutOverviewScreen.get();
    }
}
