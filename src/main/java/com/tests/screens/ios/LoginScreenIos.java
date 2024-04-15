package com.tests.screens.ios;

import com.reporters.ExtendTestLogger;
import com.runner.Driver;
import com.tests.screens.LoginScreen;
import org.openqa.selenium.By;

public class LoginScreenIos extends LoginScreen {
    private final Driver driver;
    private final By byUsernameTxtBoxXpath = By.xpath("//XCUIElementTypeTextField[@name='test-Username']");
    private final By byPasswordTxtBoxXpath = By.xpath("//XCUIElementTypeSecureTextField[@name='test-Password']");
    private final By byLoginBtnXpath = By.xpath("//XCUIElementTypeOther[@name='test-LOGIN']");

    public LoginScreenIos(Driver driver) {
        this.driver = driver;
    }

    @Override
    public LoginScreen loginToSwagLab(String username, String password) {
        driver.enterText(byUsernameTxtBoxXpath, username);
        driver.enterText(byPasswordTxtBoxXpath, password);
        ExtendTestLogger.logInfoMessage("Login for username " + username);
        driver.click(byLoginBtnXpath);
        return this;
    }
}
